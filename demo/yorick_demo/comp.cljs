(ns yorick-demo.comp
  (:require [theophilusx.yorick.card :as c]
            [theophilusx.yorick.sidebar :as sb]
            [theophilusx.yorick.utils :refer [spath]]
            [theophilusx.yorick.store :refer [cursor]]))

(defn doc-args [arg-list title]
  [c/card (into [:ul]
                (for [[arg doc] arg-list]
                  [:li [:code arg] (str " " doc)]))
   :header {:title title}
   :classes {:card "mt-3"}])

(defn doc-comp
  ([title doc]
   (doc-comp title doc nil nil))
  ([title doc args]
   (doc-comp title doc args nil))
  ([title doc args opt-args]
   [c/card [:<>
            doc
            (when args
              [doc-args args "Arguments"])
            (when opt-args
              [doc-args opt-args "Optional Keyword Arguments"])]
    :header {:title title :class ["has-background-info" "has-text-white"]}]))

(defn example [ex comp]
  [c/card
   [:<>
    [:pre
     [:code ex]]
    [:div.box
     [:h6.title.is-6 "Results"]
     comp]]
   :header {:title "Example" :class ["has-background-success" "has-text-white"]}])

(defn example2 [ex comp]
  [c/card
   [:<>
    [:pre
     [:code ex]]
    [:div.box
     [:h6.title.is-6 "Results"]
     [comp]]]
   :header {:title "Example" :class ["has-background-success" "has-text-white"]}])

(defn doc-fn [{:keys [title desc args opt-args examples]}]
  [:<>
   [doc-comp title desc args opt-args]
   (into [:<>]
         (for [e examples]
           [example2 (:text e) (:code e)]))])

(defn make-menu [page-data]
  (mapv #(sb/defentry (:title %) :value (:value %)) page-data))

(defn current-page [cur pages]
  (:page (first (filter #(= (:value %) @cur) pages))))

(defn doc-ns [sid title doc pages]
  (let [cur (cursor (conj (spath sid) :active-item))
        menu (make-menu pages)]
    (fn []
      [:<>
       [:h2.title.is-2 title]
       [:div.columns
        [:div.column
         doc]
        [:div.column.is-2
         [sb/sidebar sid [(sb/defmenu menu :title "Namespace")] :default (:value (first menu))]]]
       [:hr]
       [(or (current-page cur pages) (:page (first pages)))]])))
