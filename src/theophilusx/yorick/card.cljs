(ns theophilusx.yorick.card
  (:require [theophilusx.yorick.icon :as icons]
            [theophilusx.yorick.basic :as basic]
            [theophilusx.yorick.utils :refer [cs]]))

(defn card-header
  "Generate a card header from a map of values.
  This is an internal function used by the card component to generate a header
  for the card based on a map of values. The map supports the following keys

  | Key            | Description                                                |
  |----------------|------------------------------------------------------------|
  | `:title`       | The text to use for the header title                       |
  | `:icon`        | An icon definition map. See `theophilusx.yorick.icon`      |
  | `:icon-action` | A function to execute when the icon is clicked             |
  | `:class`       | A string or vector of strings representing CSS class names |"
  [{:keys [title icon icon-action class]}]
  [:header.card-header {:class (cs class)}
   [:p.card-header-title title]
   (when icon
     [basic/a [icons/icon-component icon]
      :class "card-header-icon"
      :on-click (when (fn? icon-action)
                  #(icon-action %))])])

(defn card-footer
  "Generates a card footer from a map of values.
  Internal function used to generate the card footer from a map. The map
  supports the following keys

  | Key        | Description                                                  |
  |------------|--------------------------------------------------------------|
  | `:items`   | A vector of footer items. An item can be a string, hiccup or |
  |            | a component                                                  |
  | `:classes` | A map with keys `:footer` or `:footer-item` that have values |
  |            | which are either a string or a vector of strings representing |
  |            | CSS class names                                               |"
  [{:keys [items classes]}]
  (into
   [:footer.card-footer {:class (cs (:footer classes))}]
   (for [i items]
     [:p.card-footer-item {:class (cs (:footer-item classes))}
      i])))

(defn card
  "A card component which supports a header, footer and a header icon action.
  The card component provides a convenient way to render a defined unit of
  content. It supports both a header and footer section and can include a
  header icon which will perform some action when clicked. The `body` argument
  represents the content of the card.

  The component also accepts optional keyword arguments

  | Key       | Description                                                    |
  |-----------|----------------------------------------------------------------|
  | `:header` | A map representing the card header data. See below for details |
  | `:footer` | A map representing the card footer data. See below for details |
  | `:classes`| A map containing CSS class name strings. See below for details |

  The `:header` map argument defines the data to be used in the card header.
  The following keys are supported

  | Key            | Description                                                |
  |----------------|------------------------------------------------------------|
  | `:title`       | The title string to be placed in the card header           |
  | `:icon`        | An icon data map. See `theophilusx.yorick.icon`            |
  | `:icon-action` | A function to execute when the icon is clicked             |
  | `:class`       | A string or vector of strings representing CSS class names |

  The `:footer` map argument defines the data to be used in the card footer.
  The following keys are supported

  | Key        | Description                                                  |
  |------------|--------------------------------------------------------------|
  | `:items`   | A vector of items to add to the footer. An item can be       |
  |            | a string, hiccup markup or another component                 |
  | `:classes` | a map with keys for `:footer` and `:footer-item`, where the  |
  |            | values are either strings or vectors of strings representing |
  |            | CSS class names to add to the footer or each item in the     |
  |            | footer                                                       |"
  [body & {:keys [header footer classes]}]
  [:div.card {:class (cs (:card classes))}
   (when header
     [card-header header])
   [:div.card-content {:class (cs (:card-content classes))}
    body]
   (when footer
     [card-footer footer])])
