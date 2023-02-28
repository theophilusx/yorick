(ns yorick-demo.pagination
  (:require [theophilusx.yorick.paginate :as p]
            [yorick-demo.comp :refer [doc-fn doc-ns]]))

(def pg-title  "paginate - a record display pagination component")

(def pg-desc
  [:p "The " [:strong "paginate"] " component provides a data pagination "
   "facility for displaying larger records broken up into separate "
   "display pages. "])

(def pg-args
  [["records" "A vectgor of arbitrary records which will be partitioned into pages and displayed using the render function."]
   ["render-fn" "A function of one argument which will interpret that argument as an input record to be rendered."]])

(def pg-opt-args
  [[":page-size" "Sets the number of records to display per page. Defaults to 10"]
   [":rounded?" "If true, display prev/next and page numbers using rounded display style."]
   [":button-size" "Set the size of navigation buttons. Possible values are :small, :medium and :large"]])

(def pg-examples
  [{:text "
  (let [records [{:name \"Record 1\"}
                    {:name \"Record 2\"}
                    {:name \"Recrod 3\"}
                    {:name \"Record 4\"}
                    {:name \"Record 5\"}
                    {:name \"Record 6\"}
                    {:name \"Record 7\"}
                    {:name \"Record 8\"}
                    {:name \"Record 9\"}
                    {:name \"Record 10\"}]
           display-fn (fn [rs]
                        (into
                         [:div.box]
                         (for [r rs]
                           [:div.box.has-background-info.has-text-white
                            [:p (str \"Name: \" (:name r))]])))]
       [p/paginate records display-fn :page-size 2])"
    :code (fn []
            (let [records [{:name "Record 1"}
                           {:name "Record 2"}
                           {:name "Recrod 3"}
                           {:name "Record 4"}
                           {:name "Record 5"}
                           {:name "Record 6"}
                           {:name "Record 7"}
                           {:name "Record 8"}
                           {:name "Record 9"}
                           {:name "Record 10"}]
                  display-fn (fn [rs]
                               (into
                                [:div.box]
                                (for [r rs]
                                  [:div.box.has-background-info.has-text-white
                                   [:p (str "Name: " (:name r))]])))]
              [p/paginate records display-fn :page-size 2]))}])

(defn paginate-page []
  [doc-fn {:title pg-title
           :desc pg-desc
           :args pg-args
           :opt-args pg-opt-args
           :examples pg-examples}])

(def ns-sid :ui.sidebar.paginate-ns)

(def ns-title  "The Paginate Component")

(def ns-desc
  [:p "The " [:strong "theophilusx.yorick.paginate"] " namespace provides a "
   "component to support pagination of a list of records. Records are broken "
   "up into pages and a navigation menu is provided for moving between the "
   "pages."])



(def ns-pages [{:title "paginate"
                :value :paginate
                :page paginate-page}])

(defn ns-page []
  [doc-ns ns-sid ns-title ns-desc ns-pages])

