(ns yorick-demo.tables
  (:require [theophilusx.yorick.table :as t]
            [theophilusx.yorick.basic :as b]
            [yorick-demo.comp :refer [doc-fn doc-ns]]))

(def cell-title  "defcell - convenience function for defining table cells")

(def cell-desc
  [:p "The " [:strong "cell"] " function is a convenience function provided "
       "to assist in the definition of the maps used to define table cells. Each "
       "row of a table is a " [:code "vector"] " of " [:code "map"] " structures "
       "which define each cell in the row. The function has one required argument "
       [:code "val"]])

(def cell-args [["val" "The value to place in the cell"]])

(def cell-opt-args
  [[":type" "The type of cell. Possible values are :td for data and :th for a header cell"]
   [":class" "A string or vector of strings specifying CSS class names to add to the cell"]
   [":colspan" "Specify the number of columns this cell should span"]
   [":rowspan" "Specify the number of rows this cell should span"]])

(def cell-examples
  [{:text  "
  (let [hdr [[(t/cell \"Header 1\" :type :th)
              (t/cell \"Header 2\" :type :th)
              (t/cell \"Header 3\" :type :th :colspan 2)]
             [(t/cell \"H1a\" :type :th)
              (t/cell \"H2a\" :type :th)
              (t/cell \"H3a\" :type :th)
              (t/cell \"H3b\" :type :th)]]
        bdy [[(t/cell \"Row 1\")
              (t/cell \"100\")
              (t/cell \"200\")
              (t/cell \"300\")]
             [(t/cell \"Row 2\")
              (t/cell \"400\")
              (t/cell \"500\")
              (t/cell \"600\")]
             [(t/cell \"Row 3\")
              (t/cell \"700\")
              (t/cell \"800\")
              (t/cell \"900\")]]]
    [:<>
      [:p \"The first table header rows\"]
      [b/render-vec (first hdr)]
      [:p \"The first table data rows\"]
      [b/render-vec (first bdy)]])"
    :code (fn []
    (let [hdr [[(t/cell "Header 1" :type :th)
                 (t/cell "Header 2" :type :th)
                 (t/cell "Header 3" :type :th :colspan 2)]
                [(t/cell "H1a" :type :th)
                 (t/cell "H2a" :type :th)
                 (t/cell "H3a" :type :th)
                 (t/cell "H3b" :type :th)]]
           bdy [[(t/cell "Row 1")
                 (t/cell "100")
                 (t/cell "200")
                 (t/cell "300")]
                [(t/cell "Row 2")
                 (t/cell "400")
                 (t/cell "500")
                 (t/cell "600")]
                [(t/cell "Row 3")
                 (t/cell "700")
                 (t/cell "800")
                 (t/cell "900")]]]
       [:<>
        [:p "The first table header rows"]
        [b/render-vec (first hdr)]
        [:p "The first table data rows"]
        [b/render-vec (first bdy)]]))}])

(defn cell-page []
  [doc-fn {:title cell-title
           :desc cell-desc
           :args cell-args
           :opt-args cell-opt-args
           :examples cell-examples}])


;; table-page

(def table-title "table - a data table component")

(def table-desc
  [:<>
   [:p
    "The " [:strong "table"] " component provides a basic component for "
    "rendering tables of data. The table uses a " [:code "vector"] " of "
    [:code "vectors"] " to represent table rows. Each row consists of cells "
    "defined using the " [:code "cell"] " function. The " [:code "body"]
    " argument is the main body of the table and is made up of a vector of "
    "vectors containing cell definition maps."]
   [:p
    "The component supports optional keyword arguments to add headers, footers and "
    "set a number of table parameters which affect the styles and features of "
    "the table."]
   [:p
    "The optional :classes argument is a map which allows specification of additional CSS "
    "classes on the different elements which make up the table. Supported keys are "
    [:code ":table, :header, :footer"] " and " [:code ":body"] " The value for each key is "
    "either a string or a vector of strings specifying CSS class names."]])

(def table-args
  [["body"
    "A vector of vectors containing cell definitoin maps. See the cell function for details on the cell definition map"]])

(def table-opt-args
  [[":classes" "A map whose values are strings or vectors of strings specifying CSS class names."]
   [":header" "A vector of cell defintion maps which make up the table header"]
   [":footer" "A vector of cell definition maps which make up the table footer"]
   [":select?" "If true, add a click handler to table body rows to support selection and highlighting of specific rows"]
   [":bordered?" "If true, table cells are rendered with a border"]
   [":striped?" "If true, rows are rendered with alternating background shades to add subtle striping"]
   [":narrow?" "If true, table is rendered as condensed and narrow as possible"]
   [":hover?" "If true, row is highlighted when the mouse hovers over it"]
   [":fullwidth?" "If true, the table will be rendered to fill the width of its enclosing container"]])

(def table-examples
  [{:text  "
  (let [hdr [[(t/cell \"Header 1\" :type :th)
              (t/cell \"Header 2\" :type :th)
              (t/cell \"Header 3\" :type :th :colspan 2)]
             [(t/cell \"H1a\" :type :th)
              (t/cell \"H2a\" :type :th)
              (t/cell \"H3a\" :type :th)
              (t/cell \"H3b\" :type :th)]]
        bdy [[(t/cell \"Row 1\")
              (t/cell \"100\")
              (t/cell \"200\")
              (t/cell \"300\")]
             [(t/cell \"Row 2\")
              (t/cell \"400\")
              (t/cell \"500\")
              (t/cell \"600\")]
             [(t/cell \"Row 3\")
              (t/cell \"700\")
              (t/cell \"800\")
              (t/cell \"900\")]]]
       [:<>
        [:p \"A basic table with headers\"]
        [t/table bdy :header hdr]
        [:p \"A table with headers and selectable rows\"]
        [t/table bdy :header hdr :select true]
        [:p \"A table with striped rows and borders\"]
        [t/table bdy :header hdr :striped true :bordered true]])"
    :code (fn []
    (let [hdr [[(t/cell "Header 1" :type :th)
                 (t/cell "Header 2" :type :th)
                 (t/cell "Header 3" :type :th :colspan 2)]
                [(t/cell "H1a" :type :th)
                 (t/cell "H2a" :type :th)
                 (t/cell "H3a" :type :th)
                 (t/cell "H3b" :type :th)]]
           bdy [[(t/cell "Row 1")
                 (t/cell "100")
                 (t/cell "200")
                 (t/cell "300")]
                [(t/cell "Row 2")
                 (t/cell "400")
                 (t/cell "500")
                 (t/cell "600")]
                [(t/cell "Row 3")
                 (t/cell "700")
                 (t/cell "800")
                 (t/cell "900")]]]
       [:<>
        [:p "A basic table with headers"]
        [t/table bdy :header hdr]
        [:p "A table with headers and selectable rows"]
        [t/table bdy :header hdr :select true]
        [:p "A table with striped rows and borders"]
        [t/table bdy :header hdr :striped true :bordered true]]))}])

(defn table-page []
  [doc-fn {:title table-title
           :desc table-desc
           :args table-args
           :opt-args table-opt-args
           :examples table-examples}])

;; ns-page

(def table-ns-doc
  [:<>
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
     "maps defining each cell for a row of data within the table."]])

(def table-ns-pages [{:title "cell" :value :cell
                      :page cell-page}
                     {:title "table"
                      :value :table
                      :page table-page}])

(defn ns-page []
  [doc-ns :ui.sidebar.sidebar-ns  "The Table Component" table-ns-doc table-ns-pages])



