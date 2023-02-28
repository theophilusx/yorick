(ns yorick-demo.navbars
  (:require [theophilusx.yorick.navbar :as nb]
            [theophilusx.yorick.basic :refer [render-map]]
            [theophilusx.yorick.store :refer [get-in]]
            [theophilusx.yorick.utils :refer [spath]]
            [theophilusx.yorick.input :refer [search]]
            [yorick-demo.comp :refer [doc-fn doc-ns]]))

;; link

(def link-title  "link - A navigation link item")

(def link-desc
  [:p "The " [:strong "theophilusx.yorick.navbar/link"] " function is a convenience function "
   "used to help create navigation links to be used in a navigation bar. The function returns "
   "a map defining the link. A number of keyword arguments are supported which can be used to "
   "override the default link definition parameters."])

(def link-args
  [["title" "The title to use as the text of the link."]])

(def link-opt-args
  [[":value" "Set the value associatged with the link when activatged. Defaults to the title converted to a keyword when absent."]
   [":icon" "An icon data map which specifies an icon to be added to the title text for th elink. See theophilusx.yorick.icon namespace for more details"]
   [":class" "A string or vector of strings specifying CSS class names to be added to the component."]
   [":id" "A unique value to be associagted with the link via the HTML id attribute. If not supplied, a gensym is used to generate a unique value"]])

(def link-examples
  [{:text  "
(let [l1 (nb/link \"Menu 1\")i
      l2 (nb/link \"Menu 2\" :value :menu2 :id \"mid2\")]
  [:<>
    [:h3 \"The result\"]
    [:p \"The link l1 map is \"]
    [render-map l1]
    [:p \"The link l2 map is \"]
    [render-map l2]])"
    :code (fn []
            (let [l1 (nb/link "Menu 1")
                  l2 (nb/link "Menu 2" :value :menu2 :id "mid2")]
              [:<>
               [:p "The link l1 map is "]
               [render-map l1]
               [:p "The link l2 map is"]
               [render-map l2]]))}])

(defn link-page []
  [doc-fn {:title link-title
           :desc link-desc
           :args link-args
           :opt-args link-opt-args
           :examples link-examples}])

;; container

(def cont-title  "container - A generic container component")

(def cont-desc
  [:p "The " [:strong "theophilusx.yorick.navbar/container"] " function is a "
      "conveniennce function used to create a generic container that can be used "
      "in the navigation bar to hold another component, such as some text, an image "
      "or a search box. Returns the correctly formatted contgainer map used by the navigation "
      "bar component."])

(def cont-args
  [["dontents" "The contents for the container. Can be a string, hiccup or another component."]])

(def cont-opt-args
  [[":class" "A string or a vector of strings specifying CSS classs names to be added to the component."]
      [":id" "A unique value to used for the HTML id attribute. If not provided, a value will be generated using a gensyym."]])

(def cont-examples
  [{:text  "
  (let [c1 (nb/container \"Some Text\")
        c2 (nb/container [:strong \"Shout out\"])
        c3 (nb/container [search #(js/alert (str \"You are searching for \" %))])]
      [:<>
        [:p \"Value of c1 is\"]
        [render-map c1]
        [:p \"Value of c2 is\"]
        [render-map c2]
        [:p \"Value of c3 is\"]
        [render-map c3]])"
    :code (fn []
            (let [c1 (nb/container "Some Text")
                  c2 (nb/container [:strong "Shout out"])
                  c3 (nb/container [search #(js/alert (str "You are searching for " %))])]
              [:<>
               [:p "Value of c1 is"]
               [render-map c1]
               [:p "Value of c2 is"]
               [render-map c2]
               [:p "Value of c3 is"]
               [render-map c3]]))}])

(defn container-page []
  [doc-fn {:title cont-title
           :desc cont-desc
           :args cont-args
           :opt-args cont-opt-args
           :examples cont-examples}])

;; dropdown

(def dd-title  "dropdown - A dropdown menu component")

(def dd-desc
  [:p "The " [:strong "theophilusx.yorick.navbar/dropdown"] " function is a "
      "convenience function which generates the map definition used to define a "
      "dropdown menu within the navigation bar. The dropdown menu can contain menus, "
      "a divider or another dropdown menu (only 1 nested dropdown menu supported)."])

(def dd-args
  [["title" "The title for the parent of the dropdown menu."]
   ["children" "A vector of navbar items. Can be a link, a container or a divider."]])

(def dd-opt-args
  [[":class" "A string or vector of strings specifying CSS class names to add to the component."]
   [":icon" "An icon data map defining an icon to add to the title of the dropdown."]
   [":hover?" "If true, the dropdown menu will appear when the mouse hovers over the parent title."]
   [":id" "Value used for the HTML id attribute. This value is used when tracking the state of the dropdown menu."]])

(def dd-examples
  [{:text  "
(let [dd-children [(nb/link \"Menu 1\")
                   (nb/link \"Menu 2\")
                   (nb/divider)
                   (nb/link \"Menu 3\")
      dd-menu (nb/dropdown \"Dropdown\" dd-children)]
  [:<>
    [:p \"The dropdown menu definition map is\"]
    [render-map dd-menu]])
"
    :code (fn []
            (let [dd-children [(nb/link "Menu 1")
                               (nb/link "Menu 2")
                               (nb/divider)
                               (nb/link "Menu 3")]
                  dd-menu (nb/dropdown "Dropdown" dd-children)]
              [:<>
               [:p "The dropdown menu definition map is"]
               [render-map dd-menu]]))}])

(defn dropdown-page []
  [doc-fn {:title dd-title
           :desc dd-desc
           :args dd-args
           :opt-args dd-opt-args
           :examples dd-examples}])

;; divider

(def div-title  "divider - A simple menu divider component")

(def div-desc
  [:p "The " [:strong "theophilusx.yorick.navbar/divider"] " function generates "
   "a definiton map for a simple menu divider. "])

(def div-examples
  [{:text  "
(let [d (nb/divider)]
   [:<>
     [:p \"The divider map is\"]
     [render-map d]])"
    :code (fn []
            (let [d (nb/divider)]
              [:<>
               [:p "The divider map is"]
               [render-map d]]))}])

(defn divider-page []
  [doc-fn {:title div-title
           :desc div-desc
           :args nil
           :opt-args nil
           :examples div-examples}])

;; navbar

(def nav-title  "navbar - a navigation bar component")

(def nav-desc
  [:<>
      [:p "The " [:strong "navbar"] " component is a basic navigation bar. "
       "It can contain a brand, menus, both standard and dropdown, static text "
       "or form elements, such as for a search box. The navbar can have different "
       "colours, hover and active item effects and is responsive to different screen "
       "sizes. A burger menu is also supported for touch screens and the navbar "
       "can contain a brand component."]
      [:p "The component uses a Reagent atom to store the navbar state. The location of the state "
       "data is controlled by the storage identifier a.k.a. " [:code "sid"] ". Clients can query the "
       "state information to trigger actions or render different content. The navbar state is "
       "stored in a map within the document store. The " [:code "sid"] " specifies the location "
       "within the store for storing the map. The map contains the " [:code "active-item"]
       " key, which holds the value for the currently selected menu item."]])

(def nav-args
  [["sid" "The storage identifier which defines the location within the document store where the navbar state will be stored."]
   ["menus" (str "A map of navbar items which make up the contents of the navbar i.e. links, "
                 "dropdown menus or generic data containers. The map supports two keys, "
                 [:code ":start"] " (mandatory) and " [:code ":end"] " (optional). "
                 "The value for each key is a vector of navbar items. The :start value will place "
                 "items from the left to the right. The :end value will be placed from the right to "
                 "the left.")]])

(def nav-opt-args
  [[":default" "The default menu selection. This will be the initial value when the component is first rendered."]
   [":brand" "A vector of items representing a brand entry. This will be placed as the left most item in the navbar."]
   [":has-burger?" "If true, a burger menu will be rendered on small screens."]
   [":spaced?" "When true, additional space will be renedered around menu items."]
   [":color" "Set the navbar color. When present, it is a keywordized verson of the Bulma color classes e.g. :is-dark"]
   [":transparent?" "When true, rememove any hover or active background effects."]
   [":expanded?" "When true, the component expands to be a full width component."]
   [":tabs?" "When true, add a bottom border on hover or for active menu itmes."]
   [":class" "A string or vector of strings specifying CSS classes to add to the component."]
   [":shadow?" "When true, add a subtle shadow effect to the navbar."]])

(def nav-examples
  [{:text  "
  (let [menus {:start [(nb/link \"Menu 1\")
                       (nb/link \"Menu 2\")
                       (nb/dropdown \"dropdown\" [(nb/link \"DDown 1\")
                                                  (nb/link \"DDown 2\")
                                                  (nb/divider)
                                                  (nb/link \"DDown 3\")])]
               :end [(nb/link \"Help\" :value :help)
                     (nb/link \"Login\" :value :login)]}
       sid :ui.demo.navbar1]
    [:<>
      [nb/navbar sid menus]
      [:p (str \"Current navbar state data: \" (get-in (spath sid)))]])"
    :code (fn []
            (let [menus {:start [(nb/link "Menu 1" :value :menu1)
                                 (nb/link "Menu 2" :value :menu2)
                                 (nb/dropdown "dropdown" [(nb/link "DDown 1" :value :sub-menu1)
                                                          (nb/link "DDown 2" :value :sub-menu2)
                                                          (nb/divider)
                                                          (nb/link "DDown 3" :value :sub-menu3)])]
                         :end [(nb/link "Help" :value :help)
                               (nb/link "Login" :value :login)]}
                  sid :ui.demo.navbar1]
              [:<>
               [nb/navbar sid menus]
               [:p (str "Current navbar state data: " (get-in (spath sid)))]]))}
   {:text "
(let [menus {:start [(nb/link \"Home\" :value :home)
                     (nb/link \"About\" :value :about)
                     (nb/link \"Support\" :value :support)]
             :end [(nb/link \"Login\" :value :login)]}
      brand-data [(nb/container \"Best Quality\")
                  (nb/link \"Widgets Inc\")]
      sid :ui.demo.navbar2]
   [:<>G
     [nb/navbar sid menus :brand brand-data]
     [:p (str \"Current navbar state data: \" (get-in (spath sid)))]])"
    :code (fn []
            (let [menus {:start [(nb/link "Home" :value :home)
                                 (nb/link "About" :value :about)
                                 (nb/link "Support" :value :support)]
                         :end [(nb/link "Login" :value :login)]}
                  brand-data [(nb/container "Best Quality")
                              (nb/link "Widgets Inc")]
                  sid :ui.demo.navbar2]
              [:<>
               [nb/navbar sid menus :brand brand-data]
               [:p (str "Current navbar state data: " (get-in (spath sid)))]]))}])

(defn navbar-page []
  [doc-fn {:title nav-title
           :desc nav-desc
           :args nav-args
           :opt-args nav-opt-args
           :examples nav-examples}])

;; NS

(def ns-sid :ui.sidebar.navbar-ns)

(def ns-title  "The Navbar Component")

(def ns-desc
  [:<>
   [:p "The " [:strong "theophilusx.yorick.navbar"] " namespace provides support "
    "for a horizontal navigation bar. The " [:strong "navbar"]
    " component supports a brand item, menus, both standard and dropdown, "
    "static content, a generic content container and a responsive burger "
    "menu for a responsive navbar which supports smaller screens."]
   [:p "This namespace provides a number of helper functions useful when defining menu "
    "items, a brand entry and generic data containers. The state of the navbar is tracked "
    "in a Reagent atom used as the document store. A storage identifier, a.k.a. "
    [:code "sid"] " is used to define the location wihtin the store to track the navbar state."
    "Client applications can use the state information to respond to user input, such as "
    "displaying specific content or taking other action in response to the user selecting an item."]])

(def ns-pages [{:title "link"
                :value :link
                :page link-page}
               {:title "container"
                :value :container
                :page container-page}
               {:title "dropdown"
                :value :dropdown
                :page dropdown-page}
               {:title "divider"
                :value :divider
                :page divider-page}
               {:title "navbar"
                :value :navbar
                :page navbar-page}])

(defn ns-page []
  [doc-ns ns-sid ns-title ns-desc ns-pages])


(comment
  (nb/dropdown "Test" [(nb/link "M1") (nb/link "M2")])
  )
