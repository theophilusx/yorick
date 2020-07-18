(ns yorick-demo.tabs
  (:require [theophilusx.yorick.tab :as t]
            [theophilusx.yorick.utils :refer [spath]]
            [theophilusx.yorick.card :as c]
            [theophilusx.yorick.store :refer [get-in global-state]]
            [theophilusx.yorick.basic :as b]))

(defn deftab-function []
  [:div.columns
   [:div.column.is-half
    [:h4.title.is-4 "Description"]
    [c/card
     [:div.content
      [:p
       "The " [:strong "deftab"] " function is a convenience function for "
       "defining tab elements for a horizontal navigation bar. The tab "
       "definitions are a map with specific keys. The " [:code "title"]
       " argument is the text to appear in the tab. The function supports the "
       "following optional keyword arguments:"]
      [:ul
       [:li [:strong ":id"] " - a unique value to be used as the id for a tab. "
        "When a tab is selected, this value is set in the global document model "
        "store under the storage identifier key associated with the tab bar. "
        "If not specified, the " [:code "title"] " argument, converted to a "
        "keyword is used as the default."]
       [:li [:strong ":icon-data"] " - an icon data map defining an icon to be "
        "added to the tab. See " [:code "theophilusx.yorick.icon"] " for "
        "details on the structure of icon data maps."]
       [:li [:strong ":class"] " - a string or vector of strings specifying "
        "CSS class names to be added to the tab."]]]
     :header {:title "deftab - convenience function for defining tabs"}]]
   [:div.column
    [:h4.title.is-4 "Example"]
    [:pre
     [:code
      "(let [tabs [(t/deftab \"Tab 1\" :id :tab1)" [:br]
      "            (t/deftab \"Tab 2\" :id :tab2)" [:br]
      "            (t/deftab \"Tab 3\" :id :tab3)]]" [:br]
      "  [:<>" [:br]
      "    [:p \"Example tab definitions\"]" [:br]
      "    [b/render-vec tabs]])"]]
    [:div.box
     (let [tabs [(t/deftab "Tab 1" :id :tab1)
                 (t/deftab "Tab 2" :id :tab2)
                 (t/deftab "Tab 3" :id :tab3)]]
       [:<>
        [:p "Example tab definitions"]
        [b/render-vec tabs]])]]])

(defn tab-component []
  [:div.columns
   [:div.column.is-half
    [:h4.title.is-4 "Description"]
    [c/card
     [:div.content
      [:p
       "The " [:strong "tab"] " component provides a basic horizontal bar of "
       "links. The " [:code "sid"] " argument is a storage identifier keyword "
       "used to determine where the selected menu id is stored within the global "
       "document model store defined by "
       [:code "theophilusx.yorick.store/global-state"] ". The " [:code "tabs"]
       " argument is a vector of tab definition maps. See the " [:code "deftab"]
       " function for a way to create the definition maps and a description of "
       "the supported keys. The component supports a number of optional keyword "
       "arguments for configuring the presentation of the tab bar:"]
      [:ul
       [:li [:strong ":position"] " - set the alignment of the tab bar. Allowed "
        "values are " [:code ":center"] " for a centred tab bar and "
        [:code ":right"] " for a right aligned tab bar. Default is left aligned."]
       [:li [:strong ":class"] " - a string or vector of strings specifying CSS "
        "class names to add to the tab bar."]
       [:li [:strong ":size"] " - set the size of the tab bar. Possible values "
        "are " [:code ":small, :medium"] " and " [:code ":large"]]
       [:li [:strong ":boxed"] " - if true, use a traditional box based tab bar."]
       [:li [:strong ":rounded"] " - if true, render the left and right most tabs "
        "with rounded outer edge and corners."]]]
     :header {:title "tab - a horizontal tab navigation bar component"}]]
   [:div.column
    [:h4.title.is-4 "Example"]
    [:pre
     [:code
      "(let [tabs [(t/deftab \"Tab 1\" :id :tab1)" [:br]
      "            (t/deftab \"Tab 2\" :id :tab2)" [:br]
      "            (t/deftab \"Tab 3\" :id :tab3)]]" [:br]
      "  [:<>" [:br]
      "    [:p \"Example 1\"]" [:br]
      "    [t/tab :example.bar1 tabs]" [:br]
      "    [:p (str \"Selected: \" (get-in global-state (spath :example.bar1)))]" [:br]
      "    [:p \"Example 2\"]" [:br]
      "    [t/tab :example.bar2 tabs :position :right]" [:br]
      "    [:p (str \"Selected: \" (get-in global-state (spath :example.bar2)))]" [:br]
      "    [:p \"Example 3\"]" [:br]
      "    [t/tab :example.bar3 tabs :boxed true]" [:br]
      "    [:p (str \"Selected: \" (get-in global-state (spath :example.bar3)))]" [:br]
      "    [:p \"Example 4\"]" [:br]
      "    [t/tab :example.bar4 tabs :boxed true :rounded true]" [:br]
      "    [:p (str \"Selected: \" (get-in global-state (spath :example.bar4)))]])"]]
    [:div.box
     (let [tabs [(t/deftab "Tab 1" :id :tab1)
                 (t/deftab "Tab 2" :id :tab2)
                 (t/deftab "Tab 3" :id :tab3)]]
       [:<>
        [:p "Example 1"]
        [t/tab :example.bar1 tabs]
        [:p (str "Selected: " (get-in global-state (spath :example.bar1)))]
        [:p "Example 2"]
        [t/tab :example.bar2 tabs :position :right]
        [:p (str "Selected: " (get-in global-state (spath :example.bar2)))]
        [:p "Example 3"]
        [t/tab :example.bar3 tabs :boxed true]
        [:p (str "Selected: " (get-in global-state (spath :example.bar3)))]
        [:p "example 4"]
        [t/tab :example.bar4 tabs :boxed true :rounded true :position :center]
        [:p (str "Selected: " (get-in global-state (spath :example.bar4)))]])]]])

(defn tabs-page []
  [:<>
   [:div.content
    [:h2.title.is-2 "The Tab Component"]
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
     "tab bar navigation tabs."]]
   [:hr]
   [t/tab :ui.tabs.tab-page [(t/deftab "deftab Function" :id :deftab)
                             (t/deftab "tab Component" :id :tab)]]
   (case (get-in global-state (spath :ui.tabs.tab-page))
     :deftab [deftab-function]
     :tab [tab-component]
     [deftab-function])])
