(ns theophilusx.yorick.store
  (:refer-clojure :exclude [get get-in reset! assoc!])
  (:require [reagent.core :as reagent]))

(def default-store (reagent/atom {}))

(defn contains-in? [m ks]
  (if (not (contains? m (first ks)))
    false
    (if (empty? (rest ks))
      true
      (recur (clojure.core/get m (first ks)) (rest ks)))))

(defn cursor
  "Returns a cursor into the store atom. The `ks` argument is a vector
  specifying the path into the store.The optional :store keyword argument
  can be used to set the store atom to use. Defaults to `default-store`."
  [ks & {:keys [store] :or {store default-store}}]
  (reagent/cursor store ks))

(defn get-in
  "Get value from store associated with the path `ks`, a vector of keys. The
  optional keyword argument `:default` can be used to specify a default value
  to be used if value is nil. Keyword :store can be used to specify an
  alternative atom store. Default store is `default-store`."
  [ks & {:keys [default store] :or {store default-store}}]
  (let [val @(cursor ks :store store)]
    (if-not (nil? val) val default)))

(defn get
  "Get value from atom store associated with `k` key argument.
  Keyword argument `:default` can be used to set a default value
  returned when `k` is nil. The `:store` keyword specifies an
  alternative atom store from default."
  [k & {:keys [default store] :or {store default-store}}]
  (get-in [k] :default default :store store))

(defn remove!
  "Remove a key from the store map atom. The `k` argument is
  the key to be removed. The optional `:store` keyword argument allows
  setting a new store atom rather than the default `default-store` atom."
  [k & {:keys [store] :or {store default-store}}]
  (swap! store dissoc k))

(defn remove-in!
  "Removes a key from a nested store map. The 'ks' argument is the path
  to the key to be removed. This operation may result in keys in the map
  whose value is an empty map. The `:store` keyword argument can be used to
  specify an alternative store atom."
  [ks & {:keys [store] :or {store default-store}}]
  (when (contains-in? @store ks)
    (if (<= (count ks) 1)
      (remove! (first ks) :store store)
      (let [target (last ks)
            path   (pop ks)]
        (swap! store update-in path dissoc target))))
  @store)

(defn reset!
  "Reset the value of store to the value `m`. The `:store` keyword
  argument can be used to set the target store. Default store is the
  `default-store` atom."
  [m & {:keys [store] :or {store default-store}}]
  (clojure.core/reset! store m))

(defn assoc-in!
  "Set the value `v` in the store at location given by the path
  specified in the keys `ks`. The optional keyword argument `:store`
  can be used to set the target store atom."
  [ks v & {:keys [store] :or {store default-store}}]
  (swap! store assoc-in ks v))

(defn assoc!
  "Set the value at key `k` to `v` in the store atom. The optional
  keyword argument `:store` can be used to set an alternative atom store.
  Default store is `default-store`."
  [k v & {:keys [store] :or {store default-store}}]
  (assoc-in! [k] v :store store))

(defn update-in!
  "Applies the function `f` to the value at `ks` within the map in the store atom and
  stores the result as the new value for `ks`. Optional keyword argument `:args`
  can be used to supply a vector of additional arguments to be passed into the function
  `f`. The keyword :store` can be used to specify an alternative store to act on.
  Default store is `default-store`.`"
  [ks f & {:keys [args store] :or {store default-store}}]
  (if (nil? args)
    (swap! store update-in ks f)
    (swap! store update-in ks #(apply f % args))))

