(ns theophilusx.yorick.test-utils
  (:require [reagent.core :as r]
            [reagent.dom.client :as rdomc]
            [theophilusx.yorick.store :as store]))

(def container (atom nil))
(def root (atom nil))

(defn setup! []
  (assert (nil? @container) "setup! called without a preceding teardown!")
  (let [el (js/document.createElement "div")]
    (js/document.body.appendChild el)
    (reset! container el)
    (reset! root (rdomc/create-root el))))

(defn teardown! []
  (when @root
    (.unmount @root)
    (reset! root nil))
  (when @container
    (js/document.body.removeChild @container)
    (reset! container nil))
  ;; Reset default-store so components using it don't leak state between tests
  (store/reset! {}))

(defn render!
  "Render a Reagent component into the test container and flush synchronously."
  [component]
  (rdomc/render @root component)
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
