(ns theophilusx.yorick.icon
  "Provides functions and components useful when adding font awesome icons."
  (:require [clojure.string :as string]
            [theophilusx.yorick.utils :refer [cs]]))

(defn deficon
  "Create an icon data map which defines the data used to generate an icon
  component. The `name` argument is the name of a font awesome icon. Supported
  optional keyword arguments are

  | Key           | Description                                             |
  |---------------|---------------------------------------------------------|
  | `:position`   | Position of the icon. Can be either `:left` or `:right` |
  | `:size`       | Size of the icon. Can be one of `:small`, `:medium` or  |
  |               | `:large`                                                |
  | `:icon-class` | Additional CSS classes to add to the `<i>` element      |
  | `:span-class` | Additional CSS classes to add to the enclosing `<span>` |
  |               | element                                                 | "
  [name & {:keys [position span-class icon-class size]}]
  {:name name
   :position position
   :size size
   :icon-class icon-class
   :span-class span-class})

(defn icon
  "Generate an icon component from `icon-data` map. The `icon-data` map is a
  `map` with keys for `:name`, `:position`, `:size`, `:icon-class` and
  `:span-class`. Only `:name` is required. See the function `deficon` for a
  convenience function to generate the map. "
  [icon-data]
  [:span.icon {:class (cs (:span-class icon-data)
                          (case (:position icon-data)
                            :left "is-left"
                            :right "is-right"
                            nil)
                          (case (:size icon-data)
                            :small "is-small mx-2"
                            :medium "is-medium mx-2"
                            :large "is-large mx-2"
                            :huge "is-large mx-3"
                            "mx-1"))}
   [:i {:class (cs (:name icon-data)
                   (:icon-class icon-data)
                   (case (:size icon-data)
                     :medium "fa-lg"
                     :large "fa-2x"
                     :huge "fa-3x"
                     nil))}]])

(defn icons
  "Generates a `vector` of `icon` components from icon data. The `icon-data`
  can be either a `map` or a `vector` of icon data `maps`"
  [icon-data]
  (if (map? icon-data)
    [[icon icon-data]]
    (vec (for [i icon-data]
           [icon i]))))

(defn icon-control-class
  "This function is used to extract the icon position classes from a vector
  of icon data maps. When adding icons to some elements, it is necessary to
  add the icon position classes to the parent element that wraps around the
  icons. This function provides that convenience. The `icon-data` can be either
  an icon data `map` or a `vector` of icon data `maps`. "
  [icon-data]
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
