(ns theophilusx.yorick.utils-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [theophilusx.yorick.utils :refer [cs spath str->keyword keyword->str
                                              make-value vector-of-vectors
                                              ensure-vector initcaps]]))

(deftest cs-test
  (testing "nil args are filtered out"
    (is (nil? (cs nil nil nil))))
  (testing "single string passes through"
    (is (= "foo" (cs "foo"))))
  (testing "keyword converted via name"
    (is (= "is-primary" (cs :is-primary))))
  (testing "vector of strings joined with space"
    (is (= "a b" (cs ["a" "b"]))))
  (testing "mixed args combined"
    (is (= "foo is-primary a b" (cs "foo" :is-primary ["a" "b"]))))
  (testing "nil mixed in is filtered"
    (is (= "foo bar" (cs "foo" nil "bar")))))

(deftest spath-test
  (testing "dot-separated keyword becomes vector"
    (is (= [:a :b :c] (spath :a.b.c))))
  (testing "single segment keyword"
    (is (= [:a] (spath :a))))
  (testing ":prefix option prepends"
    (is (= [:ui :a :b] (spath :a.b :prefix :ui)))))

(deftest str->keyword-test
  (testing "spaces become dashes"
    (is (= :hello-world (str->keyword "hello world"))))
  (testing "colons become dashes"
    (is (= :-foo (str->keyword ":foo"))))
  (testing "dots become dashes"
    (is (= :a-b (str->keyword "a.b"))))
  (testing "@ becomes dash"
    (is (= :foo-bar (str->keyword "foo@bar"))))
  (testing "result is lowercased"
    (is (= :hello (str->keyword "HELLO")))))

(deftest keyword->str-test
  (testing "dashes become spaces"
    (is (= "hello world" (keyword->str :hello-world))))
  (testing ":initial-caps capitalises each word"
    (is (= "Hello World" (keyword->str :hello-world :initial-caps true)))))

(deftest make-value-test
  (testing "uses :value if present"
    (is (= :foo (make-value {:value :foo :title "Bar"}))))
  (testing "falls back to :title converted to keyword"
    (is (= :hello-world (make-value {:title "hello world"}))))
  (testing "gensym fallback produces a keyword"
    (is (keyword? (make-value {})))))

(deftest vector-of-vectors-test
  (testing "true for vector of vectors"
    (is (vector-of-vectors [[1 2] [3 4]])))
  (testing "false for flat vector"
    (is (not (vector-of-vectors [1 2]))))
  (testing "true for empty vector (vacuous truth)"
    (is (vector-of-vectors []))))

(deftest ensure-vector-test
  (testing "wraps non-vector in vector"
    (is (= [:foo] (ensure-vector :foo))))
  (testing "passes vector through unchanged"
    (is (= [:foo :bar] (ensure-vector [:foo :bar])))))

(deftest initcaps-test
  (testing "capitalises first letter of each word"
    (is (= "Hello World" (initcaps "hello world"))))
  (testing "single word"
    (is (= "Foo" (initcaps "foo")))))
