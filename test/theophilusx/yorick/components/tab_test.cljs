(ns theophilusx.yorick.components.tab-test
  (:require [cljs.test :refer-macros [deftest testing is use-fixtures]]
            [reagent.core :as r]
            [theophilusx.yorick.test-utils :as tu]
            [theophilusx.yorick.tab :as tab]
            [theophilusx.yorick.icon :as icon]))

(use-fixtures :each
  {:before tu/setup!
   :after  tu/teardown!})

(deftest tab-bar-icon-test
  (testing "REGRESSION: :icon-data renders icon on tab (was :icon-datat typo)"
    (let [tabs [(tab/deftab "Home" :icon-data (icon/deficon "fa-home"))
                (tab/deftab "Settings")]]
      (tu/render! [tab/tab-bar :test.tabs tabs])
      ;; The icon span should be present inside the first tab
      (is (some? (tu/query ".tabs li .icon"))))))

(deftest tab-bar-fullwidth-test
  (testing "REGRESSION: :fullwidth? true adds is-fullwidth class"
    (let [tabs [(tab/deftab "A") (tab/deftab "B")]]
      (tu/render! [tab/tab-bar :test.tabs2 tabs :fullwidth? true])
      (is (tu/has-class? (tu/query ".tabs") "is-fullwidth"))))
  (testing ":fullwidth? false does not add is-fullwidth class"
    (let [tabs [(tab/deftab "A") (tab/deftab "B")]]
      (tu/render! [tab/tab-bar :test.tabs3 tabs :fullwidth? false])
      (is (not (tu/has-class? (tu/query ".tabs") "is-fullwidth"))))))

(deftest tab-bar-active-test
  (testing "first tab is active by default"
    (let [tabs [(tab/deftab "First" :value :first)
                (tab/deftab "Second" :value :second)]]
      (tu/render! [tab/tab-bar :test.tabs4 tabs])
      (is (tu/has-class? (tu/query ".tabs li") "is-active"))))
  (testing "clicking a tab makes it active"
    (let [tabs [(tab/deftab "One" :value :one)
                (tab/deftab "Two" :value :two)]]
      (tu/render! [tab/tab-bar :test.tabs5 tabs])
      (let [lis (tu/query-all ".tabs li")]
        (.click (.querySelector (aget lis 1) "a"))
        (r/flush)
        (is (tu/has-class? (aget lis 1) "is-active"))
        (is (not (tu/has-class? (aget lis 0) "is-active")))))))
