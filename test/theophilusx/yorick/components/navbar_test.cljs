(ns theophilusx.yorick.components.navbar-test
  (:require [cljs.test :refer-macros [deftest testing is use-fixtures]]
            [theophilusx.yorick.test-utils :as tu]
            [theophilusx.yorick.navbar :as nav]))

(use-fixtures :each
  {:before tu/setup!
   :after  tu/teardown!})

(deftest navbar-burger-aria-test
  (testing "REGRESSION: burger spans have aria-hidden=true (not false)"
    (let [brand [(nav/link "Home")]]
      (tu/render! [nav/navbar :test.nav {:start [(nav/link "About")]}
                   :brand-data brand :burger? true])
      (let [spans (tu/query-all ".navbar-burger span")]
        (is (= 3 (.-length spans)))
        (dotimes [i 3]
          (is (= "true" (.getAttribute (aget spans i) "aria-hidden"))))))))

(deftest navbar-brand-condition-test
  (testing "REGRESSION: brand section absent when :brand-data is nil"
    (tu/render! [nav/navbar :test.nav2 {:start [(nav/link "Home")]}])
    (is (nil? (tu/query ".navbar-brand"))))
  (testing "brand section present when :brand-data is provided"
    (let [brand [(nav/link "Yorick")]]
      (tu/render! [nav/navbar :test.nav3 {:start [(nav/link "About")]}
                   :brand-data brand])
      (is (some? (tu/query ".navbar-brand"))))))

(deftest navbar-links-test
  (testing "start links render in navbar"
    (tu/render! [nav/navbar :test.nav4
                 {:start [(nav/link "Home")
                          (nav/link "About")]}])
    (is (= 2 (.-length (tu/query-all ".navbar-start .navbar-item"))))))
