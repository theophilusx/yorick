(ns theophilusx.yorick.components.basic-test
  (:require [cljs.test :refer-macros [deftest testing is use-fixtures]]
            [theophilusx.yorick.test-utils :as tu]
            [theophilusx.yorick.basic :as b]))

(use-fixtures :each
  {:before tu/setup!
   :after  tu/teardown!})

(deftest a-test
  (testing "renders an <a> element"
    (tu/render! [b/a "Click me"])
    (is (some? (tu/query "a"))))
  (testing "href attribute is set correctly"
    (tu/render! [b/a "Link" :href "https://example.com"])
    (is (= "https://example.com" (.getAttribute (tu/query "a") "href"))))
  (testing "wraps title in a <span>"
    (tu/render! [b/a "Hello"])
    (is (= "Hello" (.-textContent (tu/query "a span"))))))

(deftest img-test
  (testing "renders an <img> element"
    (tu/render! [b/img "/images/logo.png"])
    (is (some? (tu/query "img"))))
  (testing "src attribute is set correctly"
    (tu/render! [b/img "/images/logo.png"])
    (is (= "/images/logo.png" (.getAttribute (tu/query "img") "src"))))
  (testing "alt attribute is set when provided"
    (tu/render! [b/img "/images/logo.png" :attrs {:alt "Logo"}])
    (is (= "Logo" (.getAttribute (tu/query "img") "alt")))))

(deftest notification-test
  (testing "renders with correct Bulma colour class"
    (tu/render! [b/notification "Watch out!" :class "is-warning"])
    (is (tu/has-class? (tu/query ".notification") "is-warning")))
  (testing "renders body text"
    (tu/render! [b/notification "Hello notification"])
    (is (= "Hello notification" (.-textContent (tu/query ".notification")))))
  (testing "delete button present when :delete true"
    (tu/render! [b/notification "Msg" :delete true])
    (is (some? (tu/query ".notification .delete")))))
