(ns theophilusx.yorick.test-utils
  (:require [reagent.core :as r]
            [reagent.dom.client :as rdomc]
            ["react-dom" :as react-dom]
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
  "Render a Reagent component into the test container and flush synchronously.
  Uses flushSync to force React 19's concurrent scheduler to commit immediately."
  [component]
  (react-dom/flushSync (fn [] (rdomc/render @root component)))
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

(defn set-input-value!
  "Set the value of a controlled React input element using the native prototype
  setter, bypassing React 19's _valueTracker so the subsequent input event is
  recognised as a real change. Dispatch a bubbling 'input' event afterwards."
  [el value]
  (let [native-setter (-> js/Object
                          (.getOwnPropertyDescriptor js/HTMLInputElement.prototype "value")
                          .-set)]
    (.call native-setter el value))
  (.dispatchEvent el (js/Event. "input" #js {"bubbles" true})))
