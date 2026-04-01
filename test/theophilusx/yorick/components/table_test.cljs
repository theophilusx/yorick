(ns theophilusx.yorick.components.table-test
  (:require [cljs.test :refer-macros [deftest testing is use-fixtures]]
            [reagent.core :as r]
            [theophilusx.yorick.test-utils :as tu]
            [theophilusx.yorick.table :as t]))

(use-fixtures :each
  {:before tu/setup!
   :after  tu/teardown!})

(def sample-body
  [[(t/cell "Alice") (t/cell "Engineer")]
   [(t/cell "Bob")   (t/cell "Designer")]
   [(t/cell "Carol") (t/cell "Manager")]])

(def sample-header
  [[(t/cell "Name" :type :th) (t/cell "Role" :type :th)]])

(deftest table-structure-test
  (testing "renders correct number of body rows"
    (tu/render! [t/table :test.tbl sample-body])
    (is (= 3 (.-length (tu/query-all "tbody tr")))))
  (testing "header row rendered when :header supplied"
    (tu/render! [t/table :test.tbl2 sample-body :header sample-header])
    (is (some? (tu/query "thead")))
    (is (= 2 (.-length (tu/query-all "thead th"))))))

(deftest table-select-test
  (testing "REGRESSION: :select? true enables row selection (was :select keyword)"
    (tu/render! [t/table :test.tbl3 sample-body :select? true])
    ;; Clicking a td in the first row adds is-selected to all tds in that row
    (let [first-td (tu/query "tbody tr:first-child td")]
      (.click first-td)
      (r/flush)
      (is (tu/has-class? first-td "is-selected"))))
  (testing "clicking a different row moves selection"
    (tu/render! [t/table :test.tbl4 sample-body :select? true])
    (let [rows     (tu/query-all "tbody tr")
          row0-td  (.querySelector (aget rows 0) "td")
          row1-td  (.querySelector (aget rows 1) "td")]
      (.click row0-td)
      (r/flush)
      (.click row1-td)
      (r/flush)
      (is (not (tu/has-class? row0-td "is-selected")))
      (is (tu/has-class? row1-td "is-selected")))))

(deftest table-independent-sids-test
  (testing "two tables with different sids have independent selection state"
    (tu/render!
     [:<>
      [t/table :test.tbl.a sample-body :select? true]
      [t/table :test.tbl.b sample-body :select? true]])
    (let [all-tables (tu/query-all "table")
          tbl-a      (aget all-tables 0)
          tbl-b      (aget all-tables 1)
          td-a       (.querySelector tbl-a "tbody tr:first-child td")
          td-b       (.querySelector tbl-b "tbody tr:last-child td")]
      (.click td-a)
      (r/flush)
      (.click td-b)
      (r/flush)
      ;; Table A row 0 is selected, table B row 2 is selected — independent
      (is (tu/has-class? td-a "is-selected"))
      (is (tu/has-class? td-b "is-selected"))
      ;; Table A's last row is NOT selected
      (is (not (tu/has-class? (.querySelector tbl-a "tbody tr:last-child td") "is-selected"))))))
