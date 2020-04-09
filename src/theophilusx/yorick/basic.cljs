(ns theophilusx.yorick.basic
  (:require [theophilusx.yorick.utils :refer [spath]]
            [clojure.string :as string]
            [theophilusx.yorick.store :as store]))

(defn a
  "Creates an anchor <a> component.
  The required argument is the text title to be used in the anchor.
  Optional keyword arguments include
  | Key | Description |
  |-----|-------------|
  | `href` | a hypertext reference. Defaults to `#` if not provided |
  | `on-click` | a function with no arguments to be executed when the link is clicked |
  | `class` | a string or vector of strings representing CSS class names |
  | `id` | an ID attribute value |
  | `role` | the role attribute value |
  | `aria-label` | aria-label attribute value |
  | `aria-expanded` | true if aria-expanded attribute is to be set |
  | `data-target` | data-target attribute value |"
  [title & {:keys [href on-click class id role aria-label aria-expanded
                   data-target]
                  :or {href "#"}}]
  [:a {:href href
       :on-click on-click
       :class class
       :id id
       :role role
       :aria-label aria-label
       :aria-expanded aria-expanded
       :data-target data-target}
   title])

(defn img
  "A basic component to generate an HTML <img> element.
  The expected argument is a path or link to the image file.
  Additional optional keyword arguments are
  | Key | Description |
  |-----|-------------|
  | `width` | a value for the width attribute |
  | `class` | a string or vector of strings specifying CSS class names |
  | `id` | an id attribute value |"
  [src & {:keys [width class id]}]
  [:img {:src src
         :class class
         :width width
         :id id}])

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
  [:table.table
   (into
    [:tbody]
    (for [k (keys m)]
      (cond
        (map? (get m k)) [:tr
                          [:td [:strong (str k)]]
                          [:td (render-map (get m k))]]
        (set? (get m k)) [:tr
                          [:td [:strong (str k)]]
                          [:td (render-set (get m k))]]
        (vector? (get m k)) [:tr
                             [:td [:strong (str k)]]
                             [:td (render-vec (get m k))]]
        :else [:tr
                  [:td [:strong (str k)]]
                  [:td (str (get m k))]])))])

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
  | Key | Description |
  | `:class`      | A string or vector of strings representing CSS class names |
  | `:position`   | Position of the breadcrumbs, either `:center` or `:right`  |
  | `:separator`  | Type of link separator to use. Possible values are         |
  |              | `:arrow`, `:bullet`, `:dot`, `:succeeds`                    |
  | `:size`      | Size of the breadcrumbs. Possible values are `:small`,      |
  |              | `:medium`, `:large`                                         |"
  [sid crumbs & {:keys [class position separator size]}]
  [:nav.breadcrumb {:class [class
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
                                nil))]
                    :aria-label "breadcrumbs"}
   (into
    [:ul]
    (for [c crumbs]
      (if (:icon c)
        [:li {:class (when (:active c)
                       "is-active")}
         [:a {:href "#"
              :on-click #(store/assoc-in! store/global-state (spath sid)
                                          (:value c))}
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
              :on-click #(store/assoc-in! store/global-state (spath sid)
                                          (:value c))}
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
  [:div.notification {:class [class]}
   (when delete
     [:button.delete {:on-click (fn [e]
                                  (let [note (.-parentNode (.-target e))
                                        parent (.-parentNode note)]
                                    (println (str "Note: " note))
                                    (.dir js/console note)
                                    (println (str "Parent: " parent))
                                    (.dir js/console parent)
                                    (.removeChild parent note) 
                                    ))}])
   body])
