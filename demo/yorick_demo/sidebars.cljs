(ns yorick-demo.sidebars
  (:require [theophilusx.yorick.sidebar :as s]
            [theophilusx.yorick.basic :as b]
            [theophilusx.yorick.store :refer [get-in]]
            [theophilusx.yorick.utils :refer [spath]]
            [yorick-demo.comp :refer [doc-fn doc-ns]]))

;; defentry

(def defentry-title "defentry - a menu entry definition helper")

(def defentry-desc
  [:p "The " [:strong "defentry"] " helper function assists in defining the data map "
   "used to define a menu entry item."])

(def defentry-args [["title" "The text which will be used as the menu entry."]])

(def defentry-opt-args
  [[":value" "The value to be used to record this item in the store when it is selected."]
   [":id" "The value to use as the ID attribute. If not supplied, use :value"]
   [":class" "A string or vector of strings representing CSS class names to add to the entryu."]
   [":icon" "An icon data map as returned by the deficon function."]])

(def defentry-examples
  [{:text  "
  (let [e1 (s/defentry \"Example 1\")
        e2 (s/defentry \"Example 2\" :value :example2)]
     [:<>
       [:p \"Example 1 data map\"]
       [b/render-map e1]
       [:p \"Example 2 data map\"]
       [b/render-map e2]])"
    :code (fn []
                     (let [e1 (s/defentry "Example 1")
                               e2 (s/defentry "Example 2" :value :example2)]
                           [:<>
                            [:p "Example 1 data map"]
                            [b/render-map e1]
                            [:p "Example 2 data map"]
                            [b/render-map e2]]))}])

(defn defentry-page []
  [doc-fn {:title defentry-title
           :desc defentry-desc
           :args defentry-args
           :opt-args defentry-opt-args
           :examples defentry-examples}])

;; defmenu

(def defmenu-title  "defmenu - a menu helper function")

(def defmenu-desc
  [:p "The " [:strong "defmenu"] " helper function assists in defining a sidebar menu. "
   "The " [:code "entries"] " argument is a vector of menu item data maps, typically generated by "
   "the " [:code "defentry"] " helper function."])

(def defmenu-args
  [["entries" "A vector of menu entry definition maps. See defentry for details on these maps."]])

(def defmenu-opt-args
  [[":value" "The value to be used in the defualt store to indicate the menu has been selected."]
   [":title" "Add a non-clickable text title to the menu"]
   [":id" "An ID attribute to associate with this element."]
   [":class" "A string or vector of strings specifying CSS classes to add to this element."]
   [":icon" "An icon definition map to add an icon to the menu. See deficon for details."]])

(def defmenu-examples
  [{:text  "
  (let [m1 (s/defmenu [(s/defentry \"Menu 1\" :value :m1)
                       (s/defentry \"Menu 2\" :value :m2)
                       (s/defmenu [(s/defentry \"Sub Menu A\" :value :sma)
                                   (s/defentry \"Sub Menu B\" :value :smb)]
                                               :title \"Sub Menu\")]
                                   :title \"Menu\" :value :main-menu)]
     [:<>
       [:p \"The menu definition map\"]
       [b/render-map m1]])"
    :code (fn []
    (let [m1 (s/defmenu [(s/defentry "Menu 1" :value :m1)
                         (s/defentry "Menu 2" :value :m2)
                         (s/defmenu [(s/defentry "Sub Menu A" :value :sma)
                                     (s/defentry "Sub Menu B" :value :smb)]
                           :title "Sub Menu")]
               :title "Menu" :value :main-menu)]
      [:<>
       [:p "The menu definition map"]
       [b/render-map m1]]))}])

(defn defmenu-page []
  [doc-fn {:title defmenu-title
           :desc defmenu-desc
           :args defmenu-args
           :opt-args defmenu-opt-args
           :example defmenu-examples}])

;; sidebar

(def sidebar-title  "sidebar - a basic sidebar menu component")

(def sidebar-desc
  [:p "The " [:strong "sidebar"] " component provides a vertical menu sidebar. Menus can be "
   "nested up to two levels deep. Menus can have titles and both menus and menu items can "
   "have icons. The component tracks which menus are active and which menu item has been selected "
   "by storing value identifiers in the global store."])

(def sidebar-args
  [["sid" "A storage identifier which specifies where state information associated with the sidebar is stored."]
                   ["menu" "A menu defintion map created us9ing the defmenu function."]])

(def sidebar-opt-args
  [[":default" "The value of the menu itme to be selected by defualt when the component is first loaded"]])

(def sidebar-examples
  [{:text  "
  (let [m (s/defmenu [(s/defentry \"Menu 1\" :value :m1)
                      (s/defentry \"Menu 2\" :value :m2)
                      (s/defmenu [(s/defentry \"Submenu A\" :value :sma)
                                  (s/defentry \"Submenu B\" :value :smb)]
                         :title \"Sub Menus\" :value :submenu)]
             :title \"Main Menu\" :value :mainmenu)]
      [:<>
        [:div.columns
          [:div.column
            [s/sidebar :example.sidebar m :default :m2]]
          [:div.column
            [:p \"Defualt store value\"]
            [b/render-map (get-in (spath :example.sidebar))]]]])"
    :code (fn []
            (let [m [(s/defmenu [(s/defentry "Menu 1" :value :m1)
                                 (s/defentry "Menu 2" :value :m2)
                                 (s/defmenu [(s/defentry "Submenu A" :value :sma)
                                             (s/defentry "Submenu B" :value :smb)]
                                   :title "Sub Menus" :value :submenu)]
                       :title "Main Menu" :value :mainmenu)]]
              [:div.columns
               [:div.column.is-3
                [s/sidebar :example.sidebar m :default :m2]]
               [:div.column
                [:p "Defualt store value"]
                [b/render-map (get-in (spath :example.sidebar))]]]))}])

(defn sidebar-component []
  [doc-fn {:title sidebar-title
           :desc sidebar-desc
           :args sidebar-args
           :opt-args sidebar-opt-args
           :examples sidebar-examples}])

(def sb-doc
  [:<>
   [:p
    "The " [:strong "theophilusx.yorick.sidebar"] " namespace provides basic sidebar "
    "component functionality. A sidebar is a vertical menu which consists of links "
    "representing menu items or submenus of nested links. You can have up to two levels of "
    "menus in a sidebar. You can also include dividers and static content, such as text or "
    "images."]
   [:p
    "This namespace provides two helper functions which can be used to help generate the "
    "data maps used to define menus (" [:strong "defmenu"] ") and menu items ("
    [:strong "defitem"] ")."]])

(def sb-pages
  [{:title "defentry" :value :defentry :page defentry-page}
   {:title "defmenu" :value :defmenu :page defmenu-page}
   {:title "sidebar" :value :sidebar :page sidebar-component}])

(defn sidebar-page []
  [doc-ns :ui.sidebar.sidebar-page "The Sidebar Component" sb-doc sb-pages])

