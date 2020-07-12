(ns yorick-demo.pagination
  (:require [theophilusx.yorick.paginate :as p]
            [theophilusx.yorick.card :as c]))

(defn paginate-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "paginate"] " component provides a data pagination "
       "facility for displaying larger records broken up into separate "
       "display pages. The " [:code "records"] " argument is a vector of "
       "arbitrary records. The " [:code "page-render-fn"] " is a function of "
       "one argument which is called to render a page of records. The component "
       "accepts the following optional keyword arguments:"]
      [:ul
       [:li [:strong ":page-size"] " - sets the number of records per page. "
        "Defaults to 10"]
       [:li [:strong ":rounded"] " - if true, navigation buttons are rendered "
        "with rounded edges instead of square"]
       [:li [:strong ":button-size"] " - sets the size of navigation buttons. "
        "Supported values are " [:code ":small, :medium"] " and " [:code ":large"]]]]
     :header {:title "paginate - a record display pagination component"}]]
   [:div.column
    [:pre
     [:code
      "(let [records [{:name \"Record 1\"}" [:br]
      "               {:name \"Record 2\"}" [:br]
      "               {:name \"Record 3\"}" [:br]
      "               {:name \"Record 4\"}" [:br]
      "               {:name \"Record 5\"}" [:br]
      "               {:name \"Record 6\"}" [:br]
      "               {:name \"Record 7\"}" [:br]
      "               {:name \"Record 8\"}" [:br]
      "               {:name \"Record 9\"}" [:br]
      "               {:name \"Record 10\"}]" [:br]
      "     display-fn (fn [rs]" [:br]
      "                  (into" [:br]
      "                    [:div.box]" [:br]
      "                    (for [r rs]" [:br]
      "                      [:div.box.has-background-info.has-text-white" [:br]
      "                        [:p (str \"Name: \" (:name r))]])))]" [:br]
      "  [paginate/paginate records display-fn :page-size 2])"]]
    [:div.box
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
       [p/paginate records display-fn :page-size 2])]]])

(defn paginate-page []
  [:div.contents
   [:h2.title.is-2 "The Paginate Component"]
   [:p
    "The " [:strong "theophilusx.yorick.paginate"] " namespace provides a "
    "component to support pagination of a list of records. Records are broken "
    "up into pages and a navigation menu is provided for moving between the "
    "pages."]
   [:hr]
   [:div.columns
    [:div.column.is-half
     [:h4.title.is-4 "Description"]]
    [:div.column
     [:h4.title.is-4 "Example"]]]
   [paginate-component]])

