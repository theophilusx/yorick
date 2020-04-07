(ns theophilusx.yorick.sidebar
  (:require [theophilusx.yorick.utils :refer [spath]]
            [theophilusx.yorick.icons :as icons]
            [theophilusx.yorick.store :as store]
            [theophilusx.yorick.basic :as basic]))

(defn is-active? [sid id]
  (if (= (store/get-in store/global-state (spath sid)) id)
    true
    false))

(defn set-active [sid id]
  (store/assoc-in! store/global-state (spath sid) id))

(defn make-item [i sid]
  [:li
   [basic/a [:<>
             (when (:icon-data i)
               [icons/icon-component (:icon-data i)])
             (:title i)]
    :class [(:class i)
            (when (is-active? sid (:id i)) "is-active")]
    :href  (:href i)
    :id    (:id i)
    :on-click (fn []
                (set-active sid (:id i)))]])

(defn make-menu [m sid]
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
  the following keys -
  `:type` Type of the item. Either :item for a simple link item or :menu for
          a nested sub-menu item. Defaults to `:item`.
  `:title` The title for sub-menus.
  `:href` The href for the link. Defaults to `#`.
  `:id` An id attribute for the item. If not provided, defaults to a unique
         value with the prefix `side-bar-`.
  `:icon-data` An icon data `map` (see `theophilusx.yorick.icons`)
  `:class` Any additional class attributes to add to the element.
  `:items` For items of type `:menu`, this key is a vector of `defsidebar-item`
           maps representing the sub-menu items."
  [& {:keys [type title href icon-data id items class]
      :or {type :item
           href "#"
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
  `:sessin-id` A keyword representing a path into the global state atom e.g.
               :place. If keyword has a `.` it is interpreted as a delimiter for
               another path level e.g. :place.sub-place = [:place :sub-place].
  `:default-link` The default menu id to be set as active when sidebar is first
                   loaded
  `:item` A `defsidebar-item` map defining the parent menu with sub-menus as a
          vector of `defsidebar-tiem` items in the `:items` key"
  [data]
  (store/assoc-in! store/global-state (spath (:sid data)) (:default-link data))
  (fn [data]
    [:nav.menu {:class (:class (:item data))}
     [make-menu (:item data) (:sid data)]]))
