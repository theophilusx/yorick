(ns yorick-demo.toolbars
  (:require [theophilusx.yorick.toolbar :as t]
            [theophilusx.yorick.card :as c]
            [theophilusx.yorick.input :as i]
            [theophilusx.yorick.icon :as icons]
            [theophilusx.yorick.basic :as b]
            [theophilusx.yorick.utils :refer [spath]]
            [theophilusx.yorick.tab :as tabs]
            [theophilusx.yorick.store :refer [get-in global-state]]))

(defn deftoolbar-item-function []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "deftoolbar-item"] " function is a convenience function "
       "to assist in the definition of toolbar items. A toolbar item can be any "
       "Hiccup markup or Reagent component and is defined using a " [:code "map"]
       " with 3 possible keys:"]
      [:ul
       [:li [:strong ":type"] " - the type of item. This value is a valid Hiccup "
        "tag and is used to wrap the item contents. If not specified, a "
        [:code ":div"] " tag is used."]
       [:li [:strong ":class"] " - string or vector of strings specifying CSS "
        "class names to be added to the element."]
       [:li [:strong ":content"] " - the content of the toolbar item. This can be "
        "a string, Hiccup markup or a Reagent component."]]]
     :header {:title "deftoolbar-item - a function to define toolbar items"}]]
   [:div.column.is-half
    [:pre
     [:code
      "(let [left [(deftoolbar-item " [:br]
      "              :contents [:p.subtitle.is-5 \"Some Text\"])" [:br]
      "            (deftoolbar-item " [:br]
      "              :content [button \"Do Something\"" [:br]
      "                         #(js/alert \"Do something cool\")" [:br]
      "                         :classses {:button \"is-success\"}])" [:br]
      "            (deftoolbar-item " [:br]
      "              :content " [:br]
      "                [search #(js/alert (str \"Searching for \" %))" [:br]
      "                   :button-text \"\"" [:br]
      "                   :icon-data (icons/deficon \"fa-search\")" [:br]
      "                   :classes" [:br]
      "                   {:button [\"has-background-link\"" [:br]
      "                                   \"has-text-white\"]}])]" [:br]
      "    right [(t/deftoolbar-item :content [:strong \"Bold\"])" [:br]
      "           (t/deftoolbar-item :content [:p \"Normal\"])]]" [:br]
      "  [:<>" [:br]
      "    [:p \"Left side toolbar items\"]" [:br]
      "    [b/render-vec left]" [:br]
      "    [:p \"right side toolbar items\"]" [:br]
      "    [b/render-vec right]])"]]
    (let [left  [(t/deftoolbar-item :contents [:p.subtitle.is-5 "Some Text"])
                (t/deftoolbar-item :content [i/button "Do Something"
                                             #(js/alert "Do something cool")
                                             :classses {:button "is-success"}])
                 (t/deftoolbar-item
                   :content [i/search #(js/alert (str "Searching for " %))
                             :button-text ""
                             :icon-data (icons/deficon "fa-search")
                             :classes
                             {:button ["has-background-link"
                                       "has-text-white"]}])]
          right [(t/deftoolbar-item :content [:strong "Bold"])
                 (t/deftoolbar-item :content [:p "Normal"])]]
      [:div.box
       [:p "Left side toolbar items"]
       [b/render-vec left]
       [:p "right side toolbar items"]
       [b/render-vec right]])]])

(defn toolbar-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "toolbar"] " component provides a simple horizontal "
       "toolbar which can contain text, Hiccup markup and other reagent components, "
       "such as a search box. The " [:code "data"] " argument is a " [:code "map"]
       " with the following keys:"]
      [:ul
       [:li [:strong ":left-items"] " - a vector of toolbar item definition maps "
        "for items to be placed on the left side of the toolbar. "
        "See the " [:code "deftoolbar-item"] " function for a way to generate "
        "the maps and a description of the map format."]
       [:li [:strong ":right-items"] " - a vector of tollbar item definition maps "
        "for the items to be placed on the right side of the toolbar. See the "
        [:code "deftoolbar-item"] " function for a way to generate these maps and "
        "a description of the map format."]
       [:li [:strong ":class"] " - a string or vector of strings specifying CSS "
        "class names for classes to be associated with the nav element which "
        "wraps the toolbar."]]]
     :header {:title "toolbar - a basic toolbar component"}]]
   [:div.column
    [:pre
     [:code
      "(let [left [(t/deftoolbar-item " [:br]
      "               :contents [:p.subtitle.is-5 \"Some Text\"])" [:br]
      "            (t/deftoolbar-item " [:br]
      "               :content [i/button \"Do Something\"" [:br]
      "                           #(js/alert \"Do something cool\")" [:br]
      "                           :classes {:button \"is-success\"}])" [:br]
      "            (t/deftoolbar-item " [:br]
      "               :content " [:br]
      "                  [i/search " [:br]
      "                     #(js/alert (str \"Searching for \" %))" [:br]
      "                     :button-text \"\"" [:br]
      "                     :icon-data (icons/deficon \"fa-search\")" [:br]
      "                     :classes" [:br]
      "                     {:button [\"has-background-link\"" [:br]
      "                               \"has-text-white\"]}])]" [:br]
      "    right [(t/deftoolbar-item :content [:strong \"Bold\"])" [:br]
      "           (t/deftoolbar-item :content [:p \"Normal\"])]]" [:br]
      "  [:<>" [:br]
      "    [:p \"An example toolbar\"]" [:br]
      "    [toolbar {:left-items left :right-items right}]])"]]
    [:div.box
     (let [left [(t/deftoolbar-item :content [:p.subtitle.is-5 "Some Text"])
                 (t/deftoolbar-item
                   :content [i/button "Do Something"
                             #(js/alert "Do something cool")
                             :classes {:button "has-background-success"}])
                 (t/deftoolbar-item
                   :content [i/search #(js/alert (str "Searching for " %))
                             :button-text ""
                             :icon-data (icons/deficon "fa-search")
                             :classes
                             {:button ["has-background-link"
                                       "has-text-white"]}])]
           right [(t/deftoolbar-item :content [:strong "Bold"])
                  (t/deftoolbar-item :content [:p "Normal"])]]
       [:<>
        [:p "An example toolbar"]
        [t/toolbar {:left-items left :right-items right}]])]]])

(defn toolbar-page []
  [:<>
   [:div.content
    [:h2.title.is-2 "The Toolbar Component"]
    [:p
     "The " [:strong "theophilusx.yorick.toolbar"] " namespace provides support "
     "for a toolbar component, a horizontal bar of action items to perform "
     "various actions. A toolbar can contain any Hiccup markup or another "
     "Reagent component."]]
   [:hr]
   [tabs/tab :ui.tabs.toolbar-page [(tabs/deftab "deftoolbar-item" :id :deftoolbar)
                                    (tabs/deftab "toolbar" :id :toolbar)]
    :position :center :size :medium]
   (case (get-in global-state (spath :ui.tabs.toolbar-page))
     :deftoolbar [deftoolbar-item-function]
     :toolbar [toolbar-component]
     [deftoolbar-item-function])])

