(ns yorick-demo.icons
  (:require [theophilusx.yorick.icon :as i]
            [theophilusx.yorick.card :as c]
            [theophilusx.yorick.basic :as b]
            [theophilusx.yorick.tab :as t]
            [theophilusx.yorick.store :refer [get-in global-state]]
            [theophilusx.yorick.utils :refer [spath]]))

(defn deficon-function []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "deficon"] " function is a convenience function to "
       "assist in defining the icon definition map. The " [:code "name"]
       " argument is the name of a Font Awesome icon. The function returns "
       "an icon definition " [:code "map"] ". A number of optional keyword "
       "arguments are available for further customisation of the definition map"]
      [:ul
       [:li [:strong ":position"] " - set the position of the icon within the "
        "enclosing element. Possible values are " [:code ":left"] " and "
        [:code ":right"]]
       [:li [:strong ":size"] " - set the size of the icon. Possible values "
        "are " [:code ":small, :medium"] " and " [:code ":large"]]
       [:li [:strong ":icon-class"] " - a string or vector of strings specifying "
        "additional CSS class names to add to the element."]
       [:li [:strong ":span-class"] " - a string or vector of strings specifying "
        "additional CSS class names to add the enclosing " [:code "<span>"]
        " element"]]]
     :header {:title "deficon - a convenience function to make icon definition maps"}]]
   [:div.column
    [:pre
     [:code
      "(let [icon-data (icon/deficon \"fas fa-image\")]" [:br]
      "  [:p (str \"Definition: \" icon-data)])"]]
    [:div.box
     (let [icon-data (i/deficon "fas fa-image")]
       [:p (str "Definition: " icon-data)])]]])

(defn icon-control-class-function []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "icon-control-class"] " function is a convenience "
       "function used to extract icon specific CSS class names from an icon "
       "definition map or a vector of icon definition maps. This function is "
       "useful when you need to add icon specific CSS class names to an enclosing "
       "element containing icons. The " [:code "icon-data"] " argument is either "
       "an icon definition map or a vector of icon definition maps. The function "
       "returns a string which can be added to the " [:code ":class"] " attribute "
       "of an enclosing element or component."]]
     :header {:title "icon-control-class - a convenience function for icon CSS classes"}]]
   [:div.column
    [:pre
     [:code
      "(let [icon-data (icon/deficon \"fas fa-image\" :size :medium " [:br]
      "                  :position :right)]" [:br]
      "  [:p (str \"CSS Classes: \" (icon-control-class icon-data))])"]]
    [:div.box
     (let [icon-data (i/deficon "fas fa-image" :size :medium :position :right)]
       [:p (str "CSS Classes: " (i/icon-control-class icon-data))])]]])

(defn icon-component-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "icon-component"] " component generates an icon component "
       "which can be added as content for another component or rendered on its own. "
       "the " [:code "icon-data"] " argument is an icon definition map. See the "
       [:code "deficon"] " function for a way to generate icon definition maps "
       "and a description of the supported keys."]]
     :header {:title "icon-component - render an icon"}]]
   [:div.column
    [:pre
     [:code
      "[:h6.title.is-6 \"Result\"]" [:br]
      "[icon/icon-component (icon/deficon \"fas fa-warehouse\")]"]]
    [:div.box
     [:h6.title.is-6 "Result"]
     [i/icon-component (i/deficon "fas fa-warehouse")]]
    [:pre
     [:code
      "[:p [icon/icon-component (icon/deficon \"fas fa-warehouse\" " [:br]
      "                            :size :small)]" [:br]
      "  \" Small\"]" [:br]
      "[:p [icon/icon-component (icon/deficon \"fas fa-warehouse\")]" [:br]
      "  \" Normal]" [:br]
      "[:p [icon/icon-component (icon/deficon \"fas fa-warehouse\" " [:br]
      "                            :size :medium)]" [:br]
      "  \" Medium\"]" [:br]
      "[:p [icon/icon-component (icon/deficon \"fas fa-warehouse " [:br]
      "                            :size :large)]" [:br]
      "  \" Large\"]" [:br]
      "[:p [icon/icon-component (icon/deficon \"fas fa-warehouse\" " [:br]
      "                            :size :huge)]" [:br]
      "  \" Huge\"]"]]
    [:dir.box
     [:h6.title.is-6 "Result"]
     [:p [i/icon-component (i/deficon "fas fa-warehouse" :size :small)]
      [:span "Small"]]
     [:p [i/icon-component (i/deficon "fas fa-warehouse")]
      [:span "Normal"]]
     [:p [i/icon-component (i/deficon "fas fa-warehouse" :size :medium)]
      [:span "Medium"]]
     [:p [i/icon-component (i/deficon "fas fa-warehouse" :size :large)]
      [:span "Large"]]
     [:p [i/icon-component (i/deficon "fas fa-warehouse" :size :huge)]
      [:span "Huge"]]]
    [:pre
     [:code
      "[:p [icon/icon-component (icon/deficon \"fas fa-home\"" [:br]
      "                          :span-class \"has-text-info\")]" [:br]
      "  \"Info Colour\"]" [:br]
      "[:p [icon/icon-component (icon/deficon \"fas fa-home\")]" [:br]
      "  \"Normal Colour]" [:br]
      "[:p [icon/icon-component (icon/deficon \"fas fa-home\")" [:br]
      "                          :span-class \"has-text-success\")]" [:br]
      "  \"Success Colour\"]" [:br]
      "[:p [icon/icon-component (icon/deficon \"fas fa-home\"" [:br]
      "                          :span-class \"has-text-warning\")]" [:br]
      "  \"Warning Colour\"]" [:br]
      "[:p [icon/icon-component (icon/deficon \"fas fa-home\"" [:br]
      "                          :span-class \"has-text-danger\")]" [:br]
      "  \"Danger Colour\"]"]]
    [:dir.box
     [:h6.title.is-6 "Result"]
     [:p [i/icon-component (i/deficon "fas fa-warehouse"
                             :span-class "has-text-info")]
      "Info Colour"]
     [:p [i/icon-component (i/deficon "fas fa-warehouse")]
      "Normal Colour"]
     [:p [i/icon-component (i/deficon  "fas fa-warehouse"
                             :span-class "has-text-success")]
      "Success Colour"]
     [:p [i/icon-component (i/deficon "fas fa-warehouse"
                             :span-class "has-text-warning")]
      "Warning Colour"]
     [:p [i/icon-component (i/deficon "fas fa-warehouse"
                             :span-class "has-text-danger")]
      "Danger Colour"]]]])

(defn icons-function []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "icons"] " function provides a method for generating "
       "multiple icons. The " [:code "icon-data"] " is a vector of icon definition "
       "maps. See the function " [:code "deficon"] " for a way to generate icon "
       "definition maps and a description of the supported keys. The function "
       "returns a vector of icon components."]]
     :header {:title "icons - multiple icon rendering component"}]]
   [:div.column
    [:pre
     [:code
      "(let [data [(icon/deficon \"fas fa-home\" " [:br]
      "               :span-class \"has-text-info\")" [:br]
      "            (icon/deficon \"fas fa-home\" " [:br]
      "               :span-class \"has-text-link\")" [:br]
      "            (icon/deficon \"fas fa-home\" " [:br]
      "               :span-class \"has-text-success\")" [:br]
      "            (icon/deficon \"fas fa-home\" " [:br]
      "               :span-class \"has-text-warning\")" [:br]
      "            (icon/deficon \"fas fa-home\" " [:br]
      "               :span-class \"has-text-danger\")]]" [:br]
      "  [:<>" [:br]
      "    [:p \"The icons\"]" [:br]
      "    (for [i (i/icons data)]" [:br]
      "       i)])"]]
    [:div.box
     (let [data [(i/deficon "fas fa-home" :span-class "has-text-info")
                 (i/deficon "fas fa-home" :span-class "has-text-link")
                 (i/deficon "fas fa-home" :span-class "has-text-success")
                 (i/deficon "fas fa-home" :span-class "has-text-warning")
                 (i/deficon "fas fa-home" :span-class "has-text-danger")]]
       [:<>
        [:p "The icons " ]
        (for [i (i/icons data)]
          i)])]]])

(defn icon-page []
  [:<>
   [:div.content
    [:h2.title.is-2 "The Icon Component"]
    [:p
     "The " [:strong "theophilusx.yorick.icon"] " namespace provides an icon "
     "component and associated helper functions that can be used to add "
     [b/a "Font Awesome" :href "https://fontawesome.com"] " icons to components. "]
    [:p
     "The basic data structure for working with icons is a " [:code "map"] 
     ". The icon map can be created using the " [:code "deficon"] " function."]
    [:p
     "When adding icons to some components, it is necessary to add specific "
     "CSS classes to parent elements to enable appropriate spacing and alignment "
     "of icons. The " [:code "icon-control-class"] " function is provided to "
     "meet this requirement."]
    [:p
     "The " [:code "icon-component"] " component is provided for adding a single "
     "icon to rendered output. The " [:code "icons"] " component is used for "
     "adding multiple icons to a component."]]
   [:hr]
   [t/tab :ui.tabs.icon-page [(t/deftab "deficon" :id :deficon)
                              (t/deftab "icon-control-class" :id :icon-class)
                              (t/deftab "icon-component" :id :icon-component)
                              (t/deftab "icons" :id :icons)]
    :size :medium :position :center]
   (case (get-in global-state (spath :ui.tabs.icon-page))
     :deficon [deficon-function]
     :icon-class [icon-control-class-function]
     :icon-component [icon-component-component]
     :icons [icons-function]
     [deficon-function])])

