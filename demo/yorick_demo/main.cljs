(ns yorick-demo.main
  (:require [goog.dom :as gdom]
            [reagent.dom :as rdom]
            [theophilusx.yorick.sidebar :as sb]
            [theophilusx.yorick.utils :as utils]
            [theophilusx.yorick.store :as store]
            [yorick-demo.basic :refer [basic-page]]))

(println "Reloading code")

(defn get-element [name]
  (gdom/getElement name))

(defn mount [el component]
  (rdom/render component el))

(def demo-sidebar {:sid :ui.menu
                   :default-link :basic
                   :data (sb/defsidebar-item
                           :type :menu
                           :title "Components"
                           :items [(sb/defsidebar-item
                                     :title "Basic"
                                     :id :basic)
                                   (sb/defsidebar-item
                                     :title "Cards"
                                     :id :cards)
                                   (sb/defsidebar-item
                                     :title "Icons"
                                     :id :icons)
                                   (sb/defsidebar-item
                                     :title "Input"
                                     :id :input)
                                   (sb/defsidebar-item
                                     :title "Media"
                                     :id :media)
                                   (sb/defsidebar-item
                                     :title "Modal"
                                     :id :modal)
                                   (sb/defsidebar-item
                                     :title "Navbar"
                                     :item :navbar)
                                   (sb/defsidebar-item
                                     :title "Paginate"
                                     :id :paginate)
                                   (sb/defsidebar-item
                                     :title "Sidebar"
                                     :id :sidebar)
                                   (sb/defsidebar-item
                                     :title "Tables"
                                     :id :tables)
                                   (sb/defsidebar-item
                                     :title "Toolbar"
                                     :id :toolbar)])})

(defn placeholder-page []
  [:div.columns
   [:div.column
    [:h2.title.is-2 "Placeholder Page"]
    [:p "This is the placeholder page for "
     [:strong (str (store/get-in store/global-state (utils/spath :ui.menu)))]]]])

(defn current-page []
  (case (store/get-in store/global-state (utils/spath :ui.menu))
    :basic [basic-page]
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
       [sb/sidebar demo-sidebar]]
      [:div.column
       [current-page]]]]]])


(defn mount-app []
  (when-let [el (get-element "app")]
    (mount el [greeting])))

(defn ^:dev/after-load init []
  (println "Hello World")
  (mount-app))
