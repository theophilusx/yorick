(ns yorick-demo.basic
  (:require [theophilusx.yorick.basic :as b]
            [theophilusx.yorick.icon :as icon]
            [yorick-demo.comp :refer [doc-args doc-fn doc-ns]]))


;; a

(def a-title "a - An HTML anchor component")

(def a-desc [:p "The " [:strong "a"] " component generates an HTML <a> element."
             " The component expects to be called with one argument " [:em "title"]
             ", the text to use in the link."])

(def a-args [["title" "Text to use as the link name"]])

(def a-opt-args
  [[":href" "Target for the link. If not supplied, defaults to #"]
   [":on-click" "A zero-arity function called when the link is clicked"]
   [":class" "A string or vector of strings which specify CSS classes to add to the component"]
   [":attrs" "A map of HTML5 attributes. Keys are keywordized version of HTML attribute name."]
   [":icon-data" "An icon data map. See theophilusx.yorick.icon for details."]])

(def a-examples
  [{:text  "
[:<> 
  [:p \"This is an example of an \" 
      [basic/a \"anchor\" :on-click #(js/alert \"Hello\")]
  [:p \"A link with an icon \"
     [basic/a \"Home\" :icon-data (icon/deficon \"far fa-house\")]] "
    :code (fn []
    [:<>
     [:p "This is an example of an "
      [b/a "anchor" :on-click #(js/alert "Hello")]]
     [:p "A link wiht an icon "
      [b/a "Home" :icon-data (icon/deficon "fa fa-home")]]])}])

(defn a-page []
  [doc-fn {:title a-title
           :desc a-desc
           :args a-args
           :opt-args a-opt-args
           :examples a-examples}])

;; img

(def img-title "img - An HTML image component")

(def img-desc [:p "The " [:strong "img"] " component generates an HTML <img> "
               "element. The component expects an argument which specifies "
               "the local or remote path i.e. the <img> src attribute"])

(def img-args [["src" "The path to the image used as the source for this component"]])

(def img-opt-args
  [[":class" "A string or vector of strings representing CSS class names."]
   [":attrs" "A map of HTML attribute values. Keys are the HTML attribute names as keywords."]])

(def img-examples
  [{:text  "[:p [basic/img \"images/bulma-logo.png\" :attrs {:width 400}]]"
    :code (fn []
    [:p [b/img "images/bulma-logo.png" :attrs {:width 400}]])}])

(defn img-page []
  [doc-fn {:title img-title
           :desc img-desc
           :args img-args
           :opt-args img-opt-args
           :examples img-examples}])

;; breadcrumb

(def breadcrumb-title "breadcrumbs - A component to render a trail of breadcrumb links")

(def crumb-map [[":name" "The text to use as the link name"]
                [":value" "Value to store in global state when the link is clicked"]
                [":active" "Set to true when this link is the active link"]
                [":icon" "An icon data map defining an icon to add to the link"]])

(def breadcrumb-desc
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
    "for the map are"]
   [doc-args crumb-map "Crumb Map Keys"]])

(def breadcrumb-args [["sid" "A storage identifier which specifies where breadcrumb state data is managed within the global store"]
                      ["crumbs" "A vector of maps representing breadcrumb links"]])

(def breadcrumb-opt-args
  [[":class" "A string or vector of strings specifying CSS class names to add to the component"]
   [":position" "Position of the breadcrumbs, either :left, :center or :right. Defaults to :left"]
   [":separator" "Type of link separator to use. Possible values are :arrow, :bullet, :dot or :succeeds"]
   [":size" "Size of the breadcrumbs. Possible values :small, :medium and :large. Default to :medium"]])

(def breadcrumb-examples
  [{:text  "
[basic/breadcrumbs :demo.breadcrumb.value
  [{:name \"Page 1\"
    :value :page1}
   {:name \"Page 2\"
    :value :page2}
   {:name \"Page 3\"
    :value :page3
    :active true}]]"
    :code (fn []
    [b/breadcrumbs :demo.breadcrumb.value
     [{:name "Page 1"
       :value :page1}
      {:name "Page 2"
       :value :pgae2}
      {:name "Page 3"
       :value :page3
       :active true}]])}])

(defn breadcrumb-page []
  [doc-fn {:title breadcrumb-title
           :desc breadcrumb-desc
           :args breadcrumb-args
           :opt-args breadcrumb-opt-args
           :examples breadcrumb-examples}])

;; notification

(def notification-title "notification - A basic notification component")

(def notification-desc
  [:p "The " [:strong "notification"] " component provides a basic "
   "component for rendering a notification for the user. The "
   "component expects at least one argument, the " [:em "body"]
   " argument, which can be pretty much anything (another component, "
   "hiccup markup, strings etc)"])

(def notification-args [["body" "Content to display in the notification. Can be a string, Hiccup markup or another component"]])

(def notification-opt-args
  [[":class" "A string or vector of strings representing CSS class names"]
   [":delete" "If set to true, adds a close button to the top left corner of the notification"]])

(def notification-examples
  [{:text  "
[basic/notification [:<>
                      [:h2.title.is-2 \"Look out!\"]
                      [:p \"Something evil this way comes\"]]
  :delete true :class \"is-danger\"]"
    :code (fn []
    [b/notification [:<>
                     [:h2.title.is-2 "Look out!"]
                     [:p "Something evil this way comes"]]
     :delete true :class "is-danger"])}])

(defn notification-page []
  [doc-fn {:title notification-title
           :desc notification-desc
           :args notification-args
           :opt-args notification-opt-args
           :examples notification-examples}])

;; render-set

(def set-title "render-set - Render a ClojureScript set")

(def set-desc [:p "The " [:strong "render-set"] " component renders a ClojureScript "
               "set as a comma delimited list surrounded in parenthesis. It accepts "
               "one argument, the set to render."])

(def set-args [["s" "The set to be rendered"]])

(def set-examples
  [{:text  "
[:p \"This is a ClojureScript set \"]
[basic/render-set #{1 2 3}]"
    :code (fn []
    [:<>
     [:p "This is a ClojureScript set " ]
     [b/render-set #{1 2 3}]])}])

(defn render-set-page []
  [doc-fn {:title set-title
           :desc set-desc
           :args set-args
           :opt-args nil
           :examples set-examples}])

;; render-vec

(def vec-title "render-vec - Render a ClojureScript vector")

(def vec-desc [:p "The " [:strong "render-vec"] " component is a basic component "
               "which will render a ClojureScript vector as an unordered list. "
               "It takes only one argument, the vector to render."])

(def vec-args [["v" "The vector to be rendered"]])

(def vec-examples
  [{:text  "
[:p \"This is a ClojureScript vector \"]
[basic/render-vec [:a :b :c]]"
    :code (fn []
    [:<>
     [:p "This is a ClojureScript vector " ]
     [b/render-vec [:a :b :c]]])}])

(defn render-vec-page []
  [doc-fn {:title vec-title
           :desc vec-desc
           :args vec-args
           :opt-args nil
           :examples vec-examples}])

;; render-map

(def map-title "render-map - Render a ClojureScript map")

(def map-desc [:p "The " [:strong "render-map"] " component can render a "
               "ClojureScript map as an HTML table. Keys in the map will be "
               "rendered as table headings and cells will be rendered as string or "
               "with " [:strong "render-vec"] " and " [:strong "render-set"]
               " when appropriate"])

(def map-args [["m" "The map to be rendered"]])

(def map-examples
  [{:text  "
(let [m {:key1 \"a string\"
         :key2 [:a :b :c]
         :key3 #{1 2 3}}]
  [:<>
    [:p \"Below is a ClojureScript map\"]
    [render-map m]])"
    :code (fn []
    (let [m {:key1 "a string"
             :key2 [:a :b :c]
             :key3 #{1, 2, 3}}]
      [:<>
       [:p "Below is a ClojureScript map"]
       [b/render-map m]]))}])

(defn render-map-page []
  [doc-fn {:title map-title
           :desc map-desc
           :args map-args
           :opt-args nil
           :examples map-examples}])

;; NS

(def ns-sid :ui.sidebar.basic-ns)

(def ns-title  "Basic Components")

(def ns-desc
  [:<>
   [:p "The " [:strong "theophilusx.yorick.basic"] " namespace contains basic components that
are very simple. They are mainly components related to text formatting, simple HTML entities
or basic rendering of ClojureScript data structures. Essentially, if I found typing the raw
hiccup version of an HTML element was common enough or tedious enough, I created a simple
component to do the work."]
   [:p "The data structure rendering components are mainly used during development and
debugging of an application. In particular, when first developing an application,
I find it useful to dump the global state at the end of the page. This can be useful
when you want to verify state values are what you expect or for debugging problems
in state values"]])

(def ns-pages [{:title "a"
                :value :a
                :page a-page}
               {:title "img"
                :value :img
                :page img-page}
               {:title "breadcrumb"
                :value :breadcrumb
                :page breadcrumb-page}
               {:title "notification"
                :value :notification
                :page notification-page}
               {:title "render-set"
                :value :render-set
                :page render-set-page}
               {:title "render-vec"
                :value :render-vec
                :page render-vec-page}
               {:title "render-map"
                :value :render-map
                :page render-map-page}])

(defn ns-page []
  [doc-ns ns-sid ns-title ns-desc ns-pages])

