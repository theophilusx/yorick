(ns theophilusx.yorick.components.media-test
  (:require [cljs.test :refer-macros [deftest testing is use-fixtures]]
            [theophilusx.yorick.test-utils :as tu]
            [theophilusx.yorick.media :as m]))

(use-fixtures :each
  {:before tu/setup!
   :after  tu/teardown!})

(deftest media-left-test
  (testing "renders without error"
    (tu/render! [m/media-left [:p "left content"]])
    (is (some? (tu/query "aside"))))
  (testing "applies media-left Bulma class"
    (tu/render! [m/media-left [:p "content"]])
    (is (tu/has-class? (tu/query "aside") "media-left"))))

(deftest media-right-test
  (testing "renders without error"
    (tu/render! [m/media-right [:p "right content"]])
    (is (some? (tu/query "aside"))))
  (testing "applies media-right Bulma class"
    (tu/render! [m/media-right [:p "content"]])
    (is (tu/has-class? (tu/query "aside") "media-right"))))

(deftest media-test
  (testing "renders article.media wrapper"
    (tu/render! [m/media [:p "main content"]])
    (is (some? (tu/query "article.media"))))
  (testing "renders left section when :left provided"
    (tu/render! [m/media [:p "main"] :left {:content [:p "left"]}])
    (is (some? (tu/query ".media-left"))))
  (testing "renders right section when :right provided"
    (tu/render! [m/media [:p "main"] :right {:content [:p "right"]}])
    (is (some? (tu/query ".media-right"))))
  (testing "no left section when :left absent"
    (tu/render! [m/media [:p "main"]])
    (is (nil? (tu/query ".media-left"))))
  (testing "no right section when :right absent"
    (tu/render! [m/media [:p "main"]])
    (is (nil? (tu/query ".media-right"))))
  (testing "main content is rendered inside .media-content"
    (tu/render! [m/media [:p "main content"]])
    (is (some? (tu/query ".media-content")))))
