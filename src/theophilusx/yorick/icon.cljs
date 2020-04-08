(ns theophilusx.yorick.icon
  (:require [clojure.string :as string]))

(defn deficon
  "Create an icon data map which defines the data used to generate an icon
  component. Arguments are
  `name` - name of the font awesome icon
  `position` - position of the icon (:left or :right)
  `size` - size as keyword. Supported are `:small`, `:medium` and `:large`
  `icon-class` - additional classes to add tot he `i` element
  `span-class` - additional classes to add to the enclosing `span` element."
  [name & {:keys [position span-class icon-class size]}]
  {:name name
   :position position
   :size size
   :icon-class icon-class
   :span-class span-class})

(defn icon-component
  "Generate an icon component from `icon-data` map. The `icon-data` map is a
  `map` created by calling `deficon`. "
  [icon-data]
  [:span.icon {:class [(:span-class icon-data)
                       (case (:position icon-data)
                         :left "is-left"
                         :right "is-right"
                         nil)]}
   [:i.fa {:class [(:name icon-data)
                   (:icon-class icon-data)
                   (case (:size icon-data)
                     :small "is-small"
                     :medium "is-medium"
                     :large "is-large"
                     nil)]}]])

(defn icon
  "Generates a `vector` of `icon` components from icon data. The `icon-data`
  can be either a `map` or a `vector` of icon data `maps`"
  [icon-data]
  (if (map? icon-data)
    [[icon-component icon-data]]
    (vec (for [i icon-data]
           [icon-component i]))))

(defn icon-control-class [icon-data]
  (if (map? icon-data)
    (case (:position icon-data)
      :left "has-icons-left"
      :right "has-icons-right"
      "")
    (string/join (map (fn [i]
                        (case (:position i)
                          :left "has-icon-left"
                          :right "has-icon-right"
                          ""))
                      icon-data) " ")))
