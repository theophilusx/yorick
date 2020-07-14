(ns yorick-demo.tables
  (:require [theophilusx.yorick.table :as t]
            [theophilusx.yorick.card :as c]
            [theophilusx.yorick.basic :as b]))

(defn defcell-function []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "defcell"] " function is a convenience function provided "
       "to assist in the definition of the maps used to define table cells. Each "
       "row of a table is a " [:code "vector"] " of " [:code "map"] " structures "
       "which define each cell in the row. The function has one required argument "
       [:code "val"] ", the value for the cell. A number of optional keyword "
       "arguments are also supported to further manage the cell definition:"]
      [:ul
       [:li [:strong ":type"] " - defines the type of cell. Can be either "
        [:code ":td"] ", for a table data cell or " [:code ":th"] " for a table "
        "header cell. The default if not specified is " [:code ":td"]]
       [:li [:strong ":class"] " - a string or vector of strings specifying "
        "CSS class names to add to the cell definition."]
       [:li [:strong ":colspan"] " - specify the number of columns this cell "
        "should span."]
       [:li [:strong ":rowspan"] " - specify the number of rows this cell "
        "should span."]]]
     :header {:title "defcell - convenience function for defining table cells"}]]
   [:div.column
    [:pre
     [:code
      "(let [hdr [[(defcell \"Header 1\" :type :th)" [:br]
      "            (defcell \"Header 2\" :type :th)" [:br]
      "            (defcell \"Header 4\" :type :th :colspan 2)]" [:br]
      "           [(defcell \"H1a\" :type :th)" [:br]
      "            (defcell \"H2a\" :type :th)" [:br]
      "            (defcell \"H3a\" :type :th)" [:br]
      "            (defcell \"H3b\" :type :th)]]" [:br]
      "      bdy [[(defcell \"Row 1\")" [:br]
      "            (defcell \"100\")" [:br]
      "            (defcell \"200\")" [:br]
      "            (defcell \"300\")]" [:br]
      "           [(defcell \"Row 2\")" [:br]
      "            (defcell \"400\")" [:br]
      "            (defcell \"500\")" [:br]
      "            (defcell \"600\")]" [:br]
      "           [(defcell \"Row 3\")" [:br]
      "            (defcell \"700\")" [:br]
      "            (defcell \"800\")" [:br]
      "            (defcell \"900\")]]]" [:br]
      "  [:<>" [:br]
      "    [:p \"The table header rows\"]" [:br]
      "    [b/render-vec hdr]" [:br]
      "    [:p \"The table data rows\"]" [:br]
      "    [b/render-vec bdy]])"]]
    [:div.box
     (let [hdr [[(t/defcell "Header 1" :type :th)
                 (t/defcell "Header 2" :type :th)
                 (t/defcell "Header 3" :type :th :colspan 2)]
                [(t/defcell "H1a" :type :th)
                 (t/defcell "H2a" :type :th)
                 (t/defcell "H3a" :type :th)
                 (t/defcell "H3b" :type :th)]]
           bdy [[(t/defcell "Row 1")
                 (t/defcell "100")
                 (t/defcell "200")
                 (t/defcell "300")]
                [(t/defcell "Row 2")
                 (t/defcell "400")
                 (t/defcell "500")
                 (t/defcell "600")]
                [(t/defcell "Row 3")
                 (t/defcell "700")
                 (t/defcell "800")
                 (t/defcell "900")]]]
       [:<>
        [:p "The first table header rows"]
        [b/render-vec (first hdr)]
        [:p "The first table data rows"]
        [b/render-vec (first bdy)]])]]])

(defn tables-page []
  [:<>
   [:div.content
    [:h2.title.is-2 "The Table Component"]
    [:p
     "The " [:strong "theophilusx.yorick.table"] " namespace provides functions "
     "and components for rendering tables of data. The " [:code "table"] " component "
     "provides support for tables with header and footer data, selectable row "
     "highlighting, striped rows, condensed narrow cell formatting, borders, "
     "highlighting of tables based on mouse hover and tables which are rendered "
     "to fill the full width of their enclosing container. The "
     [:code "scrollable-table"] " component provides rendered tables with "
     "content which can be scrolled."]
    [:p
     "The " [:code "defcell"] " function is provided as a convenient function "
     "to assist in defining the dell definition maps used to format table cell "
     "content. Tables are essentially defined as vectors of vectors containing "
     "maps defining each cell for a row of data within the table."]]
   [:hr]
   [:div.columns
    [:div.column.is-half
     [:h4.title.is-4 "Description"]]
    [:div.column
     [:h4.title.is-4 "Example"]]]
   [defcell-function]])

