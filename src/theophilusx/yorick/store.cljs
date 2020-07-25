(ns theophilusx.yorick.store
  (:refer-clojure :exclude [get get-in reset! swap!])
  (:require [reagent.core :as reagent]))

;; This package is blatantly stolen from the reagent-project/utils library
;; see https://github.com/reagent-project/reagent-utils
;; The main difference is to allow specification of the storage atom rather
;; than defaulting to a 'state' atom. While a single state atom is good for
;; much of what needs to be stored during a session, there is other data which
;; requires an atom, but using just a single atom results in a larger and more
;; complex atom than necessary. This module allows the developer to use multiple
;; atoms. The downside of this approach is that if not careful, you can end up
;; with problems managing too many separate atoms. It is important that all
;; atoms are reagent.core/atoms.

(defonce global-state (reagent/atom {}))

(defn cursor
  "Returns a cursor from the state atom. `state` is the reagent atom while
  `ks` are the keys (path) into the atom."
  [state ks]
  (reagent/cursor state ks))

(defn get
  "Get the key's value from the session, returns nil if it doesn't exist."
  [state k & [default]]
  (let [temp-a @(cursor state [k])]
    (if-not (nil? temp-a) temp-a default)))

(defn put!
  "Set the key `k` in the atom `state` to value `v`."
  [state k v]
  (clojure.core/swap! state assoc k v))

(defn get-in
 "Gets the value at the path specified by the vector ks from the session,
  returns nil if it doesn't exist."
  [state ks & [default]]
  (let [result @(cursor state ks)]
    (if-not (nil? result) result default)))

(defn swap!
  "Replace the current session's value with the result of executing f with
  the current value and args."
  [state f & args]
  (apply clojure.core/swap! state f args))

(defn clear!
  "Remove all data from the session and start over cleanly."
  [state]
  (clojure.core/reset! state {}))

(defn reset!
  "Reset the state of the atom `state` to the value `m`."
  [state m]
  (clojure.core/reset! state m))

(defn remove!
  "Remove a key from the session"
  [state k]
  (clojure.core/swap! state dissoc k))

(defn assoc-in!
  "Associates a value in the session, where ks is a
   sequence of keys and v is the new value and returns
   a new nested structure. If any levels do not exist,
   hash-maps will be created."
  [state ks v]
  (clojure.core/swap! state assoc-in  ks v))

(defn get!
  "Destructive get from the session. This returns the current value of the key
  and then removes it from the session."
  [state k & [default]]
  (let [cur (get state k default)]
    (remove! state k)
    cur))

(defn get-in!
  "Destructive get from the session. This returns the current value of the path
  specified by the vector ks and then removes it from the session."
  [state ks & [default]]
    (let [cur (get-in state ks default)]
      (assoc-in! state ks nil)
      cur))

(defn update!
  "Updates a value in session where k is a key and f
   is the function that takes the old value along with any
   supplied args and return the new value. If key is not
   present it will be added."
  [state k f & args]
  (clojure.core/swap!
    state
    #(apply (partial update % k f) args)))

(defn update-in!
  "Updates a value in the session, where ks is a
   sequence of keys and f is a function that will
   take the old value along with any supplied args and return
   the new value. If any levels do not exist, hash-maps
   will be created."
  [state ks f & args]
  (clojure.core/swap!
    state
    #(apply (partial update-in % ks f) args)))
