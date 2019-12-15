(ns theophilusx.yorick.utils
  (:require [clojure.string :as string]))

(defn cs [& names]
  (string/join " " (filter identity names)))

(defn value-of [e]
  (-> e .-target .-value))

(defn vector-of-vectors [v]
  (every? vector? v))




