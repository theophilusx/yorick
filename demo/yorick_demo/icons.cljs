(ns yorick-demo.icons
  (:require [theophilusx.yorick.icon :as i]
            [theophilusx.yorick.card :as c]
            [theophilusx.yorick.basic :as b]))

(defn icon-example []
  [:div.columns
   [:div.column
    [c/card [:p
             "The " [:strong "theophilusx.yorick.icon/icon"] " function can "
             "be used to add a single icon or a list of icons specified in a "
             "vector of icon data maps."]
     :header {:title "icon-component Render an icon"}]]
   [:div.column
    [:pre
     [:code
      "[icon/icon-component {:name \"fa-house\"}]"]]
    [:div.box
     [:h6.title.is-6 "Result"]
     [i/icon-component {:name "fa-warehouse"}]]
    [:pre
     [:code
      "[:p [icon/icon-component {:name \"fa-warehouse\"" [:br]
      "                          :size :small}]" [:br]
      "  \" Small\"]" [:br]
      "[:p [icon/icon-component {:name \"fa-warehouse\"}]" [:br]
      "  \" Normal]" [:br]
      "[:p [icon/icon-component {:name \"fa-warehouse\"" [:br]
      "                          :size :medium}]" [:br]
      "  \"Medium\"]" [:br]
      "[:p [icon/icon-component {:name \"fa-warehouse\"" [:br]
      "                          :size :large}]" [:br]
      "  \"Large\"]" [:br]
      "[:p [icon/icon-component {:name \"fa-warehouse\"" [:br]
      "                          :size :huge}]" [:br]
      "  \"Huge\"]"]]
    [:dir.box
     [:h6.title.is-6 "Result"]
     [:p [i/icon-component {:name "fa-warehouse"
                            :size :small}]
      " Small"]
     [:p [i/icon-component {:name "fa-warehouse"}]
      " Normal"]
     [:p [i/icon-component {:name "fa-warehouse"
                            :size :medium}]
      " Medium"]
     [:p [i/icon-component {:name "fa-warehouse"
                            :size :large}]
      " Large"]
     [:p [i/icon-component {:name "fa-warehouse"
                            :size :huge}]
      " Huge"]]]])

(defn icon-page []
  [:<>
   [:h2.title.is-2 "Icon Component"]
   [:p
    "The " [:strong "theophilusx.yorick.icon"] " namespace provides an icon "
    "component and associated helper functions that can be used to add "
    [b/a "Font Awesome" :href "https://fontawesome.com"] " icons to components. "]
   [:p
    "The basic data structure for working with icons is a " [:em "map"] ". "
    "The icon map supports the following keys"]
   [:ul.list
    [:li [:strong ":name"] " The name of a Font Awesome icon"]
    [:li [:strong ":position"] " Specifies the position of the icon within "
     "the enclosing component. Possible values are " [:em ":left"] " or "
     [:em ":right"]]
    [:li [:strong ":size"] " Specify the size of the icon within the enclosing "
     "component. Can be one of " [:em ":small, :medium"] " or " [:em ":large"]]
    [:li [:strong ":icon-class"] " CSS class names to add to the " [:em "<i>"]
     " element"]
    [:li [:strong ":span-class"] " CSS class names to add to the "
     [:em "<span>"] " element."]]
   [:p
    "The function " [:strong "theophilusx.yorick.icon/deficon"] " is provided "
    "as a helper function to create icon data maps. It has one required "
    "argument for " [:em "name"] " and optional keyword arguments for the other "
    "keys of the icon map."]
   [:p
    "The " [:strong "theophilusx.yorick.icon/icon-component"] " function takes "
    "an icon data map as its argument. This is the main component function for "
    "adding an icon to the content. The function "
    [:strong "theophilusx.yorick.icon/icons"] " function is a helper function "
    "which can accept either an icon data map or a vector of icon data maps. "
    "It is a convenience function used to simplify handling of icon components "
    "by other components. The function returns a vector of icon components"]
   [:p
    "The " [:strong "theophilusx.yorick.icon/icon-control-class"] " function is "
    "a convenience function used to generate a CSS class string from a vector "
    "of icon data maps. In some situations, it is necessary to add icon position "
    "classes to the enclosing component that will hold the icons. This function "
    "provides a convenient means to generate that class list string. The "
    "function accepts either an icon data map or a vector of icon data maps"]
   [:hr]
   [:div.columns
    [:div.column]
    [:div.column
     [:h4.title.is-4 "Example"]]]
   [icon-example]])

