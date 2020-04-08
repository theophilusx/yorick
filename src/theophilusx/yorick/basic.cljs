(ns theophilusx.yorick.basic
  (:require [theophilusx.yorick.utils :refer [spath]]
            [clojure.string :as string]
            [theophilusx.yorick.store :as store]))

(defn a
  "Creates an anchor <a> component. The required argument is the text title to
  be used in the anchor. Optional keyword arguments include
  `href` - a hypertext reference. Defaults to `#` if not provided
  `on-click` - a function with no arguments to be executed when the link is clicked
  `class` - a string or vector of strings representing CSS class names
  `id` - an ID attribute value
  `role` - the role attribute value
  `aria-label` - aria-label attribute value
  `aria-expanded` - true if aria-expanded attribute is to be set
  `data-target` - data-target attribute value"
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
  "A basic component to generate an HTML <img> element. The expected argument
  is a path or link to the image file. Additional optional keyword arguments are
  `width` - a value for the width attribute
  `class` - a string or vector of strings specifying CSS class names
  `id` - an id attribute value"
  [src & {:keys [width class id]}]
  [:img {:src src
         :class class
         :width width
         :id id}])

(declare render-map)
(declare render-set)

(defn render-vec
  "Vector display component. Will render vector as an un-ordered list"
  [v]
  (into
   [:ul]
   (for [i v]
     (cond
       (vector? i) [:li (render-vec i)]
       (map? i)    [:li (render-map  i)]
       (set? i)    [:li (render-set i)]
       :else [:li (str i)]))))

(defn render-set [s]
  [:div.box
   (str "(" (string/join ", " s) ")")])

(defn render-map [m]
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
                          [:td (str (get m k))]]
        (vector? (get m k)) [:tr
                             [:td [:strong (str k)]]
                             [:td (render-vec (get m k))]]
        :else [:tr
                  [:td [:strong (str k)]]
                  [:td (str (get m k))]])))])

(defn breadcrumbs [id crumbs & {:keys [class position separator size]}]
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
              :on-click #(store/assoc-in! store/global-state (spath id)
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
              :on-click #(store/assoc-in! store/global-state (spath id)
                                          (:value c))}
          (:name c)]])))])

(defn notification [body & {:keys [class delete]}]
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
