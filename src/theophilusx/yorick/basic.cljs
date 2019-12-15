(ns theophilusx.yorick.basic
  (:require [theophilusx.yorick.utils :refer [cs]]))

(defn box
  "A simple box component. `body` is wrapped in a div with class `box`"
  [body & {:keys [classes]}]
  [:div {:class (cs "box" classes)} body])

;; (defn card
;;   "A basic card component. Can have optional header and footer elements"
;;   [body & {:keys {header-title header-icon card-image }}])
