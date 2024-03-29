(ns theophilusx.yorick.table
  "Provides a component to render an HTML table of data."
  (:require [theophilusx.yorick.store :as store]
            [theophilusx.yorick.utils :refer [cs]]))

(defn cell
  "Generates a map defining a table cell. The 'val' argument is the data to
  be placed in the cell. The following keys are supported:

  | Key        | Description                                                   |
  |------------|---------------------------------------------------------------|
  | `:type`    | The type of cell. Possible values are `:td` for a data cell   |
  |            | and `:th` for a header cell                                   |
  | `:class`   | a string or vector of strings specifying CSS class names to   |
  |            | add to the cell                                               |
  | `:colspan` | specify the number of columns this cell should span           |
  | `:rowspan` | specify the number of rows this cell should span              |"
  [val & {:keys [type class colspan rowspan]
          :or {type :td}}]
  {:type type
   :value val
   :class class
   :colspan colspan
   :rowspan rowspan})

(defn- tr
  "Renders a table row. The `cells` argument is a vector of cell definition
  maps (see `cell` function for details on supported keys for a cell map.)
  The `active-cur` argument is a cursor pointing to state information for the
  table which shows which line is active

  | Keyword   | Description                                                 |
  |-----------|-------------------------------------------------------------|
  | `:class`  | a string or vector of strings specifying CSS class names to |
  |           | add to the `tr` element                                     |
  | `:select?`| if true, add a click handler which allows a row to be       |
  |           | selected and highlighted.                                   |
  | `:row-id` | a unique row id value used when select highlighting is      |
  |           | enabled                                                    | "
  [cells active-cur & {:keys [class select? row-id]}]
  (into [:tr {:class (cs class)}]
        (for [c cells]
          (if select?
            [(:type c) {:class (cs (:class c)
                                   (when (= @active-cur row-id) "is-selected"))
                        :colSpan (str (:colspan c))
                        :rowSpan (str (:rowspan c))
                        :on-click #(reset! active-cur row-id)}
             (:value c)]
            [(:type c) {:class (cs (:class c))
                        :colSpan (str (:colspan c))
                        :rowSpan (str (:rowspan c))}
             (:value c)]))))

(defn- thead
  "Renders a `:th` row of a table. The `rows` argument is a vector of vectors
  containing cell definitions for each row. The `model` is reagent atom used
  as the document model store for tracking selected rows when the `:select`
  keyword is set. This function supports the following optional keyword
  arguments

  | Keyword i  | Description                                                  |
  |------------|--------------------------------------------------------------|
  | `:classes` | A map of strings or vector of strings representing CSS class |
  |            | names. Supported keys are `:thead` and `:tr`.                |"
  [rows active-cur & {:keys [classes]}]
  (into
   [:thead {:class (cs (:thead classes))}]
   (for [r rows]
     [tr r active-cur :class (:tr classes)])))

(defn- tfoot
  "Renders a table footer. The `rows` argument is a vector of vectors
  containing cell definition maps. The `model` argument is a reagent atom used
  as the document model store. The optional keyword argument `:classes` is a map
  of strings or vectors of strings specifying CSS class names. The map supports
  the keys `:tfoot` for classes to be applied to the table footer element and
  `:tr` for classes to be applied to the table row element."
  [rows active-cur & {:keys [classes]}]
  (into
   [:tfoot {:class (cs (:tfoot classes))}]
   (for [r rows]
     [tr r active-cur :class (:tr classes)])))

(defn- tbody
  "Renders a table body. The `rows` argument is a vector of vectors containing
  cell definition maps. The `model` argument is a reagent atom to be used as the
  document model store. The optional keyword argument `:classes` is a map of
  strings or vectors of strings specifying CSS class names. The map supports
  the keys `:tbody` for classes to be added to the table body element and
  `:tr` for classes to be added to row elements. The optional keyword argument
  `:select`, if true, generates a table which supports select to highlight rows."
  [rows active-cur & {:keys [classes select?]}]
  (into
   [:tbody {:class (cs (:tbody classes))}]
   (map-indexed (fn [idx r]
                  [tr r active-cur :class (:tr classes) :select select? :row-id idx])
                rows)))

(defn table
  "Render a table of data. The `body` argument is a vector of vectors containing
  cell definition maps. See `cell` for details on cell structure and supported
  keys. The component supports the following optional keywords:

  | Keyword      | Description                                                 |
  |--------------|-------------------------------------------------------------|
  | `:classes`   | a map of strings or vectors of strings specifying CSS class |
  |              | names. The following keys are supported `:table`, `:thead`, |
  |              | `:tfoot` and `:tbody`                                       |
  | `:header`    | a vector of vectors containing cell definition maps         |
  |              | defining the table header. See `cell` function for       |
  |              | details on cell structure.                                  |
  | `:footer`    | a vector of vectors containing cell definition maps         |
  |              | defining the table footer. See `cell` function for       |
  |              | details on cell structure.                                  |
  | `:select?`   | if true, add a click handler to table body rows to support  |
  |              | selection and highlighting of specific row.                 |
  | `:bordered?` | if true, tables are rendered with a border                  |
  | `:striped?`  | if true, tables are rendered with rows striped              |
  | `:narrow?`   | if true, table is condensed and rendered as narrow as       |
  |              | possible.                                                   |
  | `:hover?`    | if true, rows are highlighted when mouse hovers over them   |
  | `:fullwidth?`| if true, tables are rendered to fill the full width of the  |
  |              | enclosing container.                                        |"
  [_ & _]
  (let [active-cur (store/cursor :selected-row)] 
    (fn [body & {:keys [classes header footer select? bordered? striped?
                       narrow? hover? fullwidth?]}]
      [:table.table {:class [(cs (:table classes)
                                 (when bordered? "is-bordered")
                                 (when striped? "is-striped")
                                 (when narrow? "is-narrow")
                                 (when hover? "is-hoverable")
                                 (when fullwidth? "is-fullwidth"))]}
       (when header [thead header active-cur :class (:header classes)])
       (when footer [tfoot footer active-cur :class (:footer classes)])
       [tbody body active-cur :class (:body classes) :select select?]])))

(defn scrollable-table
  "Wrap a table component in a table container to make it scrollable. The `t`
  argument is a table component."
  [t]
  [:div.table-container t])
