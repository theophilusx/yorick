(ns theophilusx.yorick.components.modal-test
  (:require [cljs.test :refer-macros [deftest testing is use-fixtures]]
            [reagent.core :as r]
            [theophilusx.yorick.test-utils :as tu]
            [theophilusx.yorick.modal :as m]
            [theophilusx.yorick.store :as store]
            [theophilusx.yorick.utils :refer [spath]]))

(use-fixtures :each
  {:before tu/setup!
   :after  tu/teardown!})

;; modal signature: (modal body sid & {:keys [classes]})
;; body is first arg, sid is second

(deftest modal-active-test
  (testing "is-active class present when store value is true"
    (store/assoc-in! (spath :test.modal) true)
    (tu/render! [m/modal [:p "modal body"] :test.modal])
    (is (tu/has-class? (tu/query ".modal") "is-active")))
  (testing "is-active class absent when store value is false"
    (store/assoc-in! (spath :test.modal2) false)
    (tu/render! [m/modal [:p "modal body"] :test.modal2])
    (is (not (tu/has-class? (tu/query ".modal") "is-active")))))

(deftest modal-close-test
  (testing "close button sets store to false"
    (store/assoc-in! (spath :test.modal3) true)
    (tu/render! [m/modal [:p "body"] :test.modal3])
    ;; Close button is button.modal-close.is-large
    (.click (tu/query ".modal-close"))
    (r/flush)
    (is (= false (store/get-in (spath :test.modal3))))))

(deftest modal-content-test
  (testing "body content rendered inside .modal-content"
    (store/assoc-in! (spath :test.modal4) true)
    (tu/render! [m/modal [:p "hello modal"] :test.modal4])
    (is (= "hello modal" (.-textContent (tu/query ".modal-content p"))))))
