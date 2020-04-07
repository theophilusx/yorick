(ns yorick-demo.main
  (:require [goog.dom :as gdom]
            [reagent.dom :as rdom]))

(println "Reloading code")

(defn get-element [name]
  (gdom/getElement name))

(defn mount [el component]
  (rdom/render component el))


(defn greeting []
  [:<>
   [:section.section.has-background-primary
    [:div.container
     [:header.header
      [:h1.title.is-1.has-text-white "Yorick Examples"]
      [:h3.subtitle.is-3
       (str "Examples of " [:strong "Reagent"]
            " styled with " [:strong "Bulma"])]]]]])


(defn mount-app []
  (when-let [el (get-element "app")]
    (mount el [greeting])))

(defn ^:dev/after-load init []
  (println "Hello World")
  (mount-app))
