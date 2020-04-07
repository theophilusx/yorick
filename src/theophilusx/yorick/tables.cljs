(ns theophilusx.yorick.tables
  (:require [reagent.core :as r]
            [theophilusx.yorick.store :as store]))

;; The structure for the top level `classes` hash
;; {:header {:thead ""
;;           :tr ""}
;;  :footer {:tfoot ""
;;           :tr ""}
;;  :body {:tbody ""
;;         :tr ""}}

(defn defcell [val & {:keys [type class colspan rowspan]
                      :or {type :td}}]
  {:type type
   :value val
   :class class
   :colspan colspan
   :rowspan rowspan})

(defn tr [cells doc & {:keys [class select row-id]}]
  (letfn [(set-select-row [id]
            (store/put! doc :selected-row id))
          (is-selected? [id]
            (= id (store/get doc :selected-row)))]
    (into
     [:tr {:class [class]}]
     (for [c cells]
       (if select
         [(:type c) {:class [(:class c)
                             (when (is-selected? row-id) "is-selected")]
                     :colSpan (str (:colspan c))
                     :rowSpan (str (:rowspan c))
                     :on-click #(set-select-row row-id)}
          (:value c)]
         [(:type c) {:class [(:class c)]
                     :colSpan (str (:colspan c))
                     :rowSpan (str (:rowspan c))}
          (:value c)])))))

(defn thead [rows doc & {:keys [classes]}]
  (into
   [:thead {:class [(:thead classes)]}]
   (for [r rows]
     [tr r doc :class (:tr classes)])))

(defn tfoot [rows doc & {:keys [classes]}]
  (into
   [:tfoot {:class [(:tfoot classes)]}]
   (for [r rows]
     [tr r doc :class (:tr classes)])))

(defn tbody [rows doc & {:keys [classes select]}]
  (into
   [:tbody {:class [(:tbody classes)]}]
   (map-indexed (fn [idx r]
                  [tr r doc :class (:tr classes) :select select :row-id idx])
                rows)))

(defn table [_ & _]
  (let [doc (r/atom {:selected-row nil})] 
    (fn [body & {:keys [classes header footer select borded striped
                       narrow hover fullwidth]}]
      [:table.table {:class [(:table classes)
                             (when borded "is-borded")
                             (when striped "is-striped")
                             (when narrow "is-narrow")
                             (when hover "is-hoverable")
                             (when fullwidth "is-fullwidth")]}
       (when header [thead header doc :class (:header classes)])
       (when footer [tfoot footer doc :class (:footer classes)])
       [tbody body doc :class (:body classes) :select select]])))

(defn table-container [t]
  [:div.table-container
   t])
