(ns yorick-demo.cards
  (:require [theophilusx.yorick.card :as c]))

(defn simple-card []
  [:div.columns
   [:div.column
    [:p
     "The " [:strong "card"] " component is a flexible general purpose "
     "presentation component. It can handle almost any type of content and "
     "can be very simple."]]
   [:div.column
    [:pre
     [:code
      "[card/card \"This is a very simple card\"]"]]
    [:div.box
     [:h6.title.is-6 "Result"]
     [c/card "This is a very simple card"]]]])

(defn card-page []
  [:<>
   [:h2.title.is-2 "The Card Component"]
   [:p
    "The " [:strong "theophilusx.yorick.card/card"] " component provides a flexible "
    "and composable component for presenting almost any type of content. The "
    "card consists of 3 parts, a header, a footer and the body. Only the body "
    "is required, header and footer are optional."]
   [:p
    "The body contents of a card can be almost anything - a string, hiccup "
    "markup, another component or a combination. The " [:em "body"] " argument "
    "needs to be in a form suitable as the contents of a Reagent/React "
    "component i.e. it cannot be a nested vector or list. If you want to pass "
    "in multiple components or vectors of hiccup markup, you need to either "
    "wrap the argument in a " [:em "div"] " vector or use the " [:em "[:<>]"]
    "shorthand wrapper."]
   [:p
    "The optional " [:em ":header"] " argument expects a map value. Supported "
    "keys for the map are " [:em ":title, :icon, :icon-action"] " and "
    [:em ":class"] "."]
   [:p [:ul
        [:li [:strong ":title"] " Text of the header title"]
        [:li [:strong ":icon"] " An icon definition map. See "
         [:strong "theophilusx.yorick.icon"] " for details on the map format"]
        [:li [:strong ":icon-action"] " An optional function to execute if the "
         "user clicks on the icon"]
        [:li [:strong ":class"] " A string or vector of strings representing "
         "CSS class names to add to the header"]]]
   [:hr]
   [:div.columns
    [:div.column]
    [:div.column
     [:h4.title.is-4 "Examples"]]]
   [simple-card]])

