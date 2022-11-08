(ns theophilusx.yorick.utils
  "A collection of useful utility functions to assist in working with components."
  (:require [clojure.string :as string]
            [goog.string :as gstring]
            [goog.string.format]
            [goog.i18n.NumberFormat.Format])
  (:import (goog.i18n NumberFormat)
           (goog.i18n.NumberFormat Format)))

(defn cs
  "Build a string of CSS class names from the arguments provided. Arguments can
  be a `string`, `keyword` or `vector` of `strings`. If the argument is a
  keyword, it is converted to a string using `name`."
  [& names]
  (let [v (filter identity (map #(cond
                                   (string? %) %
                                   (keyword? %) (name %)
                                   (vector? %) (string/join " " %)
                                   :else nil) names))]
    (when (seq v)
      (string/join " " v)))) 

(defn value-of
  "Extract the value attribute from the target property of the element `e`."
  [e]
  (-> e .-target .-value))

(defn vector-of-vectors
  "Return `true` if `v` is a `vector` of `vectors`."
  [v]
  (every? vector? v))

(defn ensure-vector
  "If `v` is not a `vector` wrap it in a vector and return it, otherwise just
  return `v`."
  [v]
  (if-not (vector? v)
    [v]
    v))

(defn spath
  "Convert the keyword `kw` to a vector representing a path for a nested map
  structure. If the keyword contains any periods `.`, they are interpreted as
  path separators e.g. `:a.b.c` becomes `[:a :b :c]`. All pat segments are
  interpreted as keywords. If the optional keyword argument `:prefix` is
  supplied, it is added as the first segment of the path."
  [kw & {:keys [prefix]}]
  (let [init (if prefix
               [prefix]
               [])]
    (reduce (fn [acc v]
              (conj acc (keyword v)))
            init (string/split (name kw) #"\."))))

(defn path->sid
  "Convert a vector of keywords representing a path into a storage map into a sid"
  [kw]
  (str ":" (string/join "." (map name kw))))

(defn value->keyword
  "Generates a keyword from the value `v`. Any spaces, commas, colons or
  ampersands are converted to a dash (-)."
  [v]
  (keyword (string/replace v #"\.|:|@|\ " "-")))

(defn initcaps
  "Convert the first letter of each word in the string `s` to a capital."
  [s]
  (string/join " " (map string/capitalize (string/split s #" "))))

(defn keyword->str
  "Convert a keyword `kw` to a `string``. Any dash (-) characters in the keyword are
  converted to spaces."
  [kw & {:keys [initial-caps]}]
  (let [s (string/replace (name kw) #"-" " ")]
    (if initial-caps
      (initcaps s)
      s)))

(defn str-format
  "Use `goog.string/format` to format the arguments `args` with the format `fmt`."
  [fmt & args]
  (apply gstring/format fmt args)) 

(defn number-thousands
  "Format the `string` argument `num-str` with commas to indicate thousands."
  [num-str]
  (.format (NumberFormat. Format/DECIMAL) num-str))
