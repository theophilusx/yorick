(ns theophilusx.yorick.components.paginate-test
  (:require [cljs.test :refer-macros [deftest testing is use-fixtures]]
            [reagent.core :as r]
            [theophilusx.yorick.test-utils :as tu]
            [theophilusx.yorick.paginate :as p]))

(use-fixtures :each
  {:before tu/setup!
   :after  tu/teardown!})

;; paginate signature: (paginate records render-fn & {:keys [page-size]})
;; form-2 component: outer (records _ & {:keys [page-size]})
;;                   inner (fn [_ render-fn & {:keys [rounded? button-size]}])
;; Previous/Next are <a> elements (via basic/a) with pagination-previous/pagination-next classes
;; Page links are <a> elements with .pagination-link class

(def sample-records (vec (range 1 11)))   ;; 10 items

(defn render-page [page]
  [:ul (for [record page] [:li {:key (str record)} (str record)])])

(deftest paginate-structure-test
  (testing "renders pagination nav"
    (tu/render! [p/paginate sample-records render-page :page-size 5])
    (is (some? (tu/query "nav.pagination"))))
  (testing "renders correct number of page links for 10 items / 5 per page"
    (tu/render! [p/paginate sample-records render-page :page-size 5])
    ;; 2 pages -> 2 pagination-link items
    (is (= 2 (.-length (tu/query-all ".pagination-link")))))
  (testing "page 1 is current initially"
    (tu/render! [p/paginate sample-records render-page :page-size 5])
    (is (tu/has-class? (tu/query ".pagination-link") "is-current"))))

(deftest paginate-navigation-test
  (testing "clicking Next advances to page 2"
    (tu/render! [p/paginate sample-records render-page :page-size 5])
    (let [next-btn (tu/query ".pagination-next")]
      (.click next-btn)
      (r/flush)
      ;; Page 2 link (second .pagination-link) should now be current
      (let [links (tu/query-all ".pagination-link")]
        (is (tu/has-class? (aget links 1) "is-current")))))
  (testing "Previous link has is-disabled class on page 1"
    (tu/render! [p/paginate sample-records render-page :page-size 5])
    (is (tu/has-class? (tu/query ".pagination-previous") "is-disabled"))))
