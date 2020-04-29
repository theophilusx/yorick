(ns yorick-demo.cards
  (:require [theophilusx.yorick.card :as c]
            [theophilusx.yorick.basic :as b]
            [theophilusx.yorick.icon :as icon]))

(defn simple-card []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:<> 
      [:p
       "The " [:strong "card"] " component is a flexible general purpose "
       "presentation component. It can handle almost any type of content and "
       "can be very simple."]]]]
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
    [c/card 
     [:<> 
      [:p
       "A " [:strong "card"] " can have a header, which is displayed to stand "
       "out from the card body."]
      [:p
       "A " [:strong "card"] " " [:em "header"] " can also have an associated "
       "icon. The icon can have an action function associated with it. "
       "Clicking on the icon will cause the action function to be called. "
       "The action function could be used to add or remove classes from the card, "
       "card header, card content or card footer or execute ClojureScript code "
       "that could perform almost any action required."]]]]
   [:div.column
    [:pre
     [:code
      "[card/card [:p \"This is the card body\"]" [:br]
      "  :header {:title \"Card Header\"}]"]]
    [:div.box
     [:h6.title.is-6 "Result"]
     [c/card [:p "This is the card body"]
      :header {:title "Card Header"}]]
    [:pre
     [:code
      "[card/card [:p \"This is the card body\"]" [:br]
      "  :header {:title \"Card Header w/ Icon\"" [:br]
      "           :icon (icon/deficon \"fa-info\")" [:br]
      "           :icon-action #(js/alert \"Hello\")}]"]]
    [:div.box
     [:h6.title.is-6 "Result"]
     [c/card [:p "This is the card body"]
      :header {:title "Card Header"
               :icon (icon/deficon "fa-info")
               :icon-action #(js/alert "Hello")}]]]])

(defn card-with-footer []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:p
      "A " [:strong "card"] " can also have a footer. The footer data is "
      "added via the " [:em ":footer"] " keyword argument. The argument "
      "value is a map which contains keys for " [:em "items"] " and "
      [:em ":classes"] ". The :items key contains a vector of items to add to "
      "the footer. Items can be strings, hiccup markup or components. The "
      ":classes argument is a map which can contains the keys " [:em ":footer"]
      " and " [:em ":footer-item"] ". The :footer key value is either a string "
      " or a vector of strings representing CSS class names to be added to the "
      "footer element. The :footer-item key is a string or vector of strings "
      "representing CSS class names to be added to " [:em "each"] " item in "
      "the footer."]]]
   [:div.column
    [:pre
     [:code
      "[card/card [:p \"This is the card body\"]" [:br]
      "  :header {:title \"Card Header\"}" [:br]
      "  :footer {:items [\"Footer item 1\"" [:br]
      "                   [:p \"Footer item 2\"]}]"]]
    [:div.box
     [:h6.title.is-6 "Result"]
     [c/card [:p "The is the card body"]
      :header {:title "Card Header"}
      :footer {:items ["Footer Item 1"
                       "Footer Item 2"
                       "Footer Item 3"]}]]]])

(defn card-with-class []
  [:div.columns
   [:div.column
    [c/card
     [:<>
      [:p "A " [:strong "card"] " can also be styled by providing CSS class "
       "names. To style the card or card content, use the " [:em ":classes"]
       " keyword argument. The value is a map with two possible keys "
       [:em ":card"] " and " [:em ":card-content"] ". Classes specified in the "
       [:em ":card"] " key of the map will be applied to the outer card element. "
       "Classes specified in the " [:em ":class-content"] " will be applied to "
       "the contents of the card. "
       "To add CSS classes to the header, use the " [:em ":class"]
       " key in the " [:em ":header"] " keyword map. To add CSS classes to the "
       "footer, use the " [:em ":classes"] " key in the " [:em ":footer"] " keyword "
       "map. The value of the " [:em ":classes"] " key in the " [:em ":footer"]
       " map is a map with keys for " [:em ":footer"] " and " [:em "footer-item"]]
      [:p "The value for each of the class related keywords are either a string "
       "specifying a CSS class or a space separated list of CSS classes or a "
       "vector of strings where each element resolves to a string representing "
       "a CSS class name or a space separated list of CSS class names."]]]]
   [:div.column
    [:pre
     [:code
      "[card/card [:p \"This is the body of the card\"]" [:br]
      "  :header {:title \"Card Header\"" [:br]
      "           :class \"has-background-primary\"}" [:br]
      "  :footer {:items [\"Footer item\"]" [:br]
      "           :classes {:footer \"has-background-info}}" [:br]
      "  :classses {:card-content }\"has-text-danger\"}]"]]
    [:div.box
     [:h6.title.is-6
      [c/card [:p "This is the body of the card"]
       :header {:title "Card Header"
                :class "has-background-primary"}
       :footer {:items ["Footer item"]
                :classes {:footer "has-background-info"}}
       :classes {:card-content "has-text-danger"}]]]]])

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
   [:ul.list
    [:li [:strong ":title"] " Text of the header title"]
    [:li [:strong ":icon"] " An icon definition map. See "
     [:strong "theophilusx.yorick.icon"] " for details on the map format"]
    [:li [:strong ":icon-action"] " An optional function to execute if the "
     "user clicks on the icon"]
    [:li [:strong ":class"] " A string or vector of strings representing "
     "CSS class names to add to the header"]]
   [:hr]
   [:div.columns
    [:div.column.is-half]
    [:div.column
     [:h4.title.is-4 "Examples"]]]
   [simple-card]
   [card-with-header]
   [card-with-footer]
   [card-with-class]])

