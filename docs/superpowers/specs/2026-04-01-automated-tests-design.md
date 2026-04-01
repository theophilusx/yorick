# Automated Test Suite Design

**Project:** Yorick — ClojureScript Reagent/Bulma component library
**Date:** 2026-04-01
**Goal:** Introduce a comprehensive automated test suite covering pure utility functions, store state management, and all UI components — with regression coverage for recently fixed bugs.

---

## Architecture

Two-tier test suite. Tier 1 runs in Node.js for fast, browser-free testing of pure functions. Tier 2 runs in headless Chrome via Karma for component rendering and behaviour tests that require a real DOM.

```
Tier 1: cljs.test + shadow-cljs :node-test    → utils.cljs, store.cljs
Tier 2: cljs.test + shadow-cljs :karma        → all 12 component namespaces
```

No CI/CD pipeline — all tests run locally on demand.

---

## File Layout

```
test/
  theophilusx/yorick/
    utils_test.cljs
    store_test.cljs
    components/
      basic_test.cljs
      input_test.cljs
      navbar_test.cljs
      tab_test.cljs
      table_test.cljs
      icon_test.cljs
      media_test.cljs
      card_test.cljs
      modal_test.cljs
      sidebar_test.cljs
      paginate_test.cljs
      toolbar_test.cljs
```

`test/` is added to `:paths` in `deps.edn`. No new Clojure/ClojureScript dependencies — `cljs.test` is built into ClojureScript.

---

## Configuration Changes

### `shadow-cljs.edn` — two new builds

```clojure
:test {:target    :node-test
       :output-to "target/test/test.js"
       :ns-regexp "theophilusx.yorick.(utils|store)-test"}

:browser-test {:target    :karma
               :output-to "target/karma/test.js"
               :ns-regexp "theophilusx.yorick.components.*-test"}
```

### `karma.conf.js` (new file at project root)

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

### `package.json` — new scripts

```json
"test":         "shadow-cljs node-test test",
"test:browser": "shadow-cljs compile browser-test && karma start --single-run",
"test:watch":   "shadow-cljs watch browser-test"
```

### `deps.edn` — add test path

```clojure
:paths ["src" "demo" "experiment" "test"]
```

### npm packages to install

```bash
npm install --save-dev karma karma-cljs-test karma-chrome-launcher
```

---

## Tier 1: Pure Function Tests

### `utils_test.cljs`

Tests every public function in `theophilusx.yorick.utils`:

| Function | Assertions |
|----------|-----------|
| `cs` | nil args filtered; strings pass through; keywords converted via `name`; vectors joined with spaces; mixed args combined |
| `spath` | `:a.b.c` → `[:a :b :c]`; single segment; `:prefix` option prepends correctly |
| `str->keyword` | spaces, dots, colons, `@` → `-`; result is lowercased |
| `keyword->str` | dashes → spaces; `:initial-caps` capitalises each word |
| `make-value` | uses `:value` if present; falls back to `:title` via `str->keyword`; gensym fallback produces a keyword |
| `vector-of-vectors` | true for `[[1] [2]]`; false for `[1 2]` and `[]` |
| `ensure-vector` | wraps non-vector; passes vector through unchanged |
| `initcaps` | each word capitalised |

### `store_test.cljs`

Each test uses a fresh `(reagent/atom {})` passed via `:store` — never touches `default-store`, no inter-test pollution.

| Function | Assertions |
|----------|-----------|
| `contains-in?` | true/false for nested paths present/absent |
| `assoc-in!` / `assoc!` | value appears at expected path in store |
| `get-in` / `get` | retrieves correct value; `:default` returned when key absent |
| `remove!` / `remove-in!` | key removed; nested key removed without disturbing siblings |
| `update-in!` | applies fn to existing value; extra `:args` passed through |
| `reset!` | replaces entire store contents |

---

## Tier 2: Component Tests

### Infrastructure

Every component test file follows the same lifecycle pattern:

```clojure
(defonce container (atom nil))

(defn setup! []
  (let [el (js/document.createElement "div")]
    (js/document.body.appendChild el)
    (reset! container el)))

(defn teardown! []
  (reagent.dom/unmount-component-at-node @container)
  (js/document.body.removeChild @container)
  (reset! container nil))

(defn render! [component]
  (reagent.dom/render component @container)
  (reagent.core/flush))
```

`use-fixtures :each` calls `setup!` before and `teardown!` after each test. Components that accept `:store` receive a fresh `(reagent/atom {})`. Components that use `default-store` directly have it `reset!` to `{}` in teardown.

Three assertion layers used as appropriate per test:
- **Smoke** — component mounts without throwing
- **Structural** — correct elements, CSS classes, attributes in rendered DOM
- **Behavioural** — DOM events trigger correct store updates or re-renders

### Per-Component Coverage

#### `basic_test.cljs`
- `a`: renders `<a>` with correct `href` attribute
- `img`: renders `<img>` with correct `src` and `alt`
- `notification`: correct Bulma colour class applied; delete button removes component from DOM

#### `input_test.cljs`
- `field`: label text present; horizontal layout applies `is-horizontal`
- `input`: correct `type` attribute; `:disabled` attribute present when set; typing updates store value
- `checkbox`: custom `:class` applies to `<input>` *(regression: `:classs` typo)*; toggling updates store boolean
- `radio`: selecting an option sets correct store value *(regression: `assoc-in!` arg order)*
- `select`: options rendered; selection updates store
- `range-field`: change event updates store with parsed integer *(regression: `parseInt` args)*
- `button`: Bulma modifier classes applied; `:on-click` handler fires

#### `navbar_test.cljs`
- Burger `<span>` elements have `aria-hidden="true"` *(regression)*
- No brand section rendered when `:brand-data` is `nil` *(regression: always-truthy `when`)*
- Brand section renders correctly when `:brand-data` is provided

#### `tab_test.cljs`
- `:icon-data` renders an icon on the tab *(regression: `:icon-datat` typo)*
- `:fullwidth? true` adds `is-fullwidth` class *(regression: missing implementation)*
- Clicking a tab updates the active tab in store

#### `table_test.cljs`
- Header row rendered when `:header` supplied
- Correct number of `<tr>` rows for given body data
- Clicking a row with `:select? true` highlights it *(regression: `:select`/`:select?` mismatch)*
- Two tables with different `sid` values maintain independent selection state

#### `icon_test.cljs`
- Vector icon data produces `has-icons-left` / `has-icons-right` classes *(regression: singular/plural)*
- Single icon renders `<span class="icon">`

#### `media_test.cljs`
- `media-left`, `media-right`, `media-content` render without error
- Correct Bulma `media-left` / `media-right` wrapper class applied

#### `card_test.cljs`
- Header, content, footer sections render when provided
- Absent sections do not produce empty DOM nodes

#### `modal_test.cljs`
- `is-active` class present when store signals open; absent when closed
- Close button sets store to inactive

#### `sidebar_test.cljs`
- `defmenu`/`defentry` macros produce renderable structure
- Clicking an entry updates active item in store

#### `paginate_test.cljs`
- Correct count of `<li>` page elements rendered
- Clicking next/prev updates current page in store

#### `toolbar_test.cljs`
- Renders without error with mixed child components

---

## Running Tests

```bash
# Tier 1 — pure function tests (Node.js)
npm test
# or directly:
npx shadow-cljs node-test test

# Tier 2 — component tests (headless Chrome)
npm run test:browser
# or directly:
npx shadow-cljs compile browser-test && npx karma start --single-run

# Watch mode during active development
# Terminal 1:
npx shadow-cljs watch browser-test
# Terminal 2:
npx karma start
```
