(ns yorick-demo.basic
  (:require [theophilusx.yorick.basic :as b]
            [theophilusx.yorick.card :as c]))

(defn a-component []
  [:div.columns
   [:div.column
    [c/card [:<>
             [:p "The " [:strong "a"] " component generates a basic HTML <a> "
              "element. The component expects to be called with one argument "
              [:em "title"] " which is the text to be used in the link. "
              "The component also accepts a number of keyword arguments."]
             [:ul
              [:li [:strong ":href"] " a hypertext link that will be the target "
               "of the link. If not supplied, defaults to " [:em "#"]]
              [:li [:strong ":on-click"] " a function to execute when the link "
               "is clicked on"]
              [:li [:strong ":class"] " a string or vector of strings representing "
               "CSS class names to apply to the <a> element"]
              [:li [:strong ":id"] " an ID attribute value"]
              [:li [:strong ":role"] " a role attribute value"]
              [:li [:strong ":data-target"] " a data-target attribute value"]
              [:li [:strong ":aria-label"] " an aria-label attribute value"]
              [:li [:strong ":aria-expanded"] " true if aria-expanded attribute "
               "is to be added"]]]
     :header [c/card-header "a - An HTML anchor component"]]]
   [:div.column
    [:div.content
     [:p "[:p \"This is an example of an \" [basic/a \"anchor\" :on-click #(js/alert \"Hello\")]"]
     [:div.box
      [:p "This is an example of an "
       [b/a "anchor" :on-click #(js/alert "Hello")]]]]]])

(defn img-component []
  [:div.columns
   [:div.column
    [c/card [:<>
             [:p "The " [:strong "img"] " component generates an HTML <img> "
              "element. The component expects an argument which specifies "
              "the local or remote path i.e. the <img> src attribute. Optional "
              "supported keyword arguments include "]
             [:ul
              [:li [:strong ":width"] " a width attribute value"]
              [:li [:strong ":class"] " a string or vector of strings representing "
               "CSS class names"]
              [:li [:strong ":id"] " an id attribute value"]]]
     :header [c/card-header "img - An HTML image component"]]]
   [:div.column
    [:p "[basic/img \"images/bulma-logo.png\" :width 400]"]
    [:div.box
     [:p [b/img "images/bulma-logo.png" :width 400]]]]])

(defn render-vec-component []
  [:div.columns
   [:div.column
    [c/card [:p "The " [:strong "render-vec"] " component is a basic component "
             "which will render a ClojureScript vector as an unordered list. "
             "It takes only one argument, the vector to render."]
     :header [c/card-header "render-vec - Render a ClojureScript vector"]]]
   [:div.column
    [:p "[:p \"This is a ClojureScript vector \" [basic/render-vec [:a :b :c]]]"]
    [:div.box
     [:p "This is a ClojureScript vector " [b/render-vec [:a :b :c]]]]]])

(defn render-set-component []
  [:div.columns
   [:div.column
    [c/card [:p "The " [:strong "render-set"] " component renders a ClojureScript "
             "set as a comma delimited list surrounded in parenthesis. It accepts "
             "one argument, the set to render."]
     :header [c/card-header "render-set - Render a ClojureScript set"]]]
   [:div.column
    [:p "[:p \"This is a ClojureScript set \" [basic/render-set #{1 2 3}]]"]
    [:div.box
     [:p "This is a ClojureScript set " [b/render-set #{1 2 3}]]]]])

(defn basic-page []
  [:<>
   [:h2.title.is-2 "Basic Components"]
   [:p
    "The " [:strong "theophilusx.yorick.basic"] " namespace contains basic "
    "components that are very simple. They are mainly components related to "
    "text formatting, simple HTML entities or basic rendering of ClojureScript "
    "data structures. Essentially, if I found typing the raw hiccup version of "
    "an HTML element was common enough or tedious enough, I created a simple "
    "component to do the work."]
   [:p "The data structure rendering components are mainly used during "
    "development and debugging of an application. In particular, when first "
    "developing an application, I find it useful to dump the global state at "
    "the end of the page. This can be useful when you want to verify state "
    "values are what you expect or for debugging problems in state values"]
   [:hr]
   [:div.columns
    [:div.column
     [:h4.title.is-4 "Description"]]
    [:div.column
     [:h4.title.is-4 "Example"]]]
   [a-component]
   [img-component]
   [render-vec-component]
   [render-set-component]])
