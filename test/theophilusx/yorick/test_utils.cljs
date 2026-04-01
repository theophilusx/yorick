(ns theophilusx.yorick.test-utils
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [theophilusx.yorick.store :as store]))

(def container (atom nil))

(defn setup! []
  (assert (nil? @container) "setup! called without a preceding teardown!")
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
