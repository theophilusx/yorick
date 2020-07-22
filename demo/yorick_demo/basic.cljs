(ns yorick-demo.basic
  (:require [theophilusx.yorick.basic :as b]
            [theophilusx.yorick.card :as c]
            [theophilusx.yorick.icon :as icon]
            [theophilusx.yorick.tab :as t]
            [theophilusx.yorick.store :refer [get-in global-state]]
            [theophilusx.yorick.utils :refer [spath]]))

(defn a-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
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
       [:li [:strong ":attrs"] " - a map of HTML attribute values. The keys are "
        "HTML attribute names as keywords e.g. " [:code ":id"]]
       [:li [:strong ":icon-data"] " - an icon definition map. Adds an icon to "
        "the anchor text. See " [:code "theophilusx.yorick.icon"] " for details "
        "on the icon definition map format"]]]
     :header {:title "a - An HTML anchor component"}]]
   [:div.column
    [:pre
     [:code
      "[:<>" [:br]
      "  [:p \"This is an example of an \" " [:br]
      "      [basic/a \"anchor\" :on-click #(js/alert \"Hello\")]"
      "  [:p \"A link with an icon \"" [:br]
      "     [basic/a \"Home\" :icon-data (icon/deficon \"fa-house\")]]"]
     ]
    [:div.box
     [:h6.title.is-6 "Result"]
     [:p "This is an example of an "
      [b/a "anchor" :on-click #(js/alert "Hello")]]
     [:p "A link wiht an icon "
      [b/a "Home" :icon-data (icon/deficon "fa-home")]]]]])

(defn img-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p "The " [:strong "img"] " component generates an HTML <img> "
       "element. The component expects an argument which specifies "
       "the local or remote path i.e. the <img> src attribute. Optional "
       "supported keyword arguments include "]
      [:ul
       [:li [:strong ":class"] " - a string or vector of strings representing "
        "CSS class names"]
       [:li [:strong ":attrs"] " - a map of HTML attribute values. Keys are the "
        "HTML attribute names as keywords e.g. " [:code ":id"]]]]
     :header {:title "img - An HTML image component"}]]
   [:div.column
    [:pre
     [:code "[:p [basic/img \"images/bulma-logo.png\" :attrs {:width 400}]]"]]
    [:div.box
     [:h6.title.is-6 "Result"]
     [:p [b/img "images/bulma-logo.png" :attrs {:width 400}]]]]])

(defn render-vec-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p "The " [:strong "render-vec"] " component is a basic component "
       "which will render a ClojureScript vector as an unordered list. "
       "It takes only one argument, the vector to render."]]
     :header {:title "render-vec - Render a ClojureScript vector"}]]
   [:div.column
    [:pre
     [:code
      "[:p \"This is a ClojureScript vector \"]" [:br]
      "[basic/render-vec [:a :b :c]]"]]
    [:div.box
     [:h6.title.is-6 "Result"]
     [:p "This is a ClojureScript vector " ]
     [b/render-vec [:a :b :c]]]]])

(defn render-set-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p "The " [:strong "render-set"] " component renders a ClojureScript "
       "set as a comma delimited list surrounded in parenthesis. It accepts "
       "one argument, the set to render."]]
     :header {:title "render-set - Render a ClojureScript set"}]]
   [:div.column
    [:pre
     [:code
      "[:p \"This is a ClojureScript set \"]" [:br]
      "[basic/render-set #{1 2 3}]"]]
    [:div.box
     [:h6.title.is-6 "Result"]
     [:p "This is a ClojureScript set " ]
     [b/render-set #{1 2 3}]]]])

(defn render-map-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p "The " [:strong "render-map"] " component can render a "
       "ClojureScript map as an HTML table. Keys in the map will be "
       "rendered as table headings and cells will be rendered as string or "
       "with " [:strong "render-vec"] " and " [:strong "render-set"]
       " when appropriate"]]
     :header {:title "render-map - Render a ClojureScript map"}]]
   [:div.column
    [:pre
     [:code
      "(let [m {:key1 \"a string\"" [:br]
      "         :key2 [:a :b :c]" [:br]
      "         :key3 #{1 2 3}}]" [:br]
      "  [:<>" [:br]
      "    [:p \"Below is a ClojureScript map\"]" [:br]
      "    [render-map m]])" [:br]]]
    [:div.box
     [:h6.title.is-6 "Result"]
     (let [m {:key1 "a string"
              :key2 [:a :b :c]
              :key3 #{1, 2, 3}}]
       [:<>
        [:p "Below is a ClojureScript map"]
        [b/render-map m]])]]])

(defn breadcrumb-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p "The " [:strong "breadcrumbs"] " component provides a trail "
       "of breadcrumb links which can be used to show the path to the "
       "current page and navigate to previous pages in the trail. "
       "The component expects at least 2 arguments, a " [:em "sid"]
       " keyword representing the path into the global state atom where "
       "the current selected link state will be stored and " [:em "crubms"]
       ", a vector of maps which define each link in the breadcrumb trail"]
      [:p "The " [:em "sid"] " keyword argument uses a period as a path "
       "separator e.g. :ui.page.current is translated to the vector "
       "[:ui :page :current] and used as the keys for storing the value "
       "associated with a breadcrumb link when it is clicked."]
      [:p "The " [:em "crumbs"] " argument is a vector of maps where "
       "each map defines a link in the breadcrumb trail. Available keys "
       "for the map are"]
      [:ul
       [:li [:strong ":name"] " The text to use in the link name"]
       [:li [:strong ":value"] " value to store when the link is clicked"]
       [:li [:strong ":active"] " true when this link is the active link"]
       [:li [:strong ":icon"] " An icon data structure representing an icon "
        "to be added to the link. See " [:strong "theophilusx.yorick.icon"]
        " for details"]]]
     :header {:title "breadcrumbs - A component to render a trail of breadcrumb links"}]]
   [:div.column
    [:pre
     [:code
      "[basic/breadcrumbs :demo.breadcrumb.value" [:br]
      "  [{:name \"Page 1\"" [:br]
      "    :value :page1}" [:br]
      "   {:name \"Page 2\"" [:br]
      "    :value :page2}" [:br]
      "   {:name \"Page 3\"" [:br]
      "    :value :page3" [:br]
      "    :active true}]]" [:br]]]
    [:div.box
     [:h6.title.is-6 "Result"]
     [b/breadcrumbs :demo.breadcrumb.value
      [{:name "Page 1"
        :value :page1}
       {:name "Page 2"
        :value :pgae2}
       {:name "Page 3"
        :value :page3
        :active true}]]]]])

(defn notification-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p "The " [:strong "notification"] " component provides a basic "
       "component for rendering a notification for the user. The "
       "component expects at least one argument, the " [:em "body"]
       " argument, which can be pretty much anything (another component, "
       "hiccup markup, strings etc)"]
      [:p "The component also supports optional keyword arguments"]
      [:ul
       [:li [:strong ":class"] " A string or vector of strings "
        "representing CSS class names"]
       [:li [:strong ":delete"] " If set to true, adds a close button "
        "to the top left corner of the notification"]]]
     :header {:title "notification - A basic notification component"}]]
   [:div.column
    [:pre
     [:code
      "[basic/notification [:<>" [:br]
      "                      [:h2.title.is-2 \"Look out!\"]" [:br]
      "                      [:p \"Something evil this way comes\"]]" [:br]
      "  :delete true :class \"is-danger\"]"]]
    [:div.box
     [:h6.title.is-6 "Result"]
     [b/notification [:<>
                      [:h2.title.is-2 "Look out!"]
                      [:p "Something evil this way comes"]]
      :delete true :class "is-danger"]]]])

(defn basic-page []
  [:<>
   [:div.content
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
     "values are what you expect or for debugging problems in state values"]]
   [:hr]
   [t/tab :ui.tabs.basic-page [(t/deftab "a" :id :a)
                               (t/deftab "img" :id :img)
                               (t/deftab "breadcrumb" :id :breadcrumb)
                               (t/deftab "notification" :id :notification)
                               (t/deftab "render-set" :id :set)
                               (t/deftab "render-vec" :id :vec)
                               (t/deftab "render-map" :id :map)]
    :size :large :position :center]
   (case (get-in global-state (spath :ui.tabs.basic-page))
     :a [a-component]
     :img [img-component]
     :breadcrumb [breadcrumb-component]
     :notification [notification-component]
     :set [render-set-component]
     :vec [render-vec-component]
     :map [render-map-component]
     [a-component])])
