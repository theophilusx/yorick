(ns yorick-demo.comp
  (:require [theophilusx.yorick.card :as c]))

(defn doc-args [arg-list]
  (into [:ul]
        (for [[arg doc] arg-list]
          [:li [:strong arg] (str " " doc)])))

(defn doc-comp
  ([title doc]
   (doc-comp title doc nil nil))
  ([title doc args]
   (doc-comp title doc args nil))
  ([title doc args opt-args]
   [c/card [:div.content
            doc
            (when args
              [:<>
               [:h6.title.is-6 "Arguments"]
               [doc-args args]])
            (when opt-args
              [:<>
               [:h6.title.is-6 "Optional Keyword Arguments"]
               [doc-args opt-args]])]
    :header {:title title :class ["has-background-info" "has-text-white"]}]))

(defn example [ex comp]
  [c/card
   [:div.content
    [:pre
     [:code ex]]
    [:div.box
     [:h6.title.is-6 "Results"]
     comp]]
   :header {:title "Example" :class ["has-background-info" "has-text-white"]}])
