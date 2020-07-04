(ns theophilusx.yorick.utils
  (:require [clojure.string :as string]
            [goog.string :as gstring]
            [goog.string.format]
            [goog.i18n.NumberFormat.Format])
  (:import (goog.i18n NumberFormat)
           (goog.i18n.NumberFormat Format)))

(defn cs [& names]
  (let [v (filter identity (map #(cond
                                   (string? %) %
                                   (keyword? %) (name %)
                                   (vector? %) (string/join " " %)
                                   :default nil) names))]
    (if (seq v)
      (string/join " " v)))) 

(defn value-of [e]
  (-> e .-target .-value))

(defn vector-of-vectors [v]
  (every? vector? v))

(defn ensure-vector [v]
  (if-not (vector? v)
    [v]
    v))

(defn spath [kw & {:keys [prefix]}]
  (let [init (if prefix
               [prefix]
               [])]
    (reduce (fn [acc v]
              (conj acc (keyword v)))
            init (string/split (name kw) #"\."))))

(defn value->keyword [v]
  (keyword (string/replace v #"\.|:|@|\ " "-")))

(defn initcaps [s]
  (string/join " " (map string/capitalize (string/split s #" "))))

(defn keyword->str [kw & {:keys [initial-caps]}]
  (let [s (string/replace (name kw) #"-" " ")]
    (if initial-caps
      (initcaps s)
      s)))

(defn str-format [fmt & args]
  (apply gstring/format fmt args)) 

(defn number-thousands [num-str]
  (.format (NumberFormat. Format/DECIMAL) num-str))
