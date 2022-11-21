(ns theophilusx.yorick.sidebar
  "Provides a vertical menu sidebar."
  (:require [theophilusx.yorick.utils :refer [cs spath str->keyword make-value]]
            [theophilusx.yorick.store :as store]
            [theophilusx.yorick.basic :as basic]))

(defn defentry
  "Returns a menu entry definition map.
  The `title` argument specifies the text which will appear as the menu entry.
  The following additional keyword arguments are also supported.

  | Keyword    | Description                                                |
  |------------|------------------------------------------------------------|
  | `:value`   | The value to use when setting the active selection in the  |
  |            | default store. If not supplied, the menu entry `title`,    |
  |            | converted to a keyword is used. If the entry has a nil     |
  |            | `title`, generate  unique value using `gensym`             |                            |
  | `:id`      | An ID value to associate with this entry. If not supplied  |
  |            | use the `:value` value.                                    |
  | `:class`   | A string or vector of strings which specify additional CSS |
  |            | classes to add.                                            |
  | `:icon`    | A `deficon` map used to specify an icon to add to the entry|"
  [title & {:keys [value id class icon] :as opts}]
  (let [v (make-value opts "entry-")]
    {:type  :menu-entry
     :title title
     :value v
     :id    (or id (name v))
     :icon icon
     :class class}))

(defn defmenu
  "Define a sidebar menu.
  The `entries` argument is a vector of menu item maps generated using `defentry`.
  The sidebar menu supports nesting of menus up to 2 deep. The following optional
  keyword arguments are supported.

  | Keyword      | Description                                                  |
  |--------------|--------------------------------------------------------------|
  | `:value`     | Value to be used in the default store to indicate the menu   |
  |              | has been selected.                                           |
  | `:title`     | Add a non-clickable text title to the menu.                  |
  | `:id`        | An ID attribute to associate with this element.              |
  | `:class`     | A string or vector of strings specifying CSS class names     |
  | `:icon`      | An icon data map which defines an icon to add to the parent  |"
  [entries & {:keys [value title id class icon] :as opts}]
  (let [v (make-value opts "entry-")]
    {:type    :menu
     :title   title
     :value   v
     :id      (or id (name v))
     :class   class
     :icon icon
     :entries entries}))

(defn- make-entry
  "Generates a menu entry component.
  `menu-cur` is a cursor pointing to the key in the store which tracks what
  (if any) menu is active. `active-cur` is a cursor pointing to the key in
  the global store where currently selected menu item is tracked. `entry` is
  a menu entry definition map."
  [menu-cur active-cur entry]
  [:li [basic/a (:title entry)
        :class (cs (:class entry)
                   (when (= (:value entry) @active-cur)
                     "is-active"))
        :id (:id entry)
        :icon-data (:icon entry)
        :on-click (fn []
                    (reset! active-cur (:value entry))
                    (reset! menu-cur nil))]])

(defn- make-child-menu
  "Generate a child menu component.
  `menu-cur` is a cursor pointing to the key in key in the default store where
  the currently active menu (if any) is being tracked. `active-cur` is a cursor
  pointing to the key in the default store where the currently active menu
  selection is being tracked."
  [menu-cur active-cur menu]
  [:li
   [basic/a (:title menu)
    :class (cs (:class menu))
    :id (:id menu)
    :icon-data (:icon menu)
    :on-click (fn []
                (if (= @menu-cur (:value menu))
                  (reset! menu-cur nil)
                  (reset! menu-cur (:value menu))))]
   (when (= @menu-cur (:value menu))
     (into [:ul]
           (for [e (:entries menu)]
             [:li 
              [basic/a (:title e)
               :class (cs (:class e)
                          (when (= (:value e) @active-cur)
                            "is-active"))
               :id (:id e)
               :icon-data (:icon e)
               :on-click #(reset! active-cur (:value e))]])))])

(defn- make-menu
  "Generate the main menu component.
  `men-cur' is a cursor into the default store where the current active
  menu (if any) is being tracked. `active-cur` is a cursor into the default
  store which tracks which menu item has been selected. `menu` is a menu
  definition map."
  [menu-cur active-cur menu]
  [:<>
   (when (:title menu)
     [:p.menu-label (:title menu)])
   (into [:ul.menu-list]
         (for [e (:entries menu)]
           (if (= (:type e) :menu)
             [make-child-menu menu-cur active-cur e]
             [make-entry menu-cur active-cur e])))])

(defn sidebar
  "Generates a sidebar vertical menu component.
  `sid` is the storage identifier to use when recording menu and active item selection.
  `menus` is a menu definition map created using `defmenu` and `defentry`. The optional
  keyword argument `:default` can be used to set the initial default active menu."
  [sid _ & {:keys [default]}]
  (store/assoc-in! (spath sid) {:active-menu nil
                                :active-item default})
  (let [menu-cur (store/cursor (conj (spath sid) :active-menu))
        active-cur (store/cursor (conj (spath sid) :active-item))]
    (fn [_ menus & _]
      (into [:aside.menu]
            (for [m menus]
              [make-menu menu-cur active-cur m])))))
