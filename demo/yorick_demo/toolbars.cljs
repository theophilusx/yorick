(ns yorick-demo.toolbars
  (:require [theophilusx.yorick.toolbar :as t]
            [theophilusx.yorick.card :as c]))

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
   ])

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
   [:div.columns
    [:div.column.is-half
     [:h4.title.is-4 "Description"]]
    [:div.column
     [:h4.title.is-4 "Example"]]]
   [deftoolbar-item-function]])

