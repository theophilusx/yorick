(ns theophilusx.yorick.store-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [reagent.core :as r]
            [theophilusx.yorick.store :as store]))

(deftest contains-in-test
  (testing "returns true for existing nested path"
    (let [m {:a {:b {:c 1}}}]
      (is (store/contains-in? m [:a :b :c]))))
  (testing "returns false for missing nested path"
    (let [m {:a {:b {}}}]
      (is (not (store/contains-in? m [:a :b :c])))))
  (testing "returns false for empty map"
    (is (not (store/contains-in? {} [:a])))))

(deftest assoc-in-test
  (testing "stores value at nested path"
    (let [s (r/atom {})]
      (store/assoc-in! [:a :b] "hello" :store s)
      (is (= "hello" (get-in @s [:a :b])))))
  (testing "assoc! stores at top-level key"
    (let [s (r/atom {})]
      (store/assoc! :name "Alice" :store s)
      (is (= "Alice" (get @s :name))))))

(deftest get-in-test
  (testing "retrieves value at nested path"
    (let [s (r/atom {:a {:b 42}})]
      (is (= 42 (store/get-in [:a :b] :store s)))))
  (testing "returns nil when path absent"
    (let [s (r/atom {})]
      (is (nil? (store/get-in [:a :b] :store s)))))
  (testing "returns :default when path absent"
    (let [s (r/atom {})]
      (is (= :not-found (store/get-in [:a :b] :default :not-found :store s)))))
  (testing "get retrieves top-level key"
    (let [s (r/atom {:x 99})]
      (is (= 99 (store/get :x :store s))))))

(deftest remove-test
  (testing "remove! deletes top-level key"
    (let [s (r/atom {:a 1 :b 2})]
      (store/remove! :a :store s)
      (is (= {:b 2} @s))))
  (testing "remove-in! deletes nested key without disturbing siblings"
    (let [s (r/atom {:a {:b 1 :c 2}})]
      (store/remove-in! [:a :b] :store s)
      (is (= {:a {:c 2}} @s)))))

(deftest update-in-test
  (testing "applies fn to existing value"
    (let [s (r/atom {:count 5})]
      (store/update-in! [:count] inc :store s)
      (is (= 6 (get @s :count)))))
  (testing "passes extra :args to fn"
    (let [s (r/atom {:val 10})]
      (store/update-in! [:val] + :args [5] :store s)
      (is (= 15 (get @s :val))))))

(deftest reset-test
  (testing "replaces entire store contents"
    (let [s (r/atom {:a 1 :b 2})]
      (store/reset! {:c 3} :store s)
      (is (= {:c 3} @s)))))
