(ns yorick-demo.tabs
  (:require [theophilusx.yorick.tab :as t]
            [theophilusx.yorick.utils :refer [spath]]
            [theophilusx.yorick.card :as c]
            [theophilusx.yorick.store :refer [get-in]]
            [theophilusx.yorick.basic :as b]
            [yorick-demo.comp :refer [doc-fn doc-ns]]))

;; deftab

(def deftab-title  "deftab - convenience function for defining tabs")

(def deftab-desc
  [:p
   "The " [:strong "deftab"] " function is a convenience function for "
   "defining tab elements for a horizontal navigation bar. The tab "
   "definitions are a map with specific keys. The " [:code "title"]
   " argument is the text to appear in the tab. The function supports the "
   "following optional keyword arguments:"])

(def deftab-args [["title" "The title to use as the text of the tab"]])

(def deftab-opt-args
  [[":value" "A unique value used within the global store to track when this tab is selected. When not specified, use the title converted to a keyword or a random gensym"]
   [":id" "A value used for the HTML ID attribute for this tab. Defaults to the :value as a string."]
   [":icon" "An icon data map which specifies an icon to be added to this tab. See the theophilusx.yorick.icon NS for details"]
   [":class" "A string or vector of strings specifying CSS class names to add to this tab"]])

(def deftab-examples
  [{:text  "
  (let [tabs [(t/deftab \"Tab 1\" :value :tab1)
              (t/deftab \"Tab 2\" :value :tab2)
              (t/deftab \"Tab 3\" :value :tab3)]]
       [:<>
        [:p \"Example tab definitions\"]
        [b/render-vec tabs]])"
    :code (fn []
    (let [tabs [(t/deftab "Tab 1" :value :tab1)
                 (t/deftab "Tab 2" :value :tab2)
                 (t/deftab "Tab 3" :value :tab3)]]
       [:<>
        [:p "Example tab definitions"]
        [b/render-vec tabs]]))}])

(defn deftab-page []
  [doc-fn {:title deftab-title
           :desc deftab-desc
           :args deftab-args
           :opt-args deftab-opt-args
           :examples deftab-examples}])

(def tab-title  "tab - a horizontal tab navigation bar component")

(def tab-desc
  [:<>
   [:p
    "The " [:strong "tab"] " component provides a basic horizontal bar of "
    "navigation links. The " [:code "sid"] " argument is a storage identifier keyword "
    "used to determine where the state of the tab bar is stored within the global "
    "document model store "]
   [:p
    "The " [:code "tabs"] " argument is a vector of tab definition maps. See the "
    [:code "deftab"] " function for a way to create the definition maps and a description of "
    "the supported keys"]])

(def tab-args [["sid" "A storage identifier keyword. Defines where whithin the global data store, state information about this tab bar is recorded"]
               ["tabs" "A vector of tab definition maps. See the deftab function for details on creating these maps"]])

(def tab-opt-args
  [[":position" "Specifies the horizontal position of the tab bar. Possible values are :center, :right and :left. Defaults to :left"]
   [":class" "A string or vector of strings specifying CSS classes to add to the tab bar container"]
   [":size" "Specifies the size for the tab bar. Possible values are :small, :medium and :large. Defaults to :medium"]
   [":boxed?" "If true, put borders around tabs for traditional boxed look"]
   [":toggle?" "If true, tabs act like radio buttons when clicked"]
   [":rounded?" "If true and :boxed? is true, use rounded corners on borders"]
   [":fullwidth?" "If true, make the tab bar the full width of the enclosing container"]])

(def tab-examples
  [{:text  "
  (let [tabs [(t/deftab \"Tab 1\" :value :tab1)
              (t/deftab \"Tab 2\" :value :tab2)
              (t/deftab \"Tab 3\" :value :tab3)]]
       [:<>
        [:p \"Example 1\"]
        [t/tab-bar :example.bar1 tabs]
        [:p (str \"Selected: \" (get-in (spath :example.bar1)))]
        [:p \"Example 2\"]
        [t/tab-bar :example.bar2 tabs :position :right]
        [:p (str \"Selected: \" (get-in (spath :example.bar2)))]
        [:p \"Example 3\"]
        [t/tab-bar :example.bar3 tabs :boxed true]
        [:p (str \"Selected: \" (get-in (spath :example.bar3)))]
        [:p \"example 4\"]
        [t/tab-bar :example.bar4 tabs :boxed true :toggle true :position :right]
        [:p (str \"Selected: \" (get-in (spath :example.bar4)))]
        [:p \"example 5\"]
        [t/tab-bar :example.bar5 tabs :boxed true :toggle true :rounded true
         :position :center]
        [:p (str \"Selected: \" (get-in (spath :example.bar5)))]])"
    :code (fn []
    (let [tabs [(t/deftab "Tab 1" :id :tab1)
                 (t/deftab "Tab 2" :value :tab2)
                 (t/deftab "Tab 3" :value :tab3)]]
       [:<>
        [:p "Example 1"]
        [t/tab-bar :example.bar1 tabs]
        [:p (str "Selected: " (get-in (spath :example.bar1)))]
        [:p "Example 2"]
        [t/tab-bar :example.bar2 tabs :position :right]
        [:p (str "Selected: " (get-in (spath :example.bar2)))]
        [:p "Example 3"]
        [t/tab-bar :example.bar3 tabs :boxed true]
        [:p (str "Selected: " (get-in (spath :example.bar3)))]
        [:p "example 4"]
        [t/tab-bar :example.bar4 tabs :boxed true :toggle true :position :right]
        [:p (str "Selected: " (get-in (spath :example.bar4)))]
        [:p "example 5"]
        [t/tab-bar :example.bar5 tabs :boxed true :toggle true :rounded true
         :position :center]
        [:p (str "Selected: " (get-in (spath :example.bar5)))]]))}])

(defn tab-page []
  [doc-fn {:title tab-title
           :desc tab-desc
           :args tab-args
           :opt-args tab-opt-args
           :examples tab-examples}])

(def ns-doc
  [:<>
   [:p
     "The " [:strong "theophilusx.yorick.tab"] " namespace provides support "
     "for a horizontal tab navigation bar. The component supports flat modern "
     "and traditional boxed style tab bars, can be aligned to the left, centre "
     "or right of the page and is available in three different sizes."]
    [:p
     "The " [:code "tab"] " component tracks the current selected tab in the "
     "global state atom defined by " [:code "theophilusx.yorick.store/global-state"]
     " under the key specified by the " [:code "sid"] " argument, allowing other "
     "components and ClojureScript code to react to tab changes. A convenience "
     "function " [:code "deftab"] " is provided to support the definition of "
     "tab bar navigation tabs."]])

(def ns-pages [{:title "deftab"
                :value :deftab
                :page deftab-page}
               {:title "tab"
                :value :tab
                :page tab-page}])

(defn ns-page []
  [doc-ns :ui.sidebar.tab-ns "The Tab Component" ns-doc ns-pages])


