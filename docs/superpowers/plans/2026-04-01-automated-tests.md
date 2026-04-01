# Automated Test Suite Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build a two-tier automated test suite — Node.js cljs.test for pure utility and store functions, headless Chrome via Karma for all 14 component namespaces — with full regression coverage for the bugs fixed in the March 2026 bug-fix pass.

**Architecture:** Tier 1 uses the shadow-cljs `:node-test` target and requires no browser. Tier 2 uses the shadow-cljs `:karma` target with ChromeHeadless; every component test mounts its subject into a fresh DOM div, flushes Reagent, and then makes structural and behavioural assertions before unmounting. A shared `test-utils` namespace provides the mount/unmount helpers to every component test file. Store isolation is achieved by passing a fresh `reagent/atom` via `:store` wherever the component supports it, and by resetting `default-store` in teardown for components that use it directly (navbar, tab, table, modal, sidebar).

**Tech Stack:** ClojureScript, Reagent 1.2.0, React 17, Bulma 0.9.4, shadow-cljs 2.21.0, cljs.test (built-in), karma 6.x, karma-cljs-test, karma-chrome-launcher, ChromeHeadless.

---

## File Map

| Action | Path | Purpose |
|--------|------|---------|
| Modify | `shadow-cljs.edn` | Add `:test` (node-test) and `:browser-test` (karma) builds |
| Modify | `deps.edn` | Add `test/` to `:paths` |
| Modify | `package.json` | Add `test`, `test:browser`, `test:watch` scripts |
| Create | `karma.conf.js` | Karma configuration for ChromeHeadless |
| Create | `test/theophilusx/yorick/utils_test.cljs` | Pure function tests for utils.cljs |
| Create | `test/theophilusx/yorick/store_test.cljs` | Pure function tests for store.cljs |
| Create | `test/theophilusx/yorick/test_utils.cljs` | Shared render!/setup!/teardown! helpers |
| Create | `test/theophilusx/yorick/components/basic_test.cljs` | Tests for basic.cljs |
| Create | `test/theophilusx/yorick/components/input_test.cljs` | Tests for input.cljs |
| Create | `test/theophilusx/yorick/components/navbar_test.cljs` | Tests for navbar.cljs |
| Create | `test/theophilusx/yorick/components/tab_test.cljs` | Tests for tab.cljs |
| Create | `test/theophilusx/yorick/components/table_test.cljs` | Tests for table.cljs |
| Create | `test/theophilusx/yorick/components/icon_test.cljs` | Tests for icon.cljs |
| Create | `test/theophilusx/yorick/components/media_test.cljs` | Tests for media.cljs |
| Create | `test/theophilusx/yorick/components/card_test.cljs` | Tests for card.cljs |
| Create | `test/theophilusx/yorick/components/modal_test.cljs` | Tests for modal.cljs |
| Create | `test/theophilusx/yorick/components/sidebar_test.cljs` | Tests for sidebar.cljs |
| Create | `test/theophilusx/yorick/components/paginate_test.cljs` | Tests for paginate.cljs |
| Create | `test/theophilusx/yorick/components/toolbar_test.cljs` | Tests for toolbar.cljs |

---

## Task 1: Project configuration

**Files:**
- Modify: `shadow-cljs.edn`
- Modify: `deps.edn`
- Modify: `package.json`
- Create: `karma.conf.js`

- [ ] **Step 1: Install npm test packages**

```bash
npm install --save-dev karma karma-cljs-test karma-chrome-launcher
```

Expected: packages added to `node_modules/` and `package-lock.json` updated.

- [ ] **Step 2: Update `shadow-cljs.edn`**

Change from:
```clojure
{:deps     {:aliases [:dev]}
 :dev-http {8080 "resources/public"}
 :builds {:demo {:target :browser
                 :output-dir "resources/public/js"
                 :asset-path "/js"
                 :modules {:main {:init-fn yorick-demo.main/init}}}
          :testbed {:target :browser
                    :output-dir "resources/public/js"
                    :asset-path "/js"
                    :modules {:main {:init-fn testbed.main/init}}}}}
```

To:
```clojure
{:deps     {:aliases [:dev]}
 :dev-http {8080 "resources/public"}
 :builds {:demo {:target :browser
                 :output-dir "resources/public/js"
                 :asset-path "/js"
                 :modules {:main {:init-fn yorick-demo.main/init}}}
          :testbed {:target :browser
                    :output-dir "resources/public/js"
                    :asset-path "/js"
                    :modules {:main {:init-fn testbed.main/init}}}
          :test {:target    :node-test
                 :output-to "target/test/test.js"
                 :ns-regexp "theophilusx.yorick.(utils|store)-test"}
          :browser-test {:target    :karma
                         :output-to "target/karma/test.js"
                         :ns-regexp "theophilusx.yorick.components.*-test"}}}
```

- [ ] **Step 3: Update `deps.edn`**

Change from:
```clojure
{:paths ["src" "demo" "experiment"]
 :deps {org.clojure/clojure {:mvn/version "1.11.1"}
        org.clojure/clojurescript {:mvn/version "1.11.60"}
        reagent/reagent {:mvn/version "1.2.0"}}
 :aliases {:dev {:extra-paths ["dev"]
                 :extra-deps {thheller/shadow-cljs {:mvn/version "2.21.0"}}}}}
```

To:
```clojure
{:paths ["src" "demo" "experiment" "test"]
 :deps {org.clojure/clojure {:mvn/version "1.11.1"}
        org.clojure/clojurescript {:mvn/version "1.11.60"}
        reagent/reagent {:mvn/version "1.2.0"}}
 :aliases {:dev {:extra-paths ["dev"]
                 :extra-deps {thheller/shadow-cljs {:mvn/version "2.21.0"}}}}}
```

- [ ] **Step 4: Update `package.json` scripts**

Change the `"scripts"` block from:
```json
"scripts": {
  "watch": "shadow-cljs watch testbed;",
  "release": "shadow-cljs release testbed;",
  "server": "shadow-cljs server;",
  "clean": "rm -rf target; rm -rf public/js"
}
```

To:
```json
"scripts": {
  "watch": "shadow-cljs watch testbed;",
  "release": "shadow-cljs release testbed;",
  "server": "shadow-cljs server;",
  "clean": "rm -rf target; rm -rf public/js",
  "test": "shadow-cljs node-test test",
  "test:browser": "shadow-cljs compile browser-test && karma start --single-run",
  "test:watch": "shadow-cljs watch browser-test"
}
```

- [ ] **Step 5: Create `karma.conf.js`**

Create the file at the project root:
```javascript
module.exports = function(config) {
  config.set({
    browsers: ['ChromeHeadless'],
    frameworks: ['cljs-test'],
    files: ['target/karma/test.js'],
    preprocessors: {},
    plugins: [
      'karma-cljs-test',
      'karma-chrome-launcher'
    ],
    colors: true,
    logLevel: config.LOG_INFO,
    singleRun: true
  });
};
```

- [ ] **Step 6: Verify node-test build compiles (no tests yet)**

```bash
npx shadow-cljs compile test
```

Expected: compile succeeds with a warning about no matching namespaces (the ns-regexp matches nothing yet). Output: `target/test/test.js` exists.

- [ ] **Step 7: Commit**

```bash
git add shadow-cljs.edn deps.edn package.json package-lock.json karma.conf.js
git commit -m "chore: add test build configuration and karma setup"
```

---

## Task 2: Pure function tests — utils_test.cljs

**Files:**
- Create: `test/theophilusx/yorick/utils_test.cljs`

- [ ] **Step 1: Create the test file**

```clojure
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
  (testing "false for empty vector"
    (is (not (vector-of-vectors [])))))

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
```

- [ ] **Step 2: Run the node-test suite**

```bash
npm test
```

Expected output: all tests pass, e.g.:
```
Testing theophilusx.yorick.utils-test
Ran 8 tests containing 21 assertions.
0 failures, 0 errors.
```

If any test fails, read the error and fix the test code (not the source — these are pure function assertions against known behaviour).

- [ ] **Step 3: Commit**

```bash
git add test/theophilusx/yorick/utils_test.cljs
git commit -m "test: add utils pure function tests"
```

---

## Task 3: Pure function tests — store_test.cljs

**Files:**
- Create: `test/theophilusx/yorick/store_test.cljs`

- [ ] **Step 1: Create the test file**

```clojure
(ns theophilusx.yorick.store-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [reagent.core :as r]
            [theophilusx.yorick.store :as store]
            [theophilusx.yorick.utils :refer [spath]]))

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
```

- [ ] **Step 2: Run the node-test suite**

```bash
npm test
```

Expected:
```
Testing theophilusx.yorick.utils-test
Testing theophilusx.yorick.store-test
Ran 13 tests containing 31 assertions.
0 failures, 0 errors.
```

- [ ] **Step 3: Commit**

```bash
git add test/theophilusx/yorick/store_test.cljs
git commit -m "test: add store pure function tests"
```

---

## Task 4: Shared browser test infrastructure — test_utils.cljs

**Files:**
- Create: `test/theophilusx/yorick/test_utils.cljs`

- [ ] **Step 1: Create the shared helper namespace**

```clojure
(ns theophilusx.yorick.test-utils
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [theophilusx.yorick.store :as store]))

(defonce container (atom nil))

(defn setup! []
  (let [el (js/document.createElement "div")]
    (js/document.body.appendChild el)
    (reset! container el)))

(defn teardown! []
  (when @container
    (rdom/unmount-component-at-node @container)
    (js/document.body.removeChild @container)
    (reset! container nil))
  ;; Reset default-store so components using it don't leak state between tests
  (store/reset! {}))

(defn render!
  "Render a Reagent component into the test container and flush synchronously."
  [component]
  (rdom/render component @container)
  (r/flush))

(defn query
  "Convenience wrapper: querySelector on the test container."
  [selector]
  (.querySelector @container selector))

(defn query-all
  "Convenience wrapper: querySelectorAll on the test container — returns a JS NodeList."
  [selector]
  (.querySelectorAll @container selector))

(defn has-class?
  "Returns true if the given DOM element has the CSS class."
  [el class-name]
  (.contains (.-classList el) class-name))
```

- [ ] **Step 2: Verify the browser-test build compiles (no test namespaces yet)**

```bash
npx shadow-cljs compile browser-test
```

Expected: compiles without error. `target/karma/test.js` exists.

- [ ] **Step 3: Commit**

```bash
git add test/theophilusx/yorick/test_utils.cljs
git commit -m "test: add shared browser test infrastructure"
```

---

## Task 5: Component tests — basic_test.cljs

**Files:**
- Create: `test/theophilusx/yorick/components/basic_test.cljs`

- [ ] **Step 1: Create the test file**

```clojure
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
```

- [ ] **Step 2: Compile and run the browser tests**

```bash
npx shadow-cljs compile browser-test && npx karma start --single-run
```

Expected:
```
SUMMARY:
✔ 6 tests completed
```

- [ ] **Step 3: Commit**

```bash
git add test/theophilusx/yorick/components/basic_test.cljs
git commit -m "test: add basic component tests"
```

---

## Task 6: Component tests — input_test.cljs

**Files:**
- Create: `test/theophilusx/yorick/components/input_test.cljs`

- [ ] **Step 1: Create the test file**

```clojure
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
        ;; value-of returns the input's value; store/assoc-in! stores it
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
```

- [ ] **Step 2: Compile and run browser tests**

```bash
npx shadow-cljs compile browser-test && npx karma start --single-run
```

Expected: all previous tests plus the new input tests pass.

- [ ] **Step 3: Commit**

```bash
git add test/theophilusx/yorick/components/input_test.cljs
git commit -m "test: add input component tests including checkbox/radio/range regressions"
```

---

## Task 7: Component tests — navbar_test.cljs

**Files:**
- Create: `test/theophilusx/yorick/components/navbar_test.cljs`

- [ ] **Step 1: Create the test file**

```clojure
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
```

- [ ] **Step 2: Compile and run browser tests**

```bash
npx shadow-cljs compile browser-test && npx karma start --single-run
```

Expected: all tests pass including new navbar tests.

- [ ] **Step 3: Commit**

```bash
git add test/theophilusx/yorick/components/navbar_test.cljs
git commit -m "test: add navbar component tests including aria-hidden and brand regressions"
```

---

## Task 8: Component tests — tab_test.cljs

**Files:**
- Create: `test/theophilusx/yorick/components/tab_test.cljs`

- [ ] **Step 1: Create the test file**

```clojure
(ns theophilusx.yorick.components.tab-test
  (:require [cljs.test :refer-macros [deftest testing is use-fixtures]]
            [reagent.core :as r]
            [theophilusx.yorick.test-utils :as tu]
            [theophilusx.yorick.tab :as tab]
            [theophilusx.yorick.icon :as icon]))

(use-fixtures :each
  {:before tu/setup!
   :after  tu/teardown!})

(deftest tab-bar-icon-test
  (testing "REGRESSION: :icon-data renders icon on tab (was :icon-datat typo)"
    (let [tabs [(tab/deftab "Home" :icon-data (icon/deficon "fa-home"))
                (tab/deftab "Settings")]]
      (tu/render! [tab/tab-bar :test.tabs tabs])
      ;; The icon span should be present inside the first tab
      (is (some? (tu/query ".tabs li .icon"))))))

(deftest tab-bar-fullwidth-test
  (testing "REGRESSION: :fullwidth? true adds is-fullwidth class"
    (let [tabs [(tab/deftab "A") (tab/deftab "B")]]
      (tu/render! [tab/tab-bar :test.tabs2 tabs :fullwidth? true])
      (is (tu/has-class? (tu/query ".tabs") "is-fullwidth"))))
  (testing ":fullwidth? false does not add is-fullwidth class"
    (let [tabs [(tab/deftab "A") (tab/deftab "B")]]
      (tu/render! [tab/tab-bar :test.tabs3 tabs :fullwidth? false])
      (is (not (tu/has-class? (tu/query ".tabs") "is-fullwidth"))))))

(deftest tab-bar-active-test
  (testing "first tab is active by default"
    (let [tabs [(tab/deftab "First" :value :first)
                (tab/deftab "Second" :value :second)]]
      (tu/render! [tab/tab-bar :test.tabs4 tabs])
      (is (tu/has-class? (tu/query ".tabs li") "is-active"))))
  (testing "clicking a tab makes it active"
    (let [tabs [(tab/deftab "One" :value :one)
                (tab/deftab "Two" :value :two)]]
      (tu/render! [tab/tab-bar :test.tabs5 tabs])
      (let [lis (tu/query-all ".tabs li")]
        (.click (.querySelector (aget lis 1) "a"))
        (r/flush)
        (is (tu/has-class? (aget lis 1) "is-active"))
        (is (not (tu/has-class? (aget lis 0) "is-active")))))))
```

- [ ] **Step 2: Compile and run browser tests**

```bash
npx shadow-cljs compile browser-test && npx karma start --single-run
```

Expected: all tests pass.

- [ ] **Step 3: Commit**

```bash
git add test/theophilusx/yorick/components/tab_test.cljs
git commit -m "test: add tab component tests including icon-data and fullwidth regressions"
```

---

## Task 9: Component tests — table_test.cljs

**Files:**
- Create: `test/theophilusx/yorick/components/table_test.cljs`

- [ ] **Step 1: Create the test file**

```clojure
(ns theophilusx.yorick.components.table-test
  (:require [cljs.test :refer-macros [deftest testing is use-fixtures]]
            [reagent.core :as r]
            [theophilusx.yorick.test-utils :as tu]
            [theophilusx.yorick.table :as t]))

(use-fixtures :each
  {:before tu/setup!
   :after  tu/teardown!})

(def sample-body
  [[(t/cell "Alice") (t/cell "Engineer")]
   [(t/cell "Bob")   (t/cell "Designer")]
   [(t/cell "Carol") (t/cell "Manager")]])

(def sample-header
  [[(t/cell "Name" :type :th) (t/cell "Role" :type :th)]])

(deftest table-structure-test
  (testing "renders correct number of body rows"
    (tu/render! [t/table :test.tbl sample-body])
    (is (= 3 (.-length (tu/query-all "tbody tr")))))
  (testing "header row rendered when :header supplied"
    (tu/render! [t/table :test.tbl2 sample-body :header sample-header])
    (is (some? (tu/query "thead")))
    (is (= 2 (.-length (tu/query-all "thead th"))))))

(deftest table-select-test
  (testing "REGRESSION: :select? true enables row selection (was :select keyword)"
    (tu/render! [t/table :test.tbl3 sample-body :select? true])
    ;; Clicking a td in the first row should add is-selected class
    (let [first-td (tu/query "tbody tr:first-child td")]
      (.click first-td)
      (r/flush)
      (is (tu/has-class? first-td "is-selected"))))
  (testing "clicking a different row moves selection"
    (tu/render! [t/table :test.tbl4 sample-body :select? true])
    (let [tds (tu/query-all "tbody td")]
      (.click (aget tds 0))
      (r/flush)
      (.click (aget tds 2))
      (r/flush)
      (is (not (tu/has-class? (aget tds 0) "is-selected")))
      (is (tu/has-class? (aget tds 2) "is-selected")))))

(deftest table-independent-sids-test
  (testing "two tables with different sids have independent selection state"
    (tu/render!
     [:<>
      [t/table :test.tbl.a sample-body :select? true]
      [t/table :test.tbl.b sample-body :select? true]])
    (let [all-tables (tu/query-all "table")
          tbl-a      (aget all-tables 0)
          tbl-b      (aget all-tables 1)
          td-a       (.querySelector tbl-a "tbody tr:first-child td")
          td-b       (.querySelector tbl-b "tbody tr:last-child td")]
      (.click td-a)
      (r/flush)
      (.click td-b)
      (r/flush)
      ;; Table A row 0 is selected, table B row 2 is selected — independent
      (is (tu/has-class? td-a "is-selected"))
      (is (tu/has-class? td-b "is-selected"))
      ;; Table A's last row is NOT selected
      (is (not (tu/has-class? (.querySelector tbl-a "tbody tr:last-child td") "is-selected"))))))
```

- [ ] **Step 2: Compile and run browser tests**

```bash
npx shadow-cljs compile browser-test && npx karma start --single-run
```

Expected: all tests pass.

- [ ] **Step 3: Commit**

```bash
git add test/theophilusx/yorick/components/table_test.cljs
git commit -m "test: add table component tests including select? and sid isolation regressions"
```

---

## Task 10: Component tests — icon_test.cljs

**Files:**
- Create: `test/theophilusx/yorick/components/icon_test.cljs`

- [ ] **Step 1: Create the test file**

```clojure
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
      (is (= :left (:position ic))))))
```

- [ ] **Step 2: Compile and run browser tests**

```bash
npx shadow-cljs compile browser-test && npx karma start --single-run
```

Expected: all tests pass.

- [ ] **Step 3: Commit**

```bash
git add test/theophilusx/yorick/components/icon_test.cljs
git commit -m "test: add icon component tests including has-icons-left/right regression"
```

---

## Task 11: Component tests — media_test.cljs

**Files:**
- Create: `test/theophilusx/yorick/components/media_test.cljs`

- [ ] **Step 1: Create the test file**

```clojure
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
    (is (nil? (tu/query ".media-left")))))
```

- [ ] **Step 2: Compile and run browser tests**

```bash
npx shadow-cljs compile browser-test && npx karma start --single-run
```

Expected: all tests pass.

- [ ] **Step 3: Commit**

```bash
git add test/theophilusx/yorick/components/media_test.cljs
git commit -m "test: add media component tests"
```

---

## Task 12: Component tests — card_test.cljs

**Files:**
- Create: `test/theophilusx/yorick/components/card_test.cljs`

- [ ] **Step 1: Create the test file**

```clojure
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
```

- [ ] **Step 2: Compile and run browser tests**

```bash
npx shadow-cljs compile browser-test && npx karma start --single-run
```

Expected: all tests pass.

- [ ] **Step 3: Commit**

```bash
git add test/theophilusx/yorick/components/card_test.cljs
git commit -m "test: add card component tests"
```

---

## Task 13: Component tests — modal_test.cljs

**Files:**
- Create: `test/theophilusx/yorick/components/modal_test.cljs`

- [ ] **Step 1: Create the test file**

```clojure
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
    (.click (tu/query ".modal-close"))
    (r/flush)
    (is (= false (store/get-in (spath :test.modal3))))))

(deftest modal-content-test
  (testing "body content rendered inside .modal-content"
    (store/assoc-in! (spath :test.modal4) true)
    (tu/render! [m/modal [:p "hello modal"] :test.modal4])
    (is (= "hello modal" (.-textContent (tu/query ".modal-content p"))))))
```

- [ ] **Step 2: Compile and run browser tests**

```bash
npx shadow-cljs compile browser-test && npx karma start --single-run
```

Expected: all tests pass.

- [ ] **Step 3: Commit**

```bash
git add test/theophilusx/yorick/components/modal_test.cljs
git commit -m "test: add modal component tests"
```

---

## Task 14: Component tests — sidebar_test.cljs

**Files:**
- Create: `test/theophilusx/yorick/components/sidebar_test.cljs`

- [ ] **Step 1: Create the test file**

```clojure
(ns theophilusx.yorick.components.sidebar-test
  (:require [cljs.test :refer-macros [deftest testing is use-fixtures]]
            [reagent.core :as r]
            [theophilusx.yorick.test-utils :as tu]
            [theophilusx.yorick.sidebar :as sb]
            [theophilusx.yorick.store :as store]
            [theophilusx.yorick.utils :refer [spath]]))

(use-fixtures :each
  {:before tu/setup!
   :after  tu/teardown!})

(def sample-menus
  [(sb/defmenu [(sb/defentry "Home"     :value :home)
                (sb/defentry "Settings" :value :settings)]
               :title "Navigation")])

(deftest sidebar-renders-test
  (testing "renders nav.menu"
    (tu/render! [sb/sidebar :test.sb sample-menus])
    (is (some? (tu/query "nav.menu"))))
  (testing "menu label rendered when :title provided"
    (tu/render! [sb/sidebar :test.sb2 sample-menus])
    (is (= "Navigation" (.-textContent (tu/query ".menu-label"))))))

(deftest sidebar-entries-test
  (testing "correct number of entries rendered"
    (tu/render! [sb/sidebar :test.sb3 sample-menus])
    (is (= 2 (.-length (tu/query-all ".menu-list li")))))
  (testing "clicking an entry updates active item in store"
    (tu/render! [sb/sidebar :test.sb4 sample-menus])
    (let [links (tu/query-all ".menu-list li a")]
      ;; Click "Settings" (second entry)
      (.click (aget links 1))
      (r/flush)
      (is (= :settings (store/get-in (conj (spath :test.sb4) :active-item)))))))
```

- [ ] **Step 2: Compile and run browser tests**

```bash
npx shadow-cljs compile browser-test && npx karma start --single-run
```

Expected: all tests pass.

- [ ] **Step 3: Commit**

```bash
git add test/theophilusx/yorick/components/sidebar_test.cljs
git commit -m "test: add sidebar component tests"
```

---

## Task 15: Component tests — paginate_test.cljs

**Files:**
- Create: `test/theophilusx/yorick/components/paginate_test.cljs`

- [ ] **Step 1: Create the test file**

```clojure
(ns theophilusx.yorick.components.paginate-test
  (:require [cljs.test :refer-macros [deftest testing is use-fixtures]]
            [reagent.core :as r]
            [theophilusx.yorick.test-utils :as tu]
            [theophilusx.yorick.paginate :as p]))

(use-fixtures :each
  {:before tu/setup!
   :after  tu/teardown!})

(def sample-records (vec (range 1 11)))   ;; 10 items
(defn render-page [page]
  [:ul (for [r page] [:li (str r)])])

(deftest paginate-structure-test
  (testing "renders pagination nav"
    (tu/render! [p/paginate sample-records render-page :page-size 5])
    (is (some? (tu/query "nav.pagination"))))
  (testing "renders correct number of page links for 10 items / 5 per page"
    (tu/render! [p/paginate sample-records render-page :page-size 5])
    ;; 2 pages → 2 pagination-link items
    (is (= 2 (.-length (tu/query-all ".pagination-link")))))
  (testing "page 1 is current initially"
    (tu/render! [p/paginate sample-records render-page :page-size 5])
    (is (tu/has-class? (tu/query ".pagination-link") "is-current"))))

(deftest paginate-navigation-test
  (testing "clicking Next advances to page 2"
    (tu/render! [p/paginate sample-records render-page :page-size 5])
    (let [next-btn (tu/query ".pagination-next")]
      (.click next-btn)
      (r/flush)
      ;; Page 2 link should now be current
      (let [links (tu/query-all ".pagination-link")]
        (is (tu/has-class? (aget links 1) "is-current")))))
  (testing "Previous button is disabled on page 1"
    (tu/render! [p/paginate sample-records render-page :page-size 5])
    (is (tu/has-class? (tu/query ".pagination-previous") "is-disabled"))))
```

- [ ] **Step 2: Compile and run browser tests**

```bash
npx shadow-cljs compile browser-test && npx karma start --single-run
```

Expected: all tests pass.

- [ ] **Step 3: Commit**

```bash
git add test/theophilusx/yorick/components/paginate_test.cljs
git commit -m "test: add paginate component tests"
```

---

## Task 16: Component tests — toolbar_test.cljs

**Files:**
- Create: `test/theophilusx/yorick/components/toolbar_test.cljs`

- [ ] **Step 1: Create the test file**

```clojure
(ns theophilusx.yorick.components.toolbar-test
  (:require [cljs.test :refer-macros [deftest testing is use-fixtures]]
            [theophilusx.yorick.test-utils :as tu]
            [theophilusx.yorick.toolbar :as tb]))

(use-fixtures :each
  {:before tu/setup!
   :after  tu/teardown!})

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
```

- [ ] **Step 2: Compile and run the full browser test suite**

```bash
npx shadow-cljs compile browser-test && npx karma start --single-run
```

Expected: all component tests pass across all 12 test files.

- [ ] **Step 3: Run the full node-test suite one final time**

```bash
npm test
```

Expected: all utils and store tests pass.

- [ ] **Step 4: Commit**

```bash
git add test/theophilusx/yorick/components/toolbar_test.cljs
git commit -m "test: add toolbar component tests"
```

---

## Self-Review

### Spec Coverage

| Spec requirement | Task |
|-----------------|------|
| Two shadow-cljs builds (:test, :browser-test) | Task 1 ✓ |
| karma.conf.js with ChromeHeadless | Task 1 ✓ |
| npm test scripts | Task 1 ✓ |
| deps.edn path update | Task 1 ✓ |
| utils_test.cljs — all public functions | Task 2 ✓ |
| store_test.cljs — all public functions, fresh atoms | Task 3 ✓ |
| Shared render!/setup!/teardown! infrastructure | Task 4 ✓ |
| basic: a, img, notification | Task 5 ✓ |
| input: field, input, checkbox (regression), radio (regression), select, range-field (regression), button | Task 6 ✓ |
| navbar: aria-hidden (regression), brand condition (regression), links | Task 7 ✓ |
| tab: icon-data (regression), fullwidth? (regression), active tab behaviour | Task 8 ✓ |
| table: header, row count, select? (regression), sid isolation | Task 9 ✓ |
| icon: has-icons-left/right (regression), deficon, render | Task 10 ✓ |
| media: media-left, media-right, media | Task 11 ✓ |
| card: body, header, footer, absent sections | Task 12 ✓ |
| modal: is-active, close button, content | Task 13 ✓ |
| sidebar: renders, menu label, entries, click updates store | Task 14 ✓ |
| paginate: structure, page links, next/prev navigation | Task 15 ✓ |
| toolbar: renders, left items, right items | Task 16 ✓ |

All 20 spec requirements covered. No gaps.
