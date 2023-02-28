(ns yorick-demo.toolbars
  (:require [theophilusx.yorick.toolbar :as t]
            [theophilusx.yorick.input :as i]
            [theophilusx.yorick.icon :as icons]
            [theophilusx.yorick.basic :as b]
            [yorick-demo.comp :refer [doc-fn doc-ns]]))

(def defitem-title "deftoolbar-item - a function to define toolbar items")

(def defitem-desc
  [:p "The " [:strong "defitem"] " function is a convenience function "
   "to assist in the definition of toolbar items. A toolbar item can be any "
   "Hiccup markup or Reagent component and is defined using a " [:code "map"]])

(def defitem-args [["content" "The toolbar item content. Can be any hiccup or reagent component"]])

(def defitem-opt-args
  [[":type" "The type of element to use to wrap the toolbar item content. Defaults to :div"]
   [":class" "A string or vector of strings specifying CSS class names to add to the item container"]])

(def defitem-examples
  [{:text  "
  (let [left  [(t/defitem [:p.subtitle.is-5 \"Some Text\"])
               (t/defitem [i/button \"Do Something\"
                            #(js/alert \"Do something cool\")
                            :classses {:button \"is-success\"}])
                 (t/defitem [i/search #(js/alert (str \"Searching for \" %))
                              :button-text \"\"
                              :icon-data (icons/deficon \"fa-search\")
                              :classes
                              {:button [\"has-background-link\"
                                        \"has-text-white\"]}])]
          right [(t/defitem [:strong \"Bold\"])
                 (t/defitem [:p \"Normal\"])]]
      [:div.box
       [:p \"Left side toolbar items\"]
       [b/render-vec left]
       [:p \"right side toolbar items\"]
       [b/render-vec right]])"
    :code (fn []
    (let [left  [(t/defitem [:p.subtitle.is-5 "Some Text"])
                (t/defitem [i/button "Do Something"
                            #(js/alert "Do something cool")
                            :classses {:button "is-success"}])
                 (t/defitem [i/search #(js/alert (str "Searching for " %))
                             :button-text ""
                             :icon-data (icons/deficon "fa-search")
                             :classes
                             {:button ["has-background-link"
                                       "has-text-white"]}])]
          right [(t/defitem [:strong "Bold"])
                 (t/defitem [:p "Normal"])]]
      [:div.box
       [:p "Left side toolbar items"]
       [b/render-vec left]
       [:p "right side toolbar items"]
       [b/render-vec right]]))}])

(defn defitem-page []
  [doc-fn {:title defitem-title
           :desc defitem-desc
           :args defitem-args
           :opt-args defitem-opt-args
           :examples defitem-examples}])

;; toolbar

(def toolbar-title  "toolbar - a basic toolbar component")

(def toolbar-desc
  [:p
   "The " [:strong "toolbar"] " component provides a simple horizontal "
   "toolbar which can contain text, Hiccup markup and other reagent components, "
   "such as a search box."])

(def toolbar-args [["items" "A vector of toolbar item definition maps. See defitem for details"]])

(def toolbar-opt-args
  [[":right-items" "A vector of toolbar item definition maps to be added from the right end of the toolbar. See defitem for details on map structure"]
   [":class" "A string or vector of strings specifying CSS class names to add to the toolbar"]])

(def toolbar-examples
  [{:text  "
  (let [main-items [(t/defitem :content [:p.subtitle.is-5 \"Some Text\"])
                    (t/defitem :content [i/button \"Do Something\"
                                         #(js/alert \"Do something cool\")
                                         :classes {:button \"has-background-success\"}])
                    (t/defitem :content [i/search #(js/alert (str \"Searching for \" %))
                                         :button-text \"\"
                                         :icon-data (icons/deficon \"fa-search\")
                                         :classes {:button [\"has-background-link\"
                                                            \"has-text-white\"]}])]
           right [(t/defitem :content [:strong \"Bold\"])
                  (t/defitem :content [:p \"Normal\"])]]
       [:<>
        [:p \"An example toolbar\"]
        [t/toolbar main-items :right-items right]])"
    :code (fn []
    (let [main-items [(t/defitem [:p.subtitle.is-5 "Some Text"])
                 (t/defitem [i/button "Do Something"
                             #(js/alert "Do something cool")
                             :classes {:button "has-background-success"}])
                 (t/defitem [i/search #(js/alert (str "Searching for " %))
                             :button-text ""
                             :icon-data (icons/deficon "fas fa-search")
                             :classes
                             {:button ["has-background-link"
                                       "has-text-white"]}])]
           right [(t/defitem [:strong "Bold"])
                  (t/defitem [:p "Normal"])]]
       [:<>
        [:p "An example toolbar"]
        [t/toolbar main-items :right-items right]]))}])

(defn toolbar-page []
  [doc-fn {:title toolbar-title
           :desc toolbar-desc
           :args toolbar-args
           :opt-args toolbar-opt-args
           :examples toolbar-examples}])

;; toolbar NS

(def ns-sid :ui.sidebar.toolbar-ns)

(def ns-title "The Toolbar Component")

(def ns-doc [:p "The " [:strong "theophilusx.yorick.toolbar"] " namespace provides support "
             "for a toolbar component, a horizontal bar of action items to perform "
             "various actions. A toolbar can contain any Hiccup markup or another "
             "Reagent component."])

(def ns-pages [{:title "defitem"
                :value :defitem
                :page defitem-page}
               {:title "toolbar"
                :value :toolbar
                :page toolbar-page}])

(defn ns-page []
  [doc-ns ns-sid ns-title ns-doc ns-pages])



