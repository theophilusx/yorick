(ns yorick-demo.icons
  (:require [theophilusx.yorick.icon :as i]
            [theophilusx.yorick.basic :as b]
            [yorick-demo.comp :refer [doc-fn doc-ns]]))

;; deficon

(def deficon-title "deficon - a convenience function to make icon definition maps")

(def deficon-desc
  [:p "The " [:strong "deficon"] " function is a convenience function to 
       assist in defining the icon definition map. The " [:code "name"]
       " argument is the name of a Font Awesome icon. The function returns 
       an icon definition " [:code "map"] ". A number of optional keyword 
       arguments are available for further customisation of the definition map"])

(def deficon-args [["name" "Name of a font awesome icon"]])

(def deficon-opt-args
  [[":position" "Set the position of the icon within the enclosing element. Possible values are :left or :right"]
   [":size" "Set the size of the icon. Possible values are :small, :medium and :large"]
   [":icon-class" "A string or vector of strings specifying additional CSS class names to add to the element."]
   [":span-class" "A string or vector of strings specifying additional CSS class names to add the enclosing <span> element."]])

(def deficon-examples
  [{:text  "
(let [icon-data (icon/deficon \"fas fa-image\")]
  [:p (str \"Definition: \" icon-data)])"
    :code (fn []
            (let [icon-data (i/deficon "fas fa-image")]
              [:p (str "Definition: " icon-data)]))}])

(defn deficon-page []
  [doc-fn {:title deficon-title
           :desc deficon-desc
           :args deficon-args
           :opt-args deficon-opt-args
           :examples deficon-examples}])

;; icon-control-class

(def icc-title "icon-control-class - a convenience function for icon CSS classes")

(def icc-desc
  [:p "The " [:strong "icon-control-class"] " function is a convenience 
       function used to extract icon specific CSS class names from an icon 
       definition map or a vector of icon definition maps. This function is 
       useful when you need to add icon specific CSS class names to an enclosing 
       element containing icons. The " [:code "icon-data"] " argument is either 
       an icon definition map or a vector of icon definition maps. The function 
       returns a string which can be added to the " [:code ":class"] " attribute 
       of an enclosing element or component."])

(def icc-args [["icon-data" "An icon data map as created by the deficon function"]])

(def icc-examples
  [{:text  "
(let [icon-data (icon/deficon \"fas fa-image\" :size :medium
                  :position :right)]
  [:p (str \"CSS Classes: \" (icon-control-class icon-data))])"
    :code (fn []
            (let [icon-data (i/deficon "fas fa-image" :size :medium :position :right)]
              [:p (str "CSS Classes: " (i/icon-control-class icon-data))]))}])

(defn icon-control-class-page []
  [doc-fn {:title icc-title
           :desc icc-desc
           :args icc-args
           :opt-args nil
           :examples icc-examples}])

(def icon-title "icon - render an icon")

(def icon-desc
  [:p "The " [:strong "icon"] " component generates an icon component 
       which can be added as content for another component or rendered on its own. 
       the " [:code "icon-data"] " argument is an icon definition map. See the "
       [:code "deficon"] " function for a way to generate icon definition maps 
       and a description of the supported keys."])

(def icon-args [["icon-data" "An icon data map as generated by the deficon function"]])

(def icon-examples
  [{:text  "[icon/icon (icon/deficon \"fas fa-warehouse\")]"
    :code (fn []
            [i/icon (i/deficon "fas fa-warehouse")])}
   {:text  "
[:p [icon/icon (icon/deficon \"fas fa-warehouse\"
                          :size :small)]
  \" Small\"]
[:p [icon/icon (icon/deficon \"fas fa-warehouse\")]
  \" Normal\"]
[:p [icon/icon (icon/deficon \"fas fa-warehouse\"
                          :size :medium)]
  \" Medium\"]
[:p [icon/icon (icon/deficon \"fas fa-warehouse
                            :size :large)]
  \" Large\"]
[:p [icon/icon (icon/deficon \"fas fa-warehouse\"
                            :size :huge)]
  \" Huge\"]"
    :code (fn []
            [:<>
             [:p [i/icon (i/deficon "fas fa-warehouse" :size :small)]
              [:span "Small"]]
             [:p [i/icon (i/deficon "fas fa-warehouse")]
              [:span "Normal"]]
             [:p [i/icon (i/deficon "fas fa-warehouse" :size :medium)]
              [:span "Medium"]]
             [:p [i/icon (i/deficon "fas fa-warehouse" :size :large)]
              [:span "Large"]]
             [:p [i/icon (i/deficon "fas fa-warehouse" :size :huge)]
              [:span "Huge"]]])}
   {:text  "
[:p [icon/icon (icon/deficon \"fas fa-home\"
                          :span-class \"has-text-info\")]
  \"Info Colour\"]
[:p [icon/icon (icon/deficon \"fas fa-home\")]
  \"Normal Colour]
[:p [icon/icon (icon/deficon \"fas fa-home\")
                          :span-class \"has-text-success\")]
  \"Success Colour\"]
[:p [icon/icon (icon/deficon \"fas fa-home\"
                          :span-class \"has-text-warning\")]
  \"Warning Colour\"]
[:p [icon/icon (icon/deficon \"fas fa-home\"
                          :span-class \"has-text-danger\")]
  \"Danger Colour\"]"
    :code (fn []
            [:<>
             [:p [i/icon (i/deficon "fas fa-warehouse"
                                     :span-class "has-text-info")]
              "Info Colour"]
             [:p [i/icon (i/deficon "fas fa-warehouse")]
              "Normal Colour"]
             [:p [i/icon (i/deficon  "fas fa-warehouse"
                                     :span-class "has-text-success")]
              "Success Colour"]
             [:p [i/icon (i/deficon "fas fa-warehouse"
                                     :span-class "has-text-warning")]
              "Warning Colour"]
             [:p [i/icon (i/deficon "fas fa-warehouse"
                                     :span-class "has-text-danger")]
              "Danger Colour"]])}])

(defn icon-page []
  [doc-fn {:title icon-title
           :desc icon-desc
           :args icon-args
           :opt-args nil
           :examples icon-examples}])

(def icons-title  "icons - multiple icon rendering component")

(def icons-desc
  [:p "The " [:strong "icons"] " function provides a method for generating 
       multiple icons. The " [:code "icon-data"] " is a vector of icon definition 
       maps. See the function " [:code "deficon"] " for a way to generate icon 
       definition maps and a description of the supported keys. The function 
       returns a vector of icon components."])

(def icons-args [["icon-data" "Either an icon-data map or a vector of icon-data maps"]])

(def icons-examples
  [{:text  "
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
       i)])"
    :code (fn []
            (let [data [(i/deficon "fas fa-home" :span-class "has-text-info")
                        (i/deficon "fas fa-home" :span-class "has-text-link")
                        (i/deficon "fas fa-home" :span-class "has-text-success")
                        (i/deficon "fas fa-home" :span-class "has-text-warning")
                        (i/deficon "fas fa-home" :span-class "has-text-danger")]]
              [:<>
               [:p "The icons " ]
               (for [i (i/icons data)] i)]))}])

(defn icons-page []
  [doc-fn {:title icons-title
           :desc icons-desc
           :args icons-args
           :opt-args nil
           :examples icons-examples}])

(def ns-sid :ui.sidebar.icons-ns)

(def ns-title  "The Icon Component")

(def ns-desc
  [:<>
   [:p "The " [:strong "theophilusx.yorick.icon"] " namespace provides an icon 
         component and associated helper functions that can be used to add "
    [b/a "Font Awesome" :href "https://fontawesome.com"] " icons to components. "]
   [:p "The basic data structure for working with icons is a " [:code "map"] 
    ". The icon map can be created using the " [:code "deficon"] " function."]
   [:p "When adding icons to some components, it is necessary to add specific 
         CSS classes to parent elements to enable appropriate spacing and alignment 
         of icons. The " [:code "icon-control-class"] " function is provided to 
         meet this requirement."]
   [:p "The " [:code "icon"] " component is provided for adding a single 
        icon to rendered output. The " [:code "icons"] " component is used for 
        adding multiple icons to a component."]])

(def ns-pages [{:title "deficon"
                :value :deficon
                :page deficon-page}
               {:title "icon-control-class"
                :value :icon-control-class
                :page icon-control-class-page}
               {:title "icon"
                :value :icon
                :page icon-page}
               {:title "icons"
                :value :icons
                :page icons-page}])

(defn ns-page []
  [doc-ns ns-sid ns-title ns-desc ns-pages])

