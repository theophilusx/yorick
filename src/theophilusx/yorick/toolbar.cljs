(ns theophilusx.yorick.toolbar)

(defn deftoolbar-item
  "Defines a toolbar item. A toolbar item definition is a map with the
  following keys -
  `:type` The type of the item. This is used to determine what (if any) type
          of element is used to wrap the item. The value should be a valid
          hiccup tag. Defaults to `:div`
  `:class` Any additional class attributes to add to the wrapping element
  `:content` This can be pretty much any valid hiccup or reagent component"
  [& {:keys [type class content]
      :or {type :div}}]
  {:type type
   :class class
   :content content})

(defn toolbar
  "Defines a basic horizontal toolbar. Expects a map with the following keys
  `:left-items` A vector of toolbar items defined using `deftoolbar-item`
  `:right-items` (Optional) A vector of toolbar items defined using
  `deftoolbar-item` which are placed to the right of other items
  `:class` (Optional) Additional classes to be added to the outer `nav` element"
  [data]
  [:nav.level {:class (:class data)}
   (into
    [:div.level-left]
    (for [i (:left-items data)]
      [(:type i) {:class ["level-item" (:class i)]}
       (:content i)]))
   (when (:right-items data)
     (into
      [:div.level-right]
      (for [i (:right-items data)]
        [(:type i) {:class ["level-item" (:class i)]}
         (:content i)])))])
