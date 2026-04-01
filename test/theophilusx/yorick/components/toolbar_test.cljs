(ns theophilusx.yorick.components.toolbar-test
  (:require [cljs.test :refer-macros [deftest testing is use-fixtures]]
            [theophilusx.yorick.test-utils :as tu]
            [theophilusx.yorick.toolbar :as tb]))

(use-fixtures :each
  {:before tu/setup!
   :after  tu/teardown!})

;; toolbar signature: (toolbar items & {:keys [right-items class]})
;; defitem: (defitem content & {:keys [type class]}) — :type defaults to :div
;; Items rendered as [type {:class "level-item ..."}] inside .level-left / .level-right

(deftest toolbar-renders-test
  (testing "renders nav.level"
    (let [items [(tb/defitem [:p "Item 1"])
                 (tb/defitem [:p "Item 2"])]]
      (tu/render! [tb/toolbar items])
      (is (some? (tu/query "nav.level")))))
  (testing "items rendered in level-left"
    (let [items [(tb/defitem [:p "A"])
                 (tb/defitem [:p "B"])]]
      (tu/render! [tb/toolbar items])
      (is (= 2 (.-length (tu/query-all ".level-left .level-item"))))))
  (testing "right-items rendered in level-right"
    (let [items       [(tb/defitem [:p "Left"])]
          right-items [(tb/defitem [:p "Right"])]]
      (tu/render! [tb/toolbar items :right-items right-items])
      (is (some? (tu/query ".level-right")))
      (is (= 1 (.-length (tu/query-all ".level-right .level-item"))))))
  (testing "no level-right when :right-items absent"
    (let [items [(tb/defitem [:p "Only"])]]
      (tu/render! [tb/toolbar items])
      (is (nil? (tu/query ".level-right"))))))
