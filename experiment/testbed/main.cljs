(ns testbed.main
  (:require [goog.dom :as gdom]
            [reagent.dom :as rdom]
            [theophilusx.yorick.utils :as utils]
            [theophilusx.yorick.store :as store]
            [theophilusx.yorick.basic :as basic]
            [testbed.navbar :as nav]))

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

(defn get-element [name]
  (gdom/getElement name))

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
  (store/assoc-in! (utils/spath :ui.menu) :navbar)
  (mount-app))
