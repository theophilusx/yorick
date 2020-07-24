(ns yorick-demo.sidebars
  (:require [theophilusx.yorick.sidebar :as s]
            [theophilusx.yorick.card :as c]
            [theophilusx.yorick.basic :as b]
            [theophilusx.yorick.store :refer [get-in global-state]]
            [theophilusx.yorick.utils :refer [spath]]
            [theophilusx.yorick.tab :as t]))

(defn defsidebar-item-function []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:code "defsidebar-item"] " function is a convenience function "
       "provided to assist in the definition of sidebar menus and menu items. "
       "The function generates a " [:code "map"] " with the correct structure "
       "and keys."]
      [:p
       "A sidebar menu consists of a parent entry and a list of children, each "
       "of which defines a menu entry or a sub-menu entry. The function can be "
       "used to define both the top level menu, child menus and sub-menus."]
      [:p
       "The following keys are supported:"]
      [:ul
       [:li [:strong ":type"] " - the type of entry. Acceptable values are "
        [:code ":item"] ", representing a menu choice link and " [:code ":menu"]
        " to represent a new top menu or sub-menu."]
       [:li [:strong ":title"] " - a string specifying the menu title for either "
        "a top level menu or sub-menu."]
       [:li [:strong ":href"] " - a hypertext URL for menus which link to an "
        "external resource."]
       [:li [:strong ":id"] " - a unique value to use as the id associated with "
        "a menu item. This value will be recorded in the global state atom."]
       [:li [:strong ":icon-data"] " - an icon data definition map to add an "
        "icon to the menu item. See " [:code "theophilusx.yorick.icons"] " for "
        "details."]
       [:li [:strong ":class"] " - a string or vector of strings specifying "
        "CSS class names to be added to this item"]
       [:li [:strong ":items"] " - Only valid when the type is " [:code ":menu"]
        ". Is a vector of item map definitions representing the sub-menu items"]]]
     :header {:title "defsidemenu-item - a menu definition helper function"}]]
   [:div.column
    [:pre
     [:code
      "(let [sb-def {:sid :sb-example" [:br]
      "              :default-link :menu1" [:br]
      "              :data (defsidebar-item :type :menu " [:br]
      "                       :title \"Example Menu\"" [:br]
      "                       :items [(defsidebar-item :title \"Menu 1\"" [:br]
      "                                  :id :menu1)" [:br]
      "                               (defsidebar-item :title \"Menu 2\"" [:br]
      "                                  :id :menu2)" [:br]
      "                               (defsidebar-item :title \"Menu 3\"" [:br]
      "                                  :id :menu3)" [:br]
      "                               (defsidebar-item :type :menu " [:br]
      "                                  :title \"Sub-Menu Example\"" [:br]
      "                                  :items [(defsidebar-item " [:br]
      "                                             :title \"Sub-menu 1\"" [:br]
      "                                             :id :sub-menu1)" [:br]
      "                                          (defsidebar-item" [:br]
      "                                             :title \"Sub-menu 2\"" [:br]
      "                                             :id :sub-menu2)])])}]" [:br]
      "  [:<>" [:br]
      "    [:p \"Definition:\"]" [:br]
      "    [b/render-map sb-def]])"]]
    [:div.box
     (let [sb-def {:sid :sb-example
                   :default-link :menu1
                   :data (s/defsidebar-item :type :menu :title "Example Menu"
                           :items [(s/defsidebar-item :title "Menu 1" :id :menu1)
                                   (s/defsidebar-item :title "Menu 2" :id :menu2)
                                   (s/defsidebar-item :title "Menu 3" :id :menu3)
                                   (s/defsidebar-item :type :menu
                                     :title "Sub-menu Example"
                                     :items [(s/defsidebar-item
                                               :title "Sub-menu 1"
                                               :id :sub-menu1)
                                             (s/defsidebar-item
                                               :title "Sub-menu 2"
                                               :id :sub-menu2)])])}]
       [:<>
        [:p "Definition:"]
        [b/render-map sb-def]])]]])

(defn sidebar-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "sidebar"] " component provides a vertical list of menus "
       "and sub-menus. The " [:code "data"] " argument is a " [:code "map"]
       " which defines the sidebar. The following keys are expected to be "
       "present in the map:"]
      [:ul
       [:li [:strong ":sid"] " - a storage identifier keyword used to determine "
        "the key within the global state atom defined by "
        [:code "theophilusx.yorick.store/global-state"] " to store the id of "
        "the selected menu item"]
       [:li [:strong ":default-link"] " - the id of the default menu item to "
        "be selected when the menu is initially rendered."]
       [:li [:strong ":data"] " - an item definition map which defines the top "
        "level menu and asociated sub-menu items. See " [:code "defsidebar-item"]
        " function for details on map definition and allowed keys."]]]
     :header {:title "sidebar - a vertical menu component"}]]
   [:div.column
    [:pre
     [:code
      "(let [sb-def {:sid :sb-example" [:br]
      "              :default-link :menu1" [:br]
      "              :data (defsidebar-item :type :menu" [:br]
      "                       :title \"Example Menu\"" [:br]
      "                       :items [(defsidebar-item :title \"Menu 1\"" [:br]
      "                                  :id :menu1)" [:br]
      "                               (defsidebar-item :title \"Menu 2\"" [:br]
      "                                  :id :menu2)" [:br]
      "                               (defsidebar-item :title \"Menu 3\"" [:br]
      "                                  :id :menu3)" [:br]
      "                               (defsidebar-item :type :menu " [:br]
      "                                  :title \"Sub-Menu Example\"" [:br]
      "                                  :items [(defsidebar-item " [:br]
      "                                             :title \"Sub-menu 1\"" [:br]
      "                                             :id :sub-menu1)" [:br]
      "                                          (defsidebar-item" [:br]
      "                                             :title \"Sub-menu 2\"" [:br]
      "                                             :id :sub-menu2)])])}]" [:br]
      "  [:<>" [:br]
      "    [:p \"Definition:\"]" [:br]
      "    [b/render-map sb-def]])"]]
    [:div.box
     (let [sb-def {:sid :sb-example
                   :default-link :menu1
                   :data (s/defsidebar-item :type :menu :title "Example Menu"
                           :items [(s/defsidebar-item :title "Menu 1" :id :menu1)
                                   (s/defsidebar-item :title "Menu 2" :id :menu2)
                                   (s/defsidebar-item :title "Menu 3" :id :menu3)
                                   (s/defsidebar-item :type :menu
                                     :title "Sub-menu Example"
                                     :items [(s/defsidebar-item
                                               :title "Sub-menu 1"
                                               :id :sub-menu1)
                                             (s/defsidebar-item
                                               :title "Sub-menu 2"
                                               :id :sub-menu2)])])}]
       [:div.columns
        [:div.column.is-quarter
         [s/sidebar sb-def]]
        [:div.column
         [:p (str "The selected menu is "
                  (get-in global-state (spath :sb-example)))]]])]]])

(defn sidebar-page []
  [:<>
   [:div.content
    [:h2.title.is-2 "The Sidebar Component"]
    [:p
     "The " [:strong "theophilusx.yorick.sidebar"] " namespace provides functions"
     " and components for rendering a vertical side bar of menus. Nesting of "
     "menus is supported and icons can be associated with each menu."]
    [:p
     "The " [:code "sidebar"] " component records the " [:code "id"]
     " associated with each menu in the global state atom defined by the "
     [:code "theophilusx.yorick.store/global-state"] ", allowing other code "
     "and components to access the selected menu id to determine rendering or "
     "other actions."]
    [:p
     "The definition of a sidebar is done using a " [:code "map"] ". The "
     "function " [:code "defsidebar-item"] " is provided as a convenience "
     "function to help in defining sidebar menus and menu items."]
    [:hr]]
   [t/tab :ui.tabs.sidebar-page [(t/deftab "defsidebar-item" :id :defsidebar)
                                 (t/deftab "sidebar" :id :sidebar)]
    :position :center :size :medium]
   (case (get-in global-state (spath :ui.tabs.sidebar-page))
     :defsidebar [defsidebar-item-function]
     :sidebar [sidebar-component]
     [sidebar-component])])
