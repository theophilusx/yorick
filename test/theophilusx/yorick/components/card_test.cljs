(ns theophilusx.yorick.components.card-test
  (:require [cljs.test :refer-macros [deftest testing is use-fixtures]]
            [theophilusx.yorick.test-utils :as tu]
            [theophilusx.yorick.card :as c]))

(use-fixtures :each
  {:before tu/setup!
   :after  tu/teardown!})

(deftest card-body-test
  (testing "renders .card-content wrapper"
    (tu/render! [c/card [:p "body text"]])
    (is (some? (tu/query ".card-content"))))
  (testing "body content is rendered"
    (tu/render! [c/card [:p "hello card"]])
    (is (= "hello card" (.-textContent (tu/query ".card-content p"))))))

(deftest card-header-test
  (testing "header rendered when :header provided"
    (tu/render! [c/card [:p "body"] :header {:title "Card Title"}])
    (is (some? (tu/query ".card-header"))))
  (testing "header title text rendered"
    (tu/render! [c/card [:p "body"] :header {:title "My Card"}])
    (is (= "My Card" (.-textContent (tu/query ".card-header-title")))))
  (testing "no header when :header absent"
    (tu/render! [c/card [:p "body"]])
    (is (nil? (tu/query ".card-header")))))

(deftest card-footer-test
  (testing "footer rendered when :footer provided"
    (tu/render! [c/card [:p "body"]
                 :footer {:items ["Save" "Cancel"]}])
    (is (some? (tu/query ".card-footer"))))
  (testing "footer items rendered"
    (tu/render! [c/card [:p "body"]
                 :footer {:items ["Save" "Cancel"]}])
    (is (= 2 (.-length (tu/query-all ".card-footer-item")))))
  (testing "no footer when :footer absent"
    (tu/render! [c/card [:p "body"]])
    (is (nil? (tu/query ".card-footer")))))
