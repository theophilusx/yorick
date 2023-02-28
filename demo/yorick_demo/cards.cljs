(ns yorick-demo.cards
  (:require [theophilusx.yorick.card :as c]
            [theophilusx.yorick.basic :as b]
            [theophilusx.yorick.icon :as icon]
            [yorick-demo.comp :refer [doc-args doc-fn doc-ns]]))

(def card-title "card - a flexible card component")

(def header-keys
  [[":title" "A header title"]
   [":icon" "An icon data map. See deficon for details. Adds an icon to the header"]
   [":icon-action" "An actohn function to call when an icon is clicked"]
   [":class" "A string or vector of scrings specifying CSS classes to add to the header"]])

(def footer-keys
  [[":items" "A vector of items to be added to the footer. Items can be strings, hiccup markup or components"]
   [":classes" "A map of strings or vectors of strings specifyinig CSS classes. Supported keys are :footer and :footer-items"]])

(def card-desc
  [:<>
   [:p "The " [:strong "card"] " component is a flexible general purpose 
       presentation component. It can handle almost any type of content and 
       can be very simple."]
   [:p "A " [:strong "card"] " can have a header, which is displayed to stand 
       out from the card body."]
   [:p "A " [:strong "card"] " " [:em "header"] " can also have an associated 
       icon. The icon can have an action function associated with it. 
       Clicking on the icon will cause the action function to be called. 
       The action function could be used to add or remove classes from the card, 
       card header, card content or card footer or execute ClojureScript code 
       that could perform almost any action required. The supported keys for the
       header are"]
   [doc-args header-keys]
   [:p "A " [:strong "card"] " can also have a footer. The footer data is 
       added via the " [:code ":footer"] " keyword argument. The argument 
       value is a map which contains keys for " [:code "items"] " and "
    [:code ":classes"] ". The :items key contains a vector of items to add to 
       the footer. Items can be strings, hiccup markup or components. The 
       :classes argument is a map which can contains the keys " [:code ":footer"]
    " and " [:code ":footer-item"] ". The :footer key value is either a string 
        or a vector of strings representing CSS class names to be added to the 
       footer element. The :footer-item key is a string or vector of strings 
       representing CSS class names to be added to " [:em "each"] " item in the footer."]
   [doc-args footer-keys]
   [:p "A " [:strong "card"] " can also be styled by providing CSS class 
       names. To style the card or card content, use the " [:code ":classes"]
    " keyword argument. The value is a map with two possible keys "
    [:code ":card"] " and " [:code ":card-content"] ". Classes specified in the "
    [:code ":card"] " key of the map will be applied to the outer card element. 
       Classes specified in the " [:code ":class-content"] " will be applied to 
       the contents of the card. To add CSS classes to the header, use the " [:code ":class"]
    " key in the " [:code ":header"] " keyword map. To add CSS classes to the 
       footer, use the " [:code ":classes"] " key in the " [:code ":footer"] " keyword 
       map. The value of the " [:code ":classes"] " key in the " [:code ":footer"]
    " map is a map with keys for " [:code ":footer"] " and " [:code "footer-item"]]
   [:p "The value for each of the class related keywords are either a string "
    "specifying a CSS class or a space separated list of CSS classes or a "
    "vector of strings where each element resolves to a string representing "
    "a CSS class name or a space separated list of CSS class names."]])

(def card-args [["body" "The content to place in the card"]])

(def card-opt-args
  [[":header" "A map of data to be used for the header component of the card"]
   [":footer" "A map of data to be used as the footer component of the card"]
   [":classes" "A map containing strings or vectors of strings specifying CSS classes to add"]])

(def card-examples
  [{:text "[card/card \"This is a very simple card\"]"
    :code (fn []
            [c/card "This is a very simple card"])}
   {:text  "[card/card [:p \"This is a card created from \"
                             [:strong \"hiccup\"] \" markup\"]"
    :code (fn []
            [c/card [:p "This is a card created from " [:strong "hiccup"] " markup"]])}
   {:text "
[card/card [:<>
            [basic/notificaiton [:<>
                                 [:h2 \"A Note\"]
                                 [:p \"Some random text\"]]]
            [basic/breadcrums :demo.breadcrums.value
             [{:name \"Page 1\"
               :value :page1}
              {:name \"Page 2\"
               :value :page2}
              {:name \"Page 3\"
               :value :page3
               :active true}]]]]"
    :code (fn []
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
                        :active true}]]]])}
   {:text "[card/card [:p \"This is the card body\"]
           :header {:title \"Card Header\"}]"
    :code (fn []
            [c/card [:p "This is the card body"]
             :header {:title "Card Header"}])}
   {:text "[card/card [:p \"This is the card body\"]
            :header {:title \"Card Header w/ Icon\"
            :icon (icon/deficon \"fa-info\")
            :icon-action #(js/alert \"Hello\")}]"
    :code (fn []
            [c/card [:p "This is the card body"]
             :header {:title "Card Header"
                      :icon (icon/deficon "fa-info")
                      :icon-action #(js/alert "Hello")}])}
   {:text  "[card/card [:p \"This is the card body\"]
             :header {:title \"Card Header\"}
             :footer {:items [:p \"Footer item 1\"]
                             [:p \"Footer item 2\"]}]"
    :code (fn []
            [c/card [:p "The is the card body"]
             :header {:title "Card Header"}
             :footer {:items ["Footer Item 1"
                              "Footer Item 2"
                              "Footer Item 3"]}])}
   {:text  "[card/card [:p \"This is the body of the card\"]
             :header {:title \"Card Header\"
                      :class \"has-background-primary\"}
             :footer {:items [\"Footer item\"]
                      :classes {:footer \"has-background-info}}
             :classses {:card-content \"has-text-danger\"}]"
    :code (fn []
            [c/card [:p "This is the body of the card"]
             :header {:title "Card Header"
                      :class "has-background-primary"}
             :footer {:items ["Footer item"]
                      :classes {:footer "has-background-info"}}
             :classes {:card-content "has-text-danger"}])}])

;; (def card-examples
;;   [{:text "[card/card \"This is a very simple card\"]"
;;     :code (fn []
;;             [c/card "This is a very simple card"])}])

(defn card-page []
  [doc-fn {:title card-title
           :desc card-desc
           :args card-args
           :opt-args card-opt-args
           :examples card-examples}])

;; NS

(def ns-sid :ui.sidebar.cord-ns)

(def ns-title  "The Card Component")

(def ns-desc
  [:<>
   [:p "The " [:strong "theophilusx.yorick.card"] " namespace provides a single
        component, the " [:strong "card"] " component provides a flexible 
        and composable component for presenting almost any type of content. The 
        card consists of 3 parts, a header, a footer and the body. Only the body 
        is required, header and footer are optional."]
   [:p "The body contents of a card can be almost anything - a string, hiccup 
        markup, another component or a combination. The " [:em "body"] " argument 
        needs to be in a form suitable as the contents of a Reagent/React 
        component i.e. it cannot be a nested vector or list. If you want to pass
        in multiple components or vectors of hiccup markup, you need to either 
        wrap the argument in a " [:em "div"] " vector or use the " [:em "[:<>]"]
       "shorthand wrapper."]])

(def ns-pages [{:title "card"
                :value :card
                :page card-page}])

(defn ns-page []
  [doc-ns ns-sid ns-title ns-desc ns-pages])



