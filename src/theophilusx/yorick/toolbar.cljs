(ns theophilusx.yorick.toolbar
  (:require [theophilusx.yorick.utils :refer [cs]]))

(defn deftoolbar-item
  "Defines a toolbar item. A toolbar item definition is a map with the
  following keys -

  | Keyword    | Description                                                   |
  |------------|---------------------------------------------------------------|
  | `:type`    | The type of the item. This is used to determine what (if any) |
  |            | type of element is used to wrap the item. The value should    |
  |            | be a valid hiccup tag. Defaults to `:div`                     |
  | `:class`   | Any additional class attributes to add to the wrapping element|
  | `:content` | This can be any valid hiccup or reagent component             |"
  [& {:keys [type class content]
      :or {type :div}}]
  {:type type
   :class class
   :content content})

(defn toolbar
  "Defines a basic horizontal toolbar. Expects a map with the following keys:

  | Key            | Description                                               |
  |----------------|-----------------------------------------------------------|
  | `:left-items`  | a vector of toolbar items defined using `deftoolbar-item` |
  |                | Items will be placed in the toolbar from the left side    |
  | `:right-items` | a vector of toolbar items defined using                   |
  |                | `deftoolbar-item` which are placed to the right of        |
  |                | other items                                               |
  | `:class`       | a string or vector of string specifying CSS class names   |"
  [data]
  [:nav.level {:class (cs (:class data))}
   (into
    [:div.level-left]
    (for [i (:left-items data)]
      [(:type i) {:class (cs "level-item"
                             (:class i))}
       (:content i)]))
   (when (:right-items data)
     (into
      [:div.level-right]
      (for [i (:right-items data)]
        [(:type i) {:class (cs "level-item"
                               (:class i))}
         (:content i)])))])
