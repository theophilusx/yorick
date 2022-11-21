(ns yorick-demo.comp
  (:require [theophilusx.yorick.card :as c]))

(defn doc-args [arg-list]
  (into [:ul]
        (for [[arg doc] arg-list]
          [:li [:strong arg] (str " " doc)])))

(defn doc-comp [title doc args]
  [c/card [:div.content
           doc
           (when args
             [doc-args args])]
   :header {:title title :class ["has-background-info" "has-text-white"]}])

(defn example [ex comp]
  [c/card
   [:div.content
    [:pre
     [:code ex]]
    [:div.box
     [:h6.title.is-6 "Results"]
     comp]]
   :header {:title "Example" :class ["has-background-info" "has-text-white"]}])
