(ns testbed.navbar
  "A basic navibatgion bar component. Uses a storage identifier (aka sid) to
  determine where to store data regarding the state of the navbar. "
  (:require [theophilusx.yorick.utils :refer [cs spath str->keyword]]
            [theophilusx.yorick.store :as store]
            [theophilusx.yorick.basic :as basic]))

(defn nav-link
  "Navbar link definition helper. Returns a map representing the basic link
  definition. The `title` argument used as the text of the link. Supported
  optional keyword arguments are

  | Keyword   |                                                             |
  |-----------|-------------------------------------------------------------|
  | `:value`  | Value used to represent this link when selected. Defaults   |
  |           | to the title converted to a keyword when absent.            |
  | `:icon`   | An icon data map used to add an icon to the link title      |
  | `:class`  | Additional CSS classes to add to the component              |
  | `:id`     | An HTML ID attribute. Should be unique. Will be generated   |
  |           | using `gensym` if absent                                    |"
  [title & {:keys [value icon class id]}]
  {:type :link
   :title title
   :value (or value (str->keyword title))
   :id (or id (gensym "link-"))
   :class class
   :icon-data icon})

(defn nav-container
  "Navbar container definition. Provides a component to enable embedding of
  arbitrary content into a navbar. The `contents` argument is any hiccup
  component or string to be embedded into the navbar. Optional keyword
  arguments supported are

  | Keyword   |                                                             |
  |-----------|-------------------------------------------------------------|
  | `:class`  | Additional CSS class names to add to the component          |
  | `:id`     | A unique HTML ID attribute. If none is provided, use        |
  |           | `gensym` to create a unique value.                          |"
  [contents & {:keys [class id]}]
  {:type :container
   :contents contents
   :id (or id (gensym "container-"))
   :class class})

(defn nav-dropdown
  "A navbar dropdown menu component.
  | Argument  |                                                             |
  |-----------|-------------------------------------------------------------|
  | `title`   | The title for the parent of the drop down menu. This is     |
  |           | what will appear in the navbar                              |
  | `children`| A vector of navbar items. Can be  a link, a container or a  |
  |           | divider.                                                    |

  The following optional keyword arguments are also supported

  | Keyword      |                                                         |
  |--------------|---------------------------------------------------------|
  | `:class`     | Additional CSS class names to add to the component      |
  | `:icon`      | An icon data map defining an icon to add to the parent  |
  | `:hoverable?`| If true, the dropdown will be exposed when the mouse    |
  |              | hovers over it.                                         |
  | `:id`        | An HTML ID attribute. Used to track the state of the    |
  |              | dropdown. A unique value will be generated via `gensym` |
  |              | if none is supplied.                                    |"
  [title children & {:keys [class icon hoverable? id]}]
  {:type :dropdown
   :title title
   :children children
   :hoverable? hoverable?
   :class class
   :id (or id (gensym "dropdown-"))
   :icon-data icon})

(defn nav-divider 
  "Generates a divider used to separate items in a dropdown menu."
  []
  {:type :divider
   :id (gensym "divider-")})

(defn- make-link
  "Generate a link component from a link definition map."
  [active-cur dropdown-cur l]
  [basic/a (:title l) :class (cs "navbar-item" (:class l)
                                 (when (= @active-cur (:value l))
                                   "is-active"))
   :on-click (fn []
               (reset! active-cur (:value l))
               (reset! dropdown-cur nil))
   :id (:id l)
   :icon-data (:icon-data l)])

(defn- make-container
  "Generate a container component from a container definition map."
  [dropdown-cur c]
  [:div.navbar-item {:class (cs (:class c))
                     :id (:id c)
                     :on-click #(reset! dropdown-cur nil)}
   (:contents c)])

(defn- make-divider
  "Generate a divider component fro a divider definition map."
  [m]
  [:hr.navbar-divider {:id (:id m)}])

(defn- make-dropdown
  "Build a dropdown menu from a sid and a dropdown definition map."
  [sid active-cur dropdown-cur d]
  [:div.navbar-item.has-dropdown {:class (cs (when (:hoverable? d) "is-hoverable")
                                             (when (= @dropdown-cur (:id d)) "is-active"))}
     [basic/a (:title d) :class (cs "navbar-link" (:class d))
      :on-click (fn []
                  (if (= @dropdown-cur (:id d))
                    (reset! dropdown-cur nil)
                    (reset! dropdown-cur (:id d))))
      :icon-data (:icon-data d)
      :id (:id d)]
   (into [:div.navbar-dropdown (when (store/get-in (conj (spath sid) :transparent?))
                                 {:class "is-boxed"})]
           (for [c (:children d)]
             (condp = (:type c)
               :link      ^{:key (:id c)} [make-link active-cur dropdown-cur c]
               :container ^{:key (:id c)} [make-container dropdown-cur c]
               :divider   ^{:key (:id c)} [make-divider c]
               (println (str "Unexpected value: " (:type c))))))])

(defn- make-item
  "Given a navbar item definition map, generate a corresponding navbar item
  component.
  | Keyword        |                                                             |
  |----------------|-------------------------------------------------------------|
  | `sid`          | The storage identifier associated with a navbar.            |
  | `active-cur`   | Cursor for active navbar item state                         |
  | `dropdown-cur` | Cursor for active dropdown state                            |
  | `m`            | A navbar item definition map created using `nav-link`,      |
  |                | `nav-container`, `nav-divider` or `nav-dropdown`.           |"
  [sid active-cur dropdown-cur m]
  (condp = (:type m)
    :link [make-link active-cur dropdown-cur m]
    :container [make-container dropdown-cur m]
    :dropdown [make-dropdown sid active-cur dropdown-cur m]))

(defn nav-menu
  "Generate the navbar menus.
  | Argument  |                                                             |
  |-----------|-------------------------------------------------------------|
  | `sid`     | The storage identifier used with the navbar                 |
  | `entries` | A map with two keys `:start` (mandatory) and `:end`         |
  |           | (optional). Values are a vector of navbar item definition   |
  |           | maps.                                                       |

  Additional optional keyword arguments supported are

  | Keyword   |                                                             |
  |-----------|-------------------------------------------------------------|
  | `:class`  | Additional CSS class names to add to the navbar-menu div    |"
  [sid _ & _]
  (let [active-cur   (store/cursor (conj (spath sid) :active-item))
        dropdown-cur (store/cursor (conj (spath sid) :active-dropdown))]
    (fn [_ entries & {:keys [class]}]
      [:div.navbar-menu {:class (cs class)}
       (into [:div.navbar-start]
             (for [i (:start entries)]
               [make-item sid active-cur dropdown-cur i]))
       (when (:end entries)
         (into [:div.navbar-end]
               (for [i (:end entries)]
                 [make-item sid active-cur dropdown-cur i])))])))

(defn nav-brand
  "Generate a brand component for left most position in navbar.

  | Argument | Description                                            |
  |--------------|----------------------------------------------------|
  | `sid`        | Storage identifier used for the navbar             |
  | `brand-data` | Vector of navbar item definition maps              |
  | `has-burger` | When true, add a burger menu on small screens      |"
  [sid _ _]
  (let [active-cur   (store/cursor (conj (spath sid) :active-item))
        dropdown-cur (store/cursor (conj (spath sid) :active-dropdown))
        burger-cur (store/cursor (conj (spath sid) :burger-active))]
    (fn [_ brand-data has-burger?]
      [:div.navbar-brand
       (for [i brand-data]
         ^{:key (:id i)} [make-item sid active-cur dropdown-cur i])
       (when has-burger?
         [:a.navbar-burger {:role          "button"
                            :aria-label    "menu"
                            :aria-expanded "false"
                            :on-click      #(swap! burger-cur not)
                            :class         (when @burger-cur "is-active")}
          [:span {:aria-hidden "false"}]
          [:span {:aria-hidden "false"}]
          [:span {:aria-hidden "false"}]])])))

(defn navbar
  "Basic navigation bar.

  | Argument      | Description                                          |
  |---------------|------------------------------------------------------|
  | `sid`         | Storage identifier to use with this navbar           |
  | `menu`        | A map of navbar items which make up the menu. Has two|
  |               | keys, `:start` (mandatory) and `:end` (optional).    |
  |               | Value for each key is a vector of definition maps    |
  |               | for navbar menu items e.g. `nav-link`, `nav-dropdown`|
  |               | and `nav-container`                                  |

  The function also accepts the following optional keyword arguments

  | Keyword        | Description                                        |
  |----------------|----------------------------------------------------|
  | `:default`     | The default menu selection. This will be the active|
  |                | menu choice when component is first loaded         |
  | `:brand`       | Vector of navbar items representing the brand to be|
  |                | placed in the left most position of the navbar.    |
  | `:has-burger?` | If true, a burger menu will be added when the      |
  |                | display device is a small screen                   |
  | `:spaced?      | When true, add additional space around menu items  |
  |                | to space them out more.                            |
  | `:color`       | Set the navbar color. This is a keyword which is   |
  |                | same as the Bulma color classes i.e. `:is-dark`    |
  | `:transparent?`| When true, removes any hover or active background  |
  |                | effects from the navbar                            |
  | `:expanded?`   | If true the navbar expands to full width component |
  | `:tab?`        | If true, add a bottom boarder on hover and sets the|
  |                | bottom boarder when menu item is active            |
  | `:class`       | A string or vector of strings specifying additional|
  |                | CSS classes to add to the navbar element           |
  | `:shadow?`     | When true, add a subtle shadow effect              |"

  [sid _ & {:keys [default transparent?]}]
  (store/assoc-in! (spath sid) {:burger-active? false
                                :active-dropdown nil
                                :active-item default
                                :transparent? transparent?})
  (fn [_ menu & {:keys [brand has-burger? spaced? color transparent? class
                       expanded? tab? shadow?]}]
    [:nav.navbar {:class (cs class
                             (when spaced? "is-spaced")
                             (when color (name color))
                             (when transparent? "is-transparent")
                             (when expanded? "is-expanded")
                             (when tab? "is-tab")
                             (when shadow? "has-shadow"))
                  :role "navigation"
                  :aria-label "main navigation"}
     (when brand
       [nav-brand sid brand has-burger?])
     [nav-menu sid menu]]))


