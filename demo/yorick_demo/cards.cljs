(ns yorick-demo.cards
  (:require [theophilusx.yorick.card :as c]
            [theophilusx.yorick.basic :as b]))

(defn simple-card []
  [:div.columns
   [:div.column.is-half
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
     [c/card "This is a very simple card"]]
    [:pre
     [:code
      "[card/card [:p \"This is a card created from \"] [:strong \"hiccup\"] \" markup\"]"]]
    [:div.box
     [:h6.title.is-6 "result"]
     [c/card [:p "This is a card created from " [:strong "hiccup"] " markup"]]]
    [:pre
     [:code
      "[card/card [:<>" [:br]
      "            [basic/notificaiton [:<>" [:br]
      "                                  [:h2 \"A Note\"]" [:br]
      "                                  [:p \"Some random text\"]]]" [:br]
      "            [basic/breadcrums :demo.breadcrums.value" [:br]
      "              [{:name \"Page 1\"" [:br]
      "                :value :page1}" [:br]
      "               {:name \"Page 2\"" [:br]
      "                :value :page2}" [:br]
      "               {:name \"Page 3\"" [:br]
      "                :value :page3" [:br]
      "                :active true}]]]]"]]
    [:div.box
     [:h6.title.is-6 "Result"]
     [c/card [:<>
              [b/notification [:<>
                                   [:h2 "A Note"]
                                   [:p "Some random text"]]]
              [b/breadcrumbs :demo.breadcrumbs.value
               [{:name "Page 1"
                 :value :page1}
                {:name "Page 2"
                 :value :page2}
                {:name "Page 3"
                 :value :page3
                 :active true}]]]]]]])

(defn card-with-header []
  [:div.columns
   [:div.column.is-half
    [:p
     "A " [:strong "card"] " can have a header, which is displayed to stand "
     "out from the card body."]
    [:p
     "A " [:strong "card"] " " [:em "header"] " can also have an associated "
     "icon. The icon can have an action function associated with it. "
     "Clicking on the icon will cause the action function to be called. "
     "The action function could be used to add or remove classes from the card, "
     "card header, card content or card footer or execute ClojureScript code "
     "that could perform almost any action required."]]
   [:div.column
    [:pre
     [:code
      "[card/card [:p \"This is the card body\"]" [:br]
      "  :header {:title \"Card Header\"}]"]]
    [:div.box
     [:h6.title.is-6 "Result"]
     [c/card [:p "This is the card body"]
      :header {:title "Card Header"}]]]])

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
    [:div.column.is-half]
    [:div.column
     [:h4.title.is-4 "Examples"]]]
   [simple-card]
   [card-with-header]])

