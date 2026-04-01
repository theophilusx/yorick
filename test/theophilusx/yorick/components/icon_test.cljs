(ns theophilusx.yorick.components.icon-test
  (:require [cljs.test :refer-macros [deftest testing is use-fixtures]]
            [theophilusx.yorick.test-utils :as tu]
            [theophilusx.yorick.icon :as icon]))

(use-fixtures :each
  {:before tu/setup!
   :after  tu/teardown!})

(deftest icon-renders-test
  (testing "single icon renders a .icon span"
    (tu/render! [icon/icon (icon/deficon "fa-home")])
    (is (some? (tu/query "span.icon"))))
  (testing "icon contains an <i> element"
    (tu/render! [icon/icon (icon/deficon "fa-home")])
    (is (some? (tu/query "span.icon i")))))

(deftest icon-control-class-test
  (testing "REGRESSION: vector of icons uses plural has-icons-left/right"
    (let [icon-data [(icon/deficon "fa-user" :position :left)
                     (icon/deficon "fa-check" :position :right)]]
      (is (= "has-icons-left has-icons-right"
             (icon/icon-control-class icon-data)))))
  (testing "single icon map with :left returns has-icons-left"
    (is (= "has-icons-left"
           (icon/icon-control-class (icon/deficon "fa-user" :position :left)))))
  (testing "single icon map with :right returns has-icons-right"
    (is (= "has-icons-right"
           (icon/icon-control-class (icon/deficon "fa-user" :position :right))))))

(deftest deficon-test
  (testing "deficon produces a map with :name"
    (let [ic (icon/deficon "fa-home")]
      (is (= "fa-home" (:name ic)))))
  (testing "deficon sets :position"
    (let [ic (icon/deficon "fa-home" :position :left)]
      (is (= :left (:position ic)))))
  (testing "deficon sets :size"
    (let [ic (icon/deficon "fa-home" :size :medium)]
      (is (= :medium (:size ic)))))
  (testing "deficon without position has nil :position"
    (let [ic (icon/deficon "fa-home")]
      (is (nil? (:position ic))))))
