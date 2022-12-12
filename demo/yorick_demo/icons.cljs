(ns yorick-demo.icons
  (:require [theophilusx.yorick.icon :as i]
            [theophilusx.yorick.basic :as b]
            [theophilusx.yorick.tab :as t]
            [theophilusx.yorick.store :refer [cursor]]
            [theophilusx.yorick.utils :refer [spath]]
            [yorick-demo.comp :refer [doc-comp example]]))

(def deficon-eg "
(let [icon-data (icon/deficon \"fas fa-image\")]
  [:p (str \"Definition: \" icon-data)])")

(defn deficon-function []
  [:div.columns
   [:div.column
    [doc-comp "deficon - a convenience function to make icon definition maps"
     [:p "The " [:strong "deficon"] " function is a convenience function to 
       assist in defining the icon definition map. The " [:code "name"]
       " argument is the name of a Font Awesome icon. The function returns 
       an icon definition " [:code "map"] ". A number of optional keyword 
       arguments are available for further customisation of the definition map"]
     [[":position" "Set the position of the icon within the enclosing element. Possible values are :left or :right"]
      [":size" "Set the size of the icon. Possible values are :small, :medium and :large"]
      [":icon-class" "A string or vector of strings specifying additional CSS class names to add to the element."]
      [":span-class" "A string or vector of strings specifying additional CSS class names to add the enclosing <span> element."]]]]
   [:div.column
    [example deficon-eg (let [icon-data (i/deficon "fas fa-image")]
                          [:p (str "Definition: " icon-data)])]]])

(def icon-control-eg "
(let [icon-data (icon/deficon \"fas fa-image\" :size :medium
                  :position :right)]
  [:p (str \"CSS Classes: \" (icon-control-class icon-data))])")

(defn icon-control-class-function []
  [:div.columns
   [:div.column
    [doc-comp "icon-control-class - a convenience function for icon CSS classes"
     [:p "The " [:strong "icon-control-class"] " function is a convenience 
       function used to extract icon specific CSS class names from an icon 
       definition map or a vector of icon definition maps. This function is 
       useful when you need to add icon specific CSS class names to an enclosing 
       element containing icons. The " [:code "icon-data"] " argument is either 
       an icon definition map or a vector of icon definition maps. The function 
       returns a string which can be added to the " [:code ":class"] " attribute 
       of an enclosing element or component."] nil]]
   [:div.column
    [example icon-control-eg (let [icon-data (i/deficon "fas fa-image" :size :medium :position :right)]
                               [:p (str "CSS Classes: " (i/icon-control-class icon-data))])]]])
(def icon-comp-eg1 "
[:p [icon/icon-component (icon/deficon \"fas fa-warehouse\"
                          :size :small)]
  \" Small\"]
[:p [icon/icon-component (icon/deficon \"fas fa-warehouse\")]
  \" Normal\"]
[:p [icon/icon-component (icon/deficon \"fas fa-warehouse\"
                          :size :medium)]
  \" Medium\"]
[:p [icon/icon-component (icon/deficon \"fas fa-warehouse
                            :size :large)]
  \" Large\"]
[:p [icon/icon-component (icon/deficon \"fas fa-warehouse\"
                            :size :huge)]
  \" Huge\"]")

(def icon-comp-eg2 "
[:p [icon/icon-component (icon/deficon \"fas fa-home\"
                          :span-class \"has-text-info\")]
  \"Info Colour\"]
[:p [icon/icon-component (icon/deficon \"fas fa-home\")]
  \"Normal Colour]
[:p [icon/icon-component (icon/deficon \"fas fa-home\")
                          :span-class \"has-text-success\")]
  \"Success Colour\"]
[:p [icon/icon-component (icon/deficon \"fas fa-home\"
                          :span-class \"has-text-warning\")]
  \"Warning Colour\"]
[:p [icon/icon-component (icon/deficon \"fas fa-home\"
                          :span-class \"has-text-danger\")]
  \"Danger Colour\"]")

(defn icon-component-component []
  [:div.columns
   [:div.column
    [doc-comp "icon-component - render an icon"
     [:p "The " [:strong "icon-component"] " component generates an icon component 
       which can be added as content for another component or rendered on its own. 
       the " [:code "icon-data"] " argument is an icon definition map. See the "
       [:code "deficon"] " function for a way to generate icon definition maps 
       and a description of the supported keys."] nil]]
   [:div.column
    [example "[icon/icon-component (icon/deficon \"fas fa-warehouse\")]"
     [i/icon-component (i/deficon "fas fa-warehouse")]]
    [example icon-comp-eg1
     [:<>
      [:p [i/icon-component (i/deficon "fas fa-warehouse" :size :small)]
       [:span "Small"]]
      [:p [i/icon-component (i/deficon "fas fa-warehouse")]
       [:span "Normal"]]
      [:p [i/icon-component (i/deficon "fas fa-warehouse" :size :medium)]
       [:span "Medium"]]
      [:p [i/icon-component (i/deficon "fas fa-warehouse" :size :large)]
       [:span "Large"]]
      [:p [i/icon-component (i/deficon "fas fa-warehouse" :size :huge)]
       [:span "Huge"]]]]
    [example icon-comp-eg2
     [:<>
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
      "Danger Colour"]]]]])

(def icon-fn-eg1 "
(let [data [(icon/deficon \"fas fa-home\"  
               :span-class \"has-text-info\") 
            (icon/deficon \"fas fa-home\" 
               :span-class \"has-text-link\") 
            (icon/deficon \"fas fa-home\"
               :span-class \"has-text-success\")
            (icon/deficon \"fas fa-home\"
               :span-class \"has-text-warning\")
            (icon/deficon \"fas fa-home\"
               :span-class \"has-text-danger\")]]
  [:<>
    [:p \"The icons\"]
    (for [i (i/icons data)]
       i)])")

(defn icons-function []
  [:div.columns
   [:div.column
    [doc-comp "icons - multiple icon rendering component"
     [:p "The " [:strong "icons"] " function provides a method for generating 
       multiple icons. The " [:code "icon-data"] " is a vector of icon definition 
       maps. See the function " [:code "deficon"] " for a way to generate icon 
       definition maps and a description of the supported keys. The function 
       returns a vector of icon components."] nil]]
   [:div.column
    [example icon-fn-eg1 (let [data [(i/deficon "fas fa-home" :span-class "has-text-info")
                                     (i/deficon "fas fa-home" :span-class "has-text-link")
                                     (i/deficon "fas fa-home" :span-class "has-text-success")
                                     (i/deficon "fas fa-home" :span-class "has-text-warning")
                                     (i/deficon "fas fa-home" :span-class "has-text-danger")]]
                           [:<>
                            [:p "The icons " ]
                            (for [i (i/icons data)]
                              i)])]]])

(defn icon-page []
  (let [tab-cur (cursor (spath :ui.tabs.icon-page.active-tab))]
    (fn []
      [:<>
       [:div.content
        [:h2.title.is-2 "The Icon Component"]
        [:p "The " [:strong "theophilusx.yorick.icon"] " namespace provides an icon 
         component and associated helper functions that can be used to add "
         [b/a "Font Awesome" :href "https://fontawesome.com"] " icons to components. "]
        [:p "The basic data structure for working with icons is a " [:code "map"] 
         ". The icon map can be created using the " [:code "deficon"] " function."]
        [:p "When adding icons to some components, it is necessary to add specific 
         CSS classes to parent elements to enable appropriate spacing and alignment 
         of icons. The " [:code "icon-control-class"] " function is provided to 
         meet this requirement."]
        [:p "The " [:code "icon-component"] " component is provided for adding a single 
        icon to rendered output. The " [:code "icons"] " component is used for 
        adding multiple icons to a component."]]
       [:hr]
       [t/tab-bar :ui.tabs.icon-page [(t/deftab "deficon" :value :deficon)
                                      (t/deftab "icon-control-class" :value :icon-class)
                                      (t/deftab "icon-component" :value :icon-component)
                                      (t/deftab "icons" :value :icons)]
        :position :centered]
       (case @tab-cur
         :deficon        [deficon-function]
         :icon-class     [icon-control-class-function]
         :icon-component [icon-component-component]
         :icons          [icons-function]
         [deficon-function])])))

