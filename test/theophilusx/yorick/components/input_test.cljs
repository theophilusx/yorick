(ns theophilusx.yorick.components.input-test
  (:require [cljs.test :refer-macros [deftest testing is use-fixtures]]
            [reagent.core :as r]
            [theophilusx.yorick.test-utils :as tu]
            [theophilusx.yorick.input :as i]
            [theophilusx.yorick.store :as store]
            [theophilusx.yorick.utils :refer [spath]]))

(use-fixtures :each
  {:before tu/setup!
   :after  tu/teardown!})

;; --- field ---

(deftest field-test
  (testing "renders label text when :label provided"
    (tu/render! [i/field [:p "body"] :label "First Name"])
    (is (= "First Name" (.-textContent (tu/query ".label")))))
  (testing "no label element when :label absent"
    (tu/render! [i/field [:p "body"]])
    (is (nil? (tu/query ".label")))))

(deftest horizontal-field-test
  (testing "has is-horizontal class"
    (tu/render! [i/horizontal-field "Label" [:p "body"]])
    (is (tu/has-class? (tu/query ".field") "is-horizontal")))
  (testing "label text rendered"
    (tu/render! [i/horizontal-field "My Label" [:p "body"]])
    (is (= "My Label" (.-textContent (tu/query ".label"))))))

;; --- input ---

(deftest input-test
  (testing "renders input of correct type"
    (let [doc (r/atom {})]
      (tu/render! [i/input :text :test.input :store doc])
      (is (= "text" (.getAttribute (tu/query "input") "type")))))
  (testing "renders email type correctly"
    (let [doc (r/atom {})]
      (tu/render! [i/input :email :test.input :store doc])
      (is (= "email" (.getAttribute (tu/query "input") "type")))))
  (testing "disabled attr set when passed in :attrs"
    (let [doc (r/atom {})]
      (tu/render! [i/input :text :test.input :store doc :attrs {:disabled true}])
      (is (some? (.getAttribute (tu/query "input") "disabled")))))
  (testing "typing updates store value"
    (let [doc (r/atom {})]
      (tu/render! [i/input :text :test.input :store doc])
      (let [inp (tu/query "input")]
        (set! (.-value inp) "hello")
        (.dispatchEvent inp (js/Event. "input" #js {"bubbles" true}))
        (r/flush)
        (is (= "hello" (store/get-in (spath :test.input) :store doc)))))))

;; --- checkbox ---

(deftest checkbox-test
  (testing "REGRESSION: custom :classes :input applies to <input> element (not :classs)"
    (let [doc (r/atom {})]
      (tu/render! [i/checkbox "Accept" :test.chk :store doc :classes {:input "my-cb-class"}])
      (is (tu/has-class? (tu/query "input[type=checkbox]") "my-cb-class"))))
  (testing "renders label text"
    (let [doc (r/atom {})]
      (tu/render! [i/checkbox "Accept terms" :test.chk :store doc])
      (is (some? (tu/query ".checkbox")))))
  (testing "clicking toggles store value from false to true"
    (let [doc (r/atom {})]
      (store/assoc-in! (spath :test.chk) false :store doc)
      (tu/render! [i/checkbox "Toggle" :test.chk :store doc])
      (.click (tu/query "input[type=checkbox]"))
      (r/flush)
      (is (= true (store/get-in (spath :test.chk) :store doc))))))

;; --- radio ---

(deftest radio-test
  (testing "REGRESSION: :checked? initialises store value correctly (assoc-in! arg order)"
    (let [doc  (r/atom {})
          btns [{:title "Yes" :value :yes :checked? true}
                {:title "No"  :value :no}]]
      (tu/render! [i/radio :test.radio btns :store doc])
      (is (= :yes (store/get-in (spath :test.radio) :store doc)))))
  (testing "renders one input per button"
    (let [doc  (r/atom {})
          btns [{:title "A" :value :a}
                {:title "B" :value :b}
                {:title "C" :value :c}]]
      (tu/render! [i/radio :test.radio2 btns :store doc])
      (is (= 3 (.-length (tu/query-all "input[type=radio]")))))))

;; --- select ---

(deftest select-test
  (testing "renders correct number of options"
    (let [doc  (r/atom {})
          opts [(i/defoption "Apple"  :value :apple  :selected? true)
                (i/defoption "Banana" :value :banana)
                (i/defoption "Cherry" :value :cherry)]]
      (tu/render! [i/select :test.select opts :store doc])
      (is (= 3 (.-length (tu/query-all "option"))))))
  (testing "initial store value set to selected option"
    (let [doc  (r/atom {})
          opts [(i/defoption "Apple"  :value :apple  :selected? true)
                (i/defoption "Banana" :value :banana)]]
      (tu/render! [i/select :test.select2 opts :store doc])
      (is (= :apple (store/get-in (spath :test.select2) :store doc)))))
  (testing "select element is present"
    (let [doc  (r/atom {})
          opts [(i/defoption "X" :value :x)]]
      (tu/render! [i/select :test.select3 opts :store doc])
      (is (some? (tu/query "select"))))))

;; --- range-field ---

(deftest range-field-test
  (testing "REGRESSION: change event stores integer (parseInt args fix)"
    (let [doc (r/atom {})]
      (tu/render! [i/range-field :test.range 0 100 :store doc :value 50])
      (is (= 50 (store/get-in (spath :test.range) :store doc)))
      (let [inp (tu/query "input[type=range]")]
        (set! (.-value inp) "75")
        (.dispatchEvent inp (js/Event. "input" #js {"bubbles" true}))
        (r/flush)
        (is (= 75 (store/get-in (spath :test.range) :store doc))))))
  (testing "min and max attributes correct"
    (let [doc (r/atom {})]
      (tu/render! [i/range-field :test.range2 10 90 :store doc])
      (let [inp (tu/query "input[type=range]")]
        (is (= "10" (.getAttribute inp "min")))
        (is (= "90" (.getAttribute inp "max")))))))

;; --- button ---

(deftest button-test
  (testing "renders button with title text"
    (tu/render! [i/button "Save" (fn [])])
    (is (= "Save" (.-textContent (tu/query "button")))))
  (testing "Bulma modifier class applied"
    (tu/render! [i/button "Delete" (fn []) :classes {:button "is-danger"}])
    (is (tu/has-class? (tu/query "button") "is-danger")))
  (testing "on-click handler fires when clicked"
    (let [clicked (atom false)]
      (tu/render! [i/button "Go" #(reset! clicked true)])
      (.click (tu/query "button"))
      (is (= true @clicked)))))
