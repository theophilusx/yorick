(ns ^:figwheel-hooks theophilusx.yorick.core
  (:require [goog.dom :as gdom]
            [reagent.core :as reagent :refer [atom]]
            [reagent.session :as session]
            [theophilusx.yorick.basic :as b]
            [theophilusx.yorick.navbar :as nb]))

(println "This text is printed from src/theophilusx/yorick/core.cljs. Go ahead and edit it and see reloading in action.")

(defn multiply [a b] (* a b))


;; define your app data so that it doesn't get over-written on reload
(defonce app-state (atom {:text "Hello bulma world!"}))

(defn get-app-element []
  (gdom/getElement "app"))



(defn hello-world []
  [:div.container
   [:h3.title.is-3 "Basic Components"]
   [:div.columns
    [:div.column
     [:div.content
      [:h6.title.is-6 "Box Component"]
      [:p "The " [:strong "box"] " component is a general purpose container "
       "which provides a border and shading around the supplied content."]]]
    [:div.column
     [:div.content
      [:h6.title.is-6 "Example"]
      [b/box [:div.message "The box component"]]]]
    [:div.column
     [:div.content
      [:h6.title.is-6 "Code"]
      [:pre.has-background-grey-light
       "[box [:div.message \"The box component\"]]"]]]]
   [nb/navbar :main-navbar
    :brand (nb/defitem
             :href "https://bulma.io"
             :contents [:img {:src "images/bulma-logo.png"
                              :width "112"
                              :height "28"}])
      :has-burger true
    :start-menu [(nb/defitem
                   :id :home
                   :contents "Home"
                   :selectable true)
                 (nb/defitem
                   :contents "Documents"
                   :id :documents
                   :selectable true)
                 (nb/defitem
                   :type :dropdown
                   :title "Dropdown"
                   :contents [(nb/defitem
                                :contents "DD 1"
                                :id :ddown-1
                                :selectable true)
                              (nb/defitem
                                :contents "DD 2"
                                :id :ddown-2
                                :selectable true)
                              (nb/defitem
                                :type :divider)
                              (nb/defitem
                                :contents "Last chance"
                                :id :ddown-3
                                :selectable true)])]
      :end-menu [(nb/defitem
                   :contents "Log Out "
                   :id :log-out
                   :selectable true)]]
   [:div.columns
    [:div.column
     [:h6 "Navbar state"]
     [:p (str @nb/navbar-state)]]
    [:div.column
     [:h6 "Global State"]
     [:p (str @session/state)]]]])

(defn mount [el]
  (reagent/render-component [hello-world] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
