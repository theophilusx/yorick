(ns yorick-demo.navbars
  (:require [theophilusx.yorick.navbar :as nb]
            [theophilusx.yorick.card :as c]))

(defn navbar-page []
  [:div.content
   [:h2.title.is-2 "The Navbar Component"]
   [:p
    "The " [:strong "theophilusx.yorick.navbar"] " namespace provides support "
    "for a navigation bar at the top of the page. The " [:strong "navbar"]
    " component supports a brand item, menus, both standard and dropdown, "
    "static content and a responsive burger item for a responsive navbar which "
    "supports smaller screens."]
   [:p
    "A navbar component uses a local document model item to sore the definition "
    "of the navbar. The " [:code "defnavbar-item"] " function is provided as a "
    "helper function for defining menu item maps. The navbar also uses a global "
    " document model atom called " [:code "global-state"] ", which is defined in "
    "the " [:strong "theophilus.yorick.store"] " namespace, to record menu item "
    "selection. The selected item " [:code ":id"] " value is stored in the "
    "global state under the key " [:code ":active-item"], " which is in turn a "
    "key associated with " [:code ":sid"] " used by the navbar. This allows other"
    "components and ClojureScript code to use the current "
    [:code ":active-item"] " to determine what to render and what other actions "
    "to take when an item is selected."]
   [:p
    "The definition of a navbar is managed using a " [:code "map"] " consisting "
    "of the following keys:"]
   [:ul
    [:li [:strong ":sid"] " - a storage identifier keyword. Used to track the "
     "actively selected menu link in the global state store."]
    [:li [:strong ":has-shadow"] " - if true, the navbar will have a light "
     "sahdow effect"]
    [:li [:strong ":is-dark"] " - if true, the navbar is rendered with a dark "
     "background and light text"]
    [:li [:strong ":has-burger"] " - if true, the navbar will have a 'burger' "
     "menu on small screens which can be clicked to expand the menus"]
    [:li [:strong ":class"] " - a string or vector of strings specifying CSS "
     "class names"]
    [:li [:strong ":default-link"] " - a default menu link to be set as the "
     "default value when the navbar is first rendered"]
    [:li [:strong ":brand"] " - a " [:code "map"] " which defines a brand item "
     "to add to the navbar. See " [:code "defnavbar-item"] " for details"]
    [:li [:strong ":menus"] " - a vector of " [:code "map"] " elements used to "
     "to define the menu entries added from the left of the navbar (after the "
     "brand). See " [:code "defnavbar-item"] " for details on map structure"]
    [:li [:strong ":end-menu"] " - a vector of " [:code "map"] " elements used "
     "to define menus to add from the right side of the navbar. See "
     [:code "defnavbar-item"] " for details on structure of menu item maps"]]
   [c/card
    [:div.content
     [:p
      "The " [:strong "defnavbar-item"] " is a convenience function to help in "
      "the definition of navbar menu items. The function returns a "
      [:code "map"] " which can be used as a component in the " [:code ":menus"]
      " and " [:code ":end-menu"] " vectors as well as the " [:code ":brand"]
      " map. The " [:strong "defnavbar-item"] " function supports the following "
      "keyword arguments:"]
     [:ul
      [:li [:strong ":type"] " - the type of item. Supported types are "
       [:code ":a, :div, :dropdown, :raw"] " and " [:code ":divider"] ". See "
       "below for definition of each type"]
      [:li [:strong ":title"] " - used for dropdown menus. Specifies the title "
       "to be used in the parent menu"]
      [:li [:strong ":contents"] " - for " [:code ":dropdown"] " type items, "
       "this is a vector of item maps representing the dropdown menu entries. "
       "For other types, it is what will be rendered in the menu item e.g. "
       "menu title."]
      [:li [:strong ":class"] " - a string or vector of strings specifying CSS "
       "class names to be associated with the menu item"]
      [:li [:strong ":href"] " - a hyptertext reference to be associaed with the "
       "menu item. Defaults to " [:code "#"] " if not specified"]
      [:li [:strong ":id"] " - value for the HTML id attribute to be associated "
       "with the menu item. Defaults to " [:code "nav-<n>"] " if not specified "
       "where n is a unique number"]
      [:li [:strong ":icon-data"] " - an icon data map defining an icon to be "
       "included with the menu item label. See "
       [:strong "theophilusx.yorick.icon"] " for details"]
      [:li [:strong ":selectable"] " - if true, a click handler is associated "
       "with the menu item to enable it to be selected. Defaults to true"]
      [:li [:strong ":is-hoverable"] " - used with dropdown menu item types. If "
       "true, the dropdown menu items will be expanded when the mouse hovers "
       "over the parent menu"]]
     [:p
      "The " [:code ":type"] " value for menu items defines the type of menu "
      "to be generated. A number of different types are supported:"]
     [:ul
       [:li [:strong ":a"] " - the most common menu type and default if no :type "
        "is specified. The click handler associated with this menu type will "
        "copy the associated " [:code ":id"] " value to the "
        [:code ":active-item"] " key associated with the navbar " [:code ":sid"]
        " in the global state atom "
        [:code "theophilusx.yorick.store/global-state"]]
       [:li [:strong ":div"] " - this menu type is a basic generic type where "
        "any data you provide in the " [:code ":contents"] " key will be "
        "wrapped inside a " [:code "[div]"] " element. When selected, the "
        [:code ":id"] " associated with the menu will be set in the "
        [:code ":active-item"] " key within the "
        [:code "theophilusx.yorick.store/global-state"] " atom under the "
        [:code ":sid"] " associted with the navbar"]
       [:li [:strong ":raw"] " - this item type is the most generic and flexible. "
        "Essentially, anything added to the " [:code ":contents"] " key in the "
        "item definition is just added 'as-is'. No wrapping inside a div or other "
        "element."]
       [:li [:strong ":dropdown"] " - this menu type is used when defining a set "
        "of dropdown menus. For this type, the " [:code ":title"] " key is used "
        "for the text label of the parent menu and the " [:code ":contents"]
        " key is expected to contain a vector of menu definitions which define "
        "the menu items for the dropdown."]
       [:li [:strong ":divider"] " - a simple horizontal divider used in dropdown "
        "menus to separate items into groups"]]]
    :header {:title "defnavbar-item - a helper function for defining menu items"}]])

