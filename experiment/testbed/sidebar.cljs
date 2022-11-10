(ns testbed.sidebar
  "Provides a vertical menu sidebar."
  (:require [theophilusx.yorick.utils :refer [cs spath str->keyword]]
            [theophilusx.yorick.store :as store]
            [theophilusx.yorick.basic :as basic]))

(defn defentry [title & {:keys [value id class icon]}]
  (let [v (or value
              (str->keyword (or title
                                (gensym "entry-"))))]
    {:type  :menu-entry
     :title title
     :value v
     :id    (or id (name v))
     :icon icon
     :class class}))

(defn defmenu [entries & {:keys [value title id class icon]}]
  (let [v (or value
              (str->keyword
               (or title
                   (gensym "entry-"))))]
    {:type    :menu
     :title   title
     :value   v
     :id      (or id (name v))
     :class   class
     :icon icon
     :entries entries}))

(defn- make-entry [menu-cur active-cur entry]
  [:li [basic/a (:title entry)
        :class (cs (:class entry)
                   (when (= (:value entry) @active-cur)
                     "is-active"))
        :id (:id entry)
        :icon-data (:icon entry)
        :on-click (fn []
                    (reset! active-cur (:value entry))
                    (reset! menu-cur nil))]])

(defn- make-child-menu [menu-cur active-cur menu]
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

(defn- make-menu [menu-cur active-cur menu]
  [:<>
   (when (:title menu)
     [:p.menu-label (:title menu)])
   (into [:ul.menu-list]
         (for [e (:entries menu)]
           (if (= (:type e) :menu)
             [make-child-menu menu-cur active-cur e]
             [make-entry menu-cur active-cur e])))])

(defn sidebar [sid _ & {:keys [default]}]
  (store/assoc-in! (spath sid) {:active-menu nil
                                :active-item default})
  (let [menu-cur (store/cursor (conj (spath sid) :active-menu))
        active-cur (store/cursor (conj (spath sid) :active-item))]
    (fn [_ menus & _]
      (into [:aside.menu]
            (for [m menus]
              [make-menu menu-cur active-cur m])))))


