(ns theophilusx.yorick.sidebar
  (:require [theophilusx.yorick.utils :refer [cs spath]]
            [theophilusx.yorick.icon :as icons]
            [theophilusx.yorick.store :as store]
            [theophilusx.yorick.basic :as basic]))

(defn is-active?
  "Return true if the `id` argument is equal to the value stored at the location 
  specified by the `sid` storage identifier keyword in the global state atom
  defined in `theophilusx.yorick.store/global-state."
  [sid id]
  (if (= (store/get-in store/global-state (spath sid)) id)
    true
    false))

(defn set-active
  "Set the value of the key specified by the `sid` storage identifier keyword
  in the global state atom defined by `theophilusx.yorick.store/global-state`
  to the value `id`."
  [sid id]
  (store/assoc-in! store/global-state (spath sid) id))

(defn make-item
  "Create a sidebar menu item. The `i` argument is an item map. See the function
  `defsidebar-item` for a description of the supported map keys. The `sid`
  storage identifier keyword specifies where the `id` associated with this item
  will be stored in the global state atom defined in
  `theophilusx.yorick.store/global-state`"
  [i sid]
  [:li
   [basic/a [:<>
             (when (:icon-data i)
               [icons/icon-component (:icon-data i)])
             (:title i)]
    :class (cs (:class i)
               (when (is-active? sid (:id i)) "is-active"))
    :href  (:href i)
    :attrs {:id (:id i)}
    :on-click (fn []
                (set-active sid (:id i)))]])

(defn make-menu
  "Generate a sidebar menu or a nested menu within the sidebar. The `m` argument
  is an item map. See `defsidebar-item` function for a description of available
  keys and their meaning. The `sid` storage identifier keyword determines where
  the selected menu item `id` is stored within the global state atom defined in
  `theophilusx.yorick.store/global-state`."
  [m sid]
  [:aside.menu
   [:h3.is-3.menu-label (:title m)]
   (into
    [:ul.menu-list]
    (for [i (:items m)]
      (if (= (:type i) :item)
        [make-item i sid]
        [make-menu i sid])))])

(defn defsidebar-item
  "Define an item for the sidebar. The item can either be a simple link item
  or it can be a sub-menu item. Items are defined by a `map` which can have
  the following keys:

  | Key          | Description                                                |
  |--------------|------------------------------------------------------------|
  | `:type`      | Type of the item. Either `:item` for a simple link item or |
  |              | `:menu` for a nested sub-menu item. Defaults to `:item`.   |
  | `:title`     | The title for sub-menus.                                   |
  | `:href`      | The href for the link.                                     |
  | `:id`        | An id attribute for the item. If not provided, defaults to |
  |              | a unique value with the prefix `side-bar-`.                |
  | `:icon-data` | An icon data `map` (see `theophilusx.yorick.icons`)        |
  | `:class`     | a string or vector of strings specifying CSS class names.  |
  | `:items`     | For items of type `:menu`, this key is a vector of         |
  |              | `defsidebar-item` maps representing the sub-menu items.    |"
  [& {:keys [type title href icon-data id items class]
      :or {type :item
           id (keyword (gensym "side-bar-"))}}]
  {:type type
   :title title
   :href href
   :icon-data icon-data
   :id id
   :items items
   :class class})

(defn sidebar
  "Defines a sidebar menu. A sidebar is defined by a `map` with the keys

  | Key             | Description                                              |
  |-----------------|----------------------------------------------------------|
  | `:sid`          | a storage identifier keyword used to determine the key   |
  |                 | within the global state in                               |
  |                 | `theophilusx.yorick.store/global-state` where the        |
  |                 | current menu `id` should be stored.                      |
  | `:default-link` | The default menu `id` to be set as active when the       |
  |                 | sidebar is first loaded                                  |
  | `:data`         | A `defsidebar-item` map defining the parent menu with    |
  |                 | the child menus defined as a vector of `defsidebar-tiem` |
  |                 | items in the `:items` key.                               |"
  [data]
  (store/assoc-in! store/global-state (spath (:sid data)) (:default-link data))
  (fn [data]
    [:nav.menu {:class (cs (:class (:item data)))}
     [make-menu (:data data) (:sid data)]]))
