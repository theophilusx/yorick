(ns theophilusx.yorick.basic)

(defn box
  "A simple box component. `contents` is wrapped in a div with class `box`"
  [body & {:keys [class]
           :or {class ""}}]
  (let [cls (str "box " class)]
    [:div {:class cls} body]))

(defn card
  "A basic card component. Can have optional header and footer elements"
  [body & {:keys {header-title header-icon card-image }}])
