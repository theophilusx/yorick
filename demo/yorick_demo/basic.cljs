(ns yorick-demo.basic
  (:require [theophilusx.yorick.basic :as b]
            [theophilusx.yorick.icon :as icon]
            [theophilusx.yorick.tab :as t]
            [theophilusx.yorick.store :refer [cursor]]
            [theophilusx.yorick.utils :refer [spath]]
            [yorick-demo.comp :refer [doc-comp example]]))

(def page-desc1
  [:p "The " [:strong "theophilusx.yorick.basic"] " namespace contains basic components that
are very simple. They are mainly components related to text formatting, simple HTML entities
or basic rendering of ClojureScript data structures. Essentially, if I found typing the raw
hiccup version of an HTML element was common enough or tedious enough, I created a simple
component to do the work."])

(def page-desc2
  [:p "The data structure rendering components are mainly used during development and
debugging of an application. In particular, when first developing an application,
I find it useful to dump the global state at the end of the page. This can be useful
when you want to verify state values are what you expect or for debugging problems
in state values"])

(def a-eg "
[:<> 
  [:p \"This is an example of an \" 
      [basic/a \"anchor\" :on-click #(js/alert \"Hello\")]
  [:p \"A link with an icon \"
     [basic/a \"Home\" :icon-data (icon/deficon \"far fa-house\")]]
")

(defn a-component []
  [:div.columns
   [:div.column
    [doc-comp "a - An HTML anchor component"
     [:p "The " [:strong "a"] " component generates an HTML <a> element."
         " The component expects to be called with one argument " [:em "title"]
         ", the text to use in the link. The component also accepts the "
         "followinkkkg keyword args."]
     [[":href" "Target for the link. If not supplied, defaults to #"]
      [":on-click" "A zero-arity function called when the link is clicked"]
      [":class" "A string or vector of strings which specify CSS classes to add to the component"]
      [":attrs" "A map of HTML5 attributes. Keys are keywordized version of HTML attribute name."]
      [":icon-data" "An icon data map. See theophilusx.yorick.icon for details."]]]]
   [:div.column
    [example a-eg [:<>
                   [:p "This is an example of an "
                        [b/a "anchor" :on-click #(js/alert "Hello")]]
                   [:p "A link wiht an icon "
                    [b/a "Home" :icon-data (icon/deficon "fa fa-home")]]]]]])

(def img-eg "[:p [basic/img \"images/bulma-logo.png\" :attrs {:width 400}]]")

(defn img-component []
  [:div.columns
   [:div.column
    [doc-comp "img - An HTML image component"
     [:p "The " [:strong "img"] " component generates an HTML <img> "
       "element. The component expects an argument which specifies "
       "the local or remote path i.e. the <img> src attribute. Optional "
      "supported keyword arguments include "]
     [[":class" "A string or vector of strings representing CSS class names."]
      [":attrs" "A map of HTML attribute values. Keys are the HTML attribute names as keywords."]]]]
   [:div.column
    [example img-eg [:p [b/img "images/bulma-logo.png" :attrs {:width 400}]]]]])

(def breadcrumb-eg "
[basic/breadcrumbs :demo.breadcrumb.value
  [{:name \"Page 1\"
    :value :page1}
   {:name \"Page 2\"
    :value :page2}
   {:name \"Page 3\"
    :value :page3
    :active true}]]")

(defn breadcrumb-component []
  [:div.columns
   [:div.column
    [doc-comp "breadcrumbs - A component to render a trail of breadcrumb links"
     [:<>
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
       "for the map are"]]
     [[":name" "The text to use as the link name"]
      [":value" "Value to store in global state when the link is clicked"]
      [":active" "Set to true when this link is the active link"]
      [":icon" "An icon data map defining an icon to add to the link"]]]]
   [:div.column
    [example breadcrumb-eg [b/breadcrumbs :demo.breadcrumb.value
                            [{:name "Page 1"
                              :value :page1}
                             {:name "Page 2"
                              :value :pgae2}
                             {:name "Page 3"
                              :value :page3
                              :active true}]]]]])

(def notification-eg "
[basic/notification [:<>
                      [:h2.title.is-2 \"Look out!\"]
                      [:p \"Something evil this way comes\"]]
  :delete true :class \"is-danger\"]")

(defn notification-component []
  [:div.columns
   [:div.column
    [doc-comp "notification - A basic notification component"
     [:<>
      [:p "The " [:strong "notification"] " component provides a basic "
       "component for rendering a notification for the user. The "
       "component expects at least one argument, the " [:em "body"]
       " argument, which can be pretty much anything (another component, "
       "hiccup markup, strings etc)"]
      [:p "The component also supports optional keyword arguments"]]
     [[":class" "A string or vector of strings representing CSS class names"]
      [":delete" "If set to true, adds a close button to the top left corner of the notification"]]]]
   [:div.column
    [example notification-eg [b/notification [:<>
                                              [:h2.title.is-2 "Look out!"]
                                              [:p "Something evil this way comes"]]
                              :delete true :class "is-danger"]]]])

(def render-set-eg "
[:p \"This is a ClojureScript set \"]
[basic/render-set #{1 2 3}]")

(defn render-set-component []
  [:div.columns
   [:div.column
    [doc-comp "render-set - Render a ClojureScript set"
     [:p "The " [:strong "render-set"] " component renders a ClojureScript "
       "set as a comma delimited list surrounded in parenthesis. It accepts "
       "one argument, the set to render."] nil]]
   [:div.column
    [example render-set-eg [:<>
                            [:p "This is a ClojureScript set " ]
                            [b/render-set #{1 2 3}]]]]])

(def render-vec-eg "
[:p \"This is a ClojureScript vector \"]
[basic/render-vec [:a :b :c]]")

(defn render-vec-component []
  [:div.columns
   [:div.column
    [doc-comp "render-vec - Render a ClojureScript vector"
     [:p "The " [:strong "render-vec"] " component is a basic component "
       "which will render a ClojureScript vector as an unordered list. "
       "It takes only one argument, the vector to render."] nil]]
   [:div.column
    [example render-vec-eg [:<>
                            [:p "This is a ClojureScript vector " ]
                            [b/render-vec [:a :b :c]]]]]])

(def render-map-eg "
(let [m {:key1 \"a string\"
         :key2 [:a :b :c]
         :key3 #{1 2 3}}]
  [:<>
    [:p \"Below is a ClojureScript map\"]
    [render-map m]])")

(defn render-map-component []
  [:div.columns
   [:div.column
    [doc-comp "render-map - Render a ClojureScript map"
     [:p "The " [:strong "render-map"] " component can render a "
       "ClojureScript map as an HTML table. Keys in the map will be "
       "rendered as table headings and cells will be rendered as string or "
       "with " [:strong "render-vec"] " and " [:strong "render-set"]
       " when appropriate"] nil]]
   [:div.column
    [example render-map-eg (let [m {:key1 "a string"
                                    :key2 [:a :b :c]
                                    :key3 #{1, 2, 3}}]
                             [:<>
                              [:p "Below is a ClojureScript map"]
                              [b/render-map m]])]]])

(defn basic-page []
  (let [cur (cursor (spath :ui.tabs.basic-page.active-tab))]
    (fn []
      [:<>
       [:div.content
        [:h2.title.is-2 "Basic Components"]
        page-desc1
        page-desc2]
       [:hr]
       [t/tab-bar :ui.tabs.basic-page [(t/deftab "a" :value :a)
                                       (t/deftab "img" :value :img)
                                       (t/deftab "breadcrumb" :value :breadcrumb)
                                       (t/deftab "notification" :value :notification)
                                       (t/deftab "render-set" :value :set)
                                       (t/deftab "render-vec" :value :vec)
                                       (t/deftab "render-map" :value :map)]
        :position :centered]
       (case @cur
         :a [a-component]
         :img [img-component]
         :breadcrumb [breadcrumb-component]
         :notification [notification-component]
         :set [render-set-component]
         :vec [render-vec-component]
         :map [render-map-component]
         [a-component])])))
