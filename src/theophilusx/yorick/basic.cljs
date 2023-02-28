(ns theophilusx.yorick.basic
  "A collection of simple components which are too basic to require their own
  name space."
  (:require [theophilusx.yorick.utils :refer [cs spath]]
            [theophilusx.yorick.icon :as icon]
            [clojure.string :as string]
            [theophilusx.yorick.store :as store]))

(defn a
  "Creates an anchor <a> component.
  The required argument is the text title to be used in the anchor.
  Optional keyword arguments include

  | Key          | Description                                                |
  |--------------|------------------------------------------------------------|
  | `:href`      | a hypertext reference. Defaults to `#` if not provided     |
  | `:on-click`  | a function with no arguments to be executed when the       |
  |              | link is clicked                                            |
  | `:class`     | a string or vector of strings representing CSS class names |
  | `:attrs`     | a map of HTML attribute values. Keys are HTML attribute    |
  |              | names as keywords e.g. `:role`                             |
  | `:icon-data` | an icon definition map to add an icon to the link title.   |
  |              | See `theophilusx.yorick.icon` for details on map structure |
  | `:id`        | An HTML id value to associate with this element            |"
  [title & {:keys [href on-click class attrs icon-data id]}]
  [:a (merge attrs {:href href
                    :on-click on-click
                    :id (or id (gensym "a-"))
                    :class (cs class)})
   (when icon-data
     [icon/icon icon-data])
   [:span title]])

(defn img
  "A basic component to generate an HTML <img> element.
  The expected argument is a path or link to the image file.
  Additional optional keyword arguments are

  | Key      | Description                                                 |
  |----------|-------------------------------------------------------------|
  | `:class` | a string or vector of strings specifying CSS class names    |
  | `:id`    | an HTML id value for this element                           |
  | `:attrs` | a map of HTML attribute values. Keys are the HTML attribute |
  |          | names as keywords e.g. `:id`                                |"
  [src & {:keys [class attrs id]}]
  [:img (merge attrs {:src src
                      :id (or id (gensym "img-"))
                      :class (cs class)})])

(declare render-map)
(declare render-set)

(defn render-vec
  "Render a ClojureScript `vector` as an un-ordered list"
  [v]
  (into
   [:ul]
   (for [i v]
     (cond
       (vector? i) [:li (render-vec i)]
       (map? i)    [:li (render-map  i)]
       (set? i)    [:li (render-set i)]
       :else [:li (str i)]))))

(defn render-set
  "Render a ClojureScript `set` as a string.
  Values are separated by commas (,) and surrounded by parenthesis."
  [s]
  [:<>
   (str "(" (string/join ", " s) ")")])

(defn render-map
  "Render a ClojureScript `map` as an HTML table.
  Uses `render-vec` and `render-set` to render values which are ClojuresScript
  `vectors` or `maps`."
  [m]
  [:div.table-container
   [:table.table
    (into
     [:tbody]
     (for [k (keys m)]
       (cond
         (map? (get m k))    [:tr
                           [:td [:strong (str k)]]
                           [:td (render-map (get m k))]]
         (set? (get m k))    [:tr
                           [:td [:strong (str k)]]
                           [:td (render-set (get m k))]]
         (vector? (get m k)) [:tr
                              [:td [:strong (str k)]]
                              [:td (render-vec (get m k))]]
         :else               [:tr
                [:td [:strong (str k)]]
                [:td (str (get m k))]])))]])

(defn breadcrumbs
  "Renders a breadcrumb link line.
  Creates a list of page links representing a breadcrumb trail. The `sid`
  argument is a state path keyword where periods represent path separators e.g.
  `:ui.breadcrumb.current` represents the path keys `[:ui :breadcrumb :current]`.
  The `crumbs` argument is a vector of maps representing each link in the trail.
  Each map consists of the following keys

  | Key       | Description                                                   |
  |-----------|---------------------------------------------------------------|
  | `:name`   | Text to use in the link                                       |
  | `:icon`   | An icon data map describing an icon to add to the link        |
  |           | See `theophilusx.yorick.icon` for description of icon data map|
  | `:active` | True if this link is active                                   |
  | `:value`  | The value to set in the global state atom when the link is    |
  |           | selected                                                      |

  This component also accepts optional keyword arguments

  | Key           | Description                                                |
  |---------------|------------------------------------------------------------|
  | `:class`      | A string or vector of strings representing CSS class names |
  | `:position`   | Position of the breadcrumbs, either `:center` or `:right`  |
  | `:separator`  | Type of link separator to use. Possible values are         |
  |               | `:arrow`, `:bullet`, `:dot`, `:succeeds`                   |
  | `:size`       | Size of the breadcrumbs. Possible values are `:small`,     |
  |               | `:medium`, `:large`                                        | "
  [sid crumbs & {:keys [class position separator size]}]
  [:nav.breadcrumb {:class (cs class
                               (when position
                                 (case position
                                   :center "is-centered"
                                   :right "is-right"
                                   nil))
                               (when separator
                                 (case separator
                                   :arrow "has-arrow-separator"
                                   :bullet "has-bullet-separator"
                                   :dot "has-dot-separator"
                                   :succeeds "has-succeeds-separator"
                                   nil))
                               (when size
                                 (case size
                                   :small "is-small"
                                   :medium "is-medium"
                                   :large "is-large"
                                   :normal ""
                                   nil)))
                    :aria-label "breadcrumbs"}
   (into
    [:ul]
    (for [c crumbs]
      (if (:icon c)
        [:li {:class (when (:active c)
                       "is-active")}
         [:a {:href "#"
              :on-click #(store/assoc-in! (spath sid) (:value c))}
          [:span.icon {:class (when (contains? (:icon c) :size)
                                (case (:size (:icon c))
                                  :small "is-small"
                                  :medium "is-medium"
                                  :large "is-large"
                                  nil))}
           [:i.fa {:class (:name (:icon c))
                   :aria-hidden "true"}]]
          [:span (:name c)]]]
        [:li {:class (when (:active c)
                       "is-active")}
         [:a {:href "#"
              :on-click #(store/assoc-in! (spath sid) (:value c))}
          (:name c)]])))])

(defn notification
  "Simple notification component with optional close button.
  This component displays whatever is passed in as the `body` argument as
  a notification. Supports the following optional keyword arguments

  | Key       | Description                                                 |
  |-----------|-------------------------------------------------------------|
  | `:class`  | A string or vector of strings specifying CSS class names    |
  | `:delete` | Adds a delete (X) button to top left corner of notification |" 
  [body & {:keys [class delete]}]
  [:div.notification {:class (cs class)}
   (when delete
     [:button.delete {:on-click (fn [e]
                                  (let [note (.-parentNode (.-target e))
                                        parent (.-parentNode note)]
                                    (.removeChild parent note)))}])
   body])
