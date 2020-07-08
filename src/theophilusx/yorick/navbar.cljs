(ns theophilusx.yorick.navbar
  (:require [reagent.core :as r]
            [theophilusx.yorick.utils :refer [cs spath]]
            [theophilusx.yorick.store :as store]
            [theophilusx.yorick.icon :as icons]
            [theophilusx.yorick.basic :as basic]))

(defn active?
  "Returns true if `id` is the value of the key `:active-state` in the atom
  specified by `model`."
  [model id]
  (if (= (:active-item @model) id)
    true
    false))

(defn set-active
  "Set the value of `:active-state` in the atom `state` to `id` or nil if `id`
  is not supplied."
  [model & id]
  (swap! model assoc :active-item (first id)))

(defn dropdown-active?
  "Returns true if value of `:active-dropdown` in atom `model` is equal to `id`."
  [model id]
  (if (= (:active-dropdown @model) id)
    true
    false))

(defn toggle-dropdown
  "If `id` is nil, set `:active-dropdown` in atom `model` to nil, otherwise set
  `:active-dropdown` to nil if `id` equals current value or to value of `id`
  otherwise.\"`"
  [model & id]
  (if (nil? id)
    (swap! model assoc :active-dropdown nil)
    (if (= (:active-dropdown @model) (first id))
      (swap! model assoc :active-dropdown nil)
      (swap! model assoc :active-dropdown (first id)))))

(defn set-choice
  "Set the value of the key specified by storage identifier keyword `sid` to
  the value of `id` in the global state atom."
  [sid & id]
  (store/assoc-in! store/global-state (spath sid) (first id)))

(defn item-a
  "Create a navbar anchor component from the map `a`. The `model` is the atom
  used as the document model store. The map can have the following keys:

  | Keyword | Description |
  | `:icon-data` | An icon data map (see `theophilusx.yorick.icon` for details)|
  | `:contents`  | The link title contents                                     |
  | `:class`     | a string or vector of strings specifying CSS class names to |
  |              | be associated with the anchor                               |
  | `:href`      | A href value to be used by the anchor                       |
  | `:selectable`| If true, a click handler will be associated with the link,  |
  |              | making the item selectable in the navbar                    |"
  [a model]
  [basic/a [:<>
            (when (:icon-data a)
              [icons/icon-component (:icon-data a)])
            (:contents a)]
   :class (cs "navbar-item"
              (:class a)
              (when (active? model (:id a)) "is-active"))
   :href (:href a)
   :on-click (when (:selectable a)
               (fn []
                 (toggle-dropdown model)
                 (set-active model (:id a))
                 (set-choice (:sid @model) (:id a))))])

(defn item-raw
  "A generic item which is just wrapped in a plain `div`. The `r` argument is a
  map with supported keys `:class`, a string or vector of strings containing CSS
  class names and `:content`, the content to put inside the `div`. No additional
  classes are added to the `div` apart from `content`. No click handler is
  associated with the component."
  [r _]
  [:div.content {:class (cs (:class r))}
   (:contents r)])

(defn item-div
  "This function generates a `div` component which contains sub-components
  built from the contents of the `:contents` key in the map `d`. The `model`
  argument is a reagent atom to be used as the document model store for tracking
  the state of the enclosed components. The following keys are recognised in the
  map `d`:

  | Keyword     | Description                                                 |
  | `:class`    | a string or vector of strings specifying CSS class names    |
  |             | to be added to the outer `div`                              |
  | `:contents` | A vector of navbar item maps. See below for definition of   |
  |             | these maps                                                  |"
  [d model]
  (into
   [:div.navbar-item {:class (cs (:class d))}]
   (for [c (:contents d)]
     (case (:type c)
       :a [item-a c model]
       :raw [item-raw c model]
       [item-div c model]))))

(defn item-dropdown
  "Generates a dropdown menu component. The `d` argument is a map which defines
  the dropdown menu. The `model` argument is a reagent atom used as the document
  model store for tracking state. The following keys are used from the `d` map:

  | Keyword     | Description                                             |
  |-------------|---------------------------------------------------------|
  | `:id`       | The HTML id attribute associated with the dropdown menu |
  | `:title`    | The title for the dropdown menu                         |
  | `:contents` | A vector of navbar item definition maps. See below      |"
  [d model]
  [:div.navbar-item.has-dropdown
   {:class (cs (when (:is-hoverable d) "is-hoverable")
               (when (dropdown-active? model (:id d))
                 "is-active"))}
   [basic/a (:title d) :class "navbar-link" :attrs {:id (:id d)}
    :on-click #(toggle-dropdown model (:id d))]
   (into
    [:div.navbar-dropdown]
    (for [i (:contents d)]
      (case (:type i)
        :a [item-a i model]
        :dropdown [item-dropdown i model]
        :divider [:hr.navbar-divider]
        [item-div i model])))])

(defn make-item
  "Dispatcher function which calls appropriate generator helper function based
  on the `:type` key in the `i` map. The `model` argument is a reagent atom to
  be used as the document model store for tracking state changes. See the
  `defnavbar-item` function below for details on the keys supported in navbar
  item maps."
  [i model]
  (case (:type i)
    :a [item-a i model]
    :raw [item-raw i model]
    :dropdown [item-dropdown i model]
    :divider [:hr.navbar-divider]
    [item-div i model]))

(defn burger
  "Generate a burger menu for mobile devices to allow for expansion of the
  navbar on small screens. The `model` argument is a reagent atom to be used as
  the document model store for tracking state. The `:sid` key from the `model`
  atom is used to determine the HTML data-target attribute."
  [model]
  [basic/a [:<>
            [:span {:aria-hidden true}]
            [:span {:aria-hidden true}]
            [:span {:aria-hidden true}]]
   :class (cs "navbar-burger"
              "burger"
              (when (get @model :burger-active)
                "is-active"))
   :role "button"
   :aria-label "menu"
   :aria-expanded "false"
   :data-target (:sid @model)
   :on-click #(swap! model update :burger-active not)])

(defn brand
  "Generate a brand component for the navbar. The `model` argument is a
  reagent atom used as the document model store and for the data items required
  to define the brand component. The following keys are expected to exist in the
  atom: `:brand` and `:has-burger`."
  [model]
  [:div.navbar-brand
   (make-item (:brand @model) model)
   (when (:has-burger @model)
     (burger model))])

(defn menu
  "Generate the navbar menus. The `model` argument is a reagent atom used as
  the document model store. It is a `map` which contains the definitions for
  the navbar menu. The following keys are supported in the `map`

  | Key              | Description                                           |
  |------------------|-------------------------------------------------------|
  | `:burger-active` | Current state of the burger element                   |
  | `:sid`           | Storage identifier keyword used for this navbar       |
  | `:menus`         | Vector of menu definition maps (see `defnavbar-item`) |
  | `:end-menu`      | Vector of menu definition maps (see `defnavbar-item`) |"
  [model]
  [:div.navbar-menu {:class (cs (when (:burger-active @model)
                                  "is-active"))
                     :id (:sid @model)}
   (into
    [:div.navbar-start]
    (for [s (:menus @model)]
      [make-item s model]))
   (when (:end-menu @model)
     (into
      [:div.navbar-end]
      (for [e (:end-menu @model)]
        [make-item e model])))])

(defn defnavbar-item
  "Define navbar entry item map. Supported keys are

  | Key             | Description                                             |
  |-----------------|---------------------------------------------------------|
  | `:type`         | the entry type. See below for possible values.          |
  |                 | Defaults to `:a`                                        |
  | `:title`        | The item title to appear in the navbar                  |
  | `:class`        | a string or vector of strings specifying CSS class      |
  |                 | names                                                   |
  | `:href`         | hypertext url for `:a` items. Defaults to `#`           |
  | `:id`           | Add an id attribute to this item. Defaults to `nav-<n>` |
  |                 | where `<n>` is a unique value                           |
  | `:contents`     | the actual item contents                                |
  | `:icon-data`    | An icon data `map` (see `theophilusx.yorick.icons`)     |
  | `:selectable`   | determines if the item has a click handler attached.    |
  |                 | Defaults to true.                                       |
  | `:is-hoverable` | for dropdown menus determines if the menu will dropdown |
  |                 | when mouse hovers over                                  |

  Possible values for the `:type` key are

  | Value       | Description                                                 |
  |-------------|-------------------------------------------------------------|
  | `:a`        | A plain anchor based menu item                              |
  | `:div`      | A basic div element e.g. arbitrary content wrapped in a div |
  | `:dropdown` | A dropdown menu element                                     |
  | `:raw`      | A raw element. Just used 'as-is'.                           |
  | `:divider   | A horizontal divider used in dropdown menus                 |"
  [& {:keys [type title classes href id contents selectable
             icon-data is-hoverable]
      :or {type :a
           href "#"
           id (keyword (gensym "nav-"))
           selectable true}}]
  {:type type
   :title title
   :class classes
   :href href
   :id id
   :contents contents
   :selectable selectable
   :icon-data icon-data
   :is-hoverable is-hoverable})

(defn navbar
  "Define an application navbar. The `nb-def` argument is a map which can
  have the following keys:

  | Key             | Description                                             |
  |-----------------|---------------------------------------------------------|
  | `:sid`          | A storage identifier keyword e.g :topbar or :top.bar    |
  | `:has-shadow`   | true if the navbar has a shadow effect. Default true    |
  | `:is-dark`      | if true, navbar is a dark colour. Default is false      |
  | `:has-burger`   | true if navabar should include a 'burger' menu.         |
  |                 | Defaault true                                           |
  | `:class`        | additional class attributes to be added to main nav tag |
  | `:default-link` | the default (active) link set when navbar first loaded  |
  | `:brand`        | Brand definition (use defnavbar-item to define)         |
  | `:menus`        | the navbar menus = vector of defnavbar-item items       |
  | `:end-menu`     | vector of menus to be added after main menus i.e. to    |
  |                 | the right                                               |"
  [nb-def]
  (let [model (r/atom (merge {:has-shadow true
                              :has-burger true
                              :is-dark false
                              :burger-active false
                              :active-item (:default-link nb-def)
                              :dropdown-active false}
                             nb-def))]
    (set-choice (:sid @model) (:default-link @model))
    (fn [nb-def]
      (swap! model assoc :menus (:menus nb-def)
             :end-menu (:end-menu nb-def))
      [:nav.navbar {:class (cs  (:class @model)
                                (when (:has-shadow @model) "has-shadow"))
                    :role       "navigation"
                    :aria-label "main navigation"}
       (when (:brand @model)
         [brand model])
       [menu model]])))
