(ns testbed.main
  (:require [goog.dom :as gdom]
            [reagent.dom :as rdom]
            [theophilusx.yorick.utils :as utils]
            [theophilusx.yorick.store :as store]
            [theophilusx.yorick.basic :as basic]
            [testbed.navbar :as nav]
            [testbed.paginate :as paginate]
            [testbed.sidebar :as sidebar]
            [testbed.tab :as tab]
            [testbed.table :as table]))

;; navbar

(def navbar-menus {:start [(nav/nav-link "Item1")
                            (nav/nav-link "Another Item" :value :item2)
                            (nav/nav-container [:p "Just some stuff"])
                            (nav/nav-dropdown "Dropdown" [(nav/nav-link "Dropdown 1" :itemd1)
                                                      (nav/nav-link "Dropdown 2" :itemd2)
                                                      (nav/nav-divider)
                                                      (nav/nav-link "Dropdown 3" :itemd3)])]
                    :end [(nav/nav-link "Login" :value :login)
                          (nav/nav-link "Create Account" :value :create)]})

(def brand [(nav/nav-container (basic/a (basic/img "/images/bulma-logo.png")))
            (nav/nav-container "Cranky Man")])

(defn navbar-page []
  [:<>
   [nav/navbar :navbar navbar-menus :brand brand :default :item2
    :transparent? true :shadow? true]
   [:h2 "See the value"]
   [:h3 (str "Value is " (store/get-in (utils/spath :navbar.active-item)))]
   [:p "The value in the global store is"]
   [basic/render-map @store/default-store]])

;; pagination

(def pages (mapv (fn [i]
                   {:title (str "This is heading " i)
                    :body (str "This is the body of record " i)}) (range 200)))

(defn test-render [p]
  (let [rows (partition-all 2 p)]
    (into [:div.container]
          (for [r rows]
            (into [:div.columns]
                  (for [i r]
                    [:div.column
                     [:h1 (:title i)]
                     [:p (:body i)]]))))))

(defn paginate-page []
  [:<>
   [paginate/paginate pages test-render]])

;; sidebar


(def test-menu [(sidebar/defmenu [(sidebar/defentry "Menu 1" :value :menu1)
                                  (sidebar/defentry "Menu 2")
                                  (sidebar/defmenu [(sidebar/defentry "Submenu 1")
                                                    (sidebar/defentry "Submenu 2")
                                                    (sidebar/defentry "Submenu 3")]
                                    :title "More Options")
                                  (sidebar/defentry "Menu 4")]
                  :title "Main")
                (sidebar/defmenu [(sidebar/defentry "Admin 1")
                                  (sidebar/defentry "Admin 2")
                                  (sidebar/defentry "Admin 3")]
                  :title "Administration")])

(defn sidebar-page []
  [:<>
   [sidebar/sidebar :sidebar test-menu]
   [:h2 "Values"]
   [basic/render-map @store/default-store]
   [:h3 "Input Data"]
   [basic/render-vec test-menu]])

;; Tab bars

(def tab-data [(tab/deftab "Tab 1")
               (tab/deftab "Tab 2")
               (tab/deftab "Tab 3")])

(defn tab-page []
  [:<>
   [tab/tab-bar :tab tab-data]
   [:h1 "Values"]
   [basic/render-map @store/default-store]])
(defn get-element [name]
  (gdom/getElement name))

;; tables

(def table-header [[(table/cell "Group A" :colspan 2 :type :th) (table/cell "Group B" :colspan 2 :type :th)
                    (table/cell "Group C" :colspan 2 :type :th) (table/cell "Group D" :colspan 2 :type :th)]
                   [(table/cell "A1" :type :th) (table/cell "A2" :type :th)
                    (table/cell "B1" :type :th) (table/cell "B2" :type :th)
                    (table/cell "C1" :type :th) (table/cell "C2" :type :th)
                    (table/cell "D1" :type :th) (table/cell "D2" :type :th)]])

(def table-data [[(table/cell 1) (table/cell 2) (table/cell 3) (table/cell 4)
                   (table/cell "A") (table/cell "B") (table/cell "C") (table/cell "D")]
                  [(table/cell 5) (table/cell 6) (table/cell 7) (table/cell 8)
                   (table/cell "AA") (table/cell "BB") (table/cell "CC") (table/cell "DD")]])

(def table-footer [[(table/cell 6) (table/cell 8) (table/cell 10) (table/cell 12)
                    (table/cell 6) (table/cell 8) (table/cell 10) (table/cell 12)]
                   [(table/cell 14 :colspan 2) (table/cell 22 :colspan 2)
                    (table/cell 14 :colspan 2) (table/cell 22 :colspan 2)]])

(def table-data2 (into []
                       (for [r (range 1 3)]
                         (into []
                               (for [c (range 1 44)]
                                 (table/cell (* r c)))))))

(defn table-page []
  [:<>
   [:h1 "Basic Table"]
   [:div.table-container
    [table/table table-data :header table-header :footer table-footer]]
   [:h1 "Scrollable Table"]
   [table/scrollable-table [table/table table-data2]]
   [:h1 "Try again!"]
   [:div.table-container [table/table table-data2]]])


(defn mount [el component]
  (rdom/render component el))

(defn placeholder-page []
  [:div.columns
   [:div.column
    [:h2.title.is-2 "Placeholder Page"]
    [:p "This is the placeholder page for "
     [:strong (str (store/get-in (utils/spath :ui.menu)))]]]])

(defn current-page []
  (case (store/get-in (utils/spath :ui.menu))
    :navbar [navbar-page]
    :paginate [paginate-page]
    :sidebar [sidebar-page]
    :tab [tab-page]
    :table [table-page]
    [placeholder-page]))

(defn greeting []
  [:<>
   [:section.section.has-background-primary
    [:div.container
     [:header.header
      [:h1.title.is-1.has-text-white "Yorick Examples"]
      [:h3.subtitle.is-3
       "Example of " [:strong "Reagent"] " components styled with "
       [:strong "Bulma"]]]]]
   [:section.section
    [:div.container
     [:div.columns
      [:div.column.is-2
       [:h4 "Is placeholder column"]]
      [:div.column
       [current-page]]]]]])

(defn mount-app []
  (when-let [el (get-element "app")]
    (mount el [greeting])))

(defn ^:dev/after-load init []
  (println "Hello World")
  (store/assoc-in! (utils/spath :ui.menu) :table)
  (mount-app))
