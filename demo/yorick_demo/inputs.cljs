(ns yorick-demo.inputs
  (:require [theophilusx.yorick.card :as c]
            [theophilusx.yorick.input :as i]))

(defn field-component []
  [:div.columns
   [:div.column
    [c/card
     [:<>
      [:p
       "The " [:strong "field"] " component is a general purpose component "
       "used as a container for other input components. The component has a "
       "mandatory `body` argument, which represents the contents of the field. "
       "This is typically an input component or a `<div>` element with the "
       "`control` CSS class associated with it. The component also supports "
       "two optional keyword arguments."]
      [:ul
       [:li [:strong ":label"] " - a text label to associate with the input "
        "component"]
       [:li [:strong ":classes"] " - a map of CSS class names to add. Supported "
        "map keys are `:field` for CSS classes to add to the field div and "
        "`:label` for CSS classes to add to the label component."]]]
     :header {:title "field - a general purpose container for input fields"}]]
   [:div.column
    [:pre
     [:code
      "[:p \"This is a basic field example\"]" [:br]
      "[input/field [:p \"Field contents\"]]" [:br]
      "[:p \"This is a field with a label\"]" [:br]
      "[:input/field [:p \"Field contents\"] :label \"field label\"]" [:br]
      "[:p \"This is a basic field with label and additional classes\"]" [:br]
      "[input/field [:p \"Field contents\"] :label \"field label\"" [:br]
      "  :classes {:field \"has-background-danger\" :label \"has-text-warning\"}]"]]
    [:div.box
     [:p "This is a basic field example"]
     [i/field [:p "Field contents"]]
     [:p "This is a field with a label"]
     [i/field [:p "Field contents"] :label "field label"]
     [:p "This is a basic field with label and additional classes"]
     [i/field
      [:p "Field contents"]
      :label "field label"
      :classes {:field "has-background-danger" :label "has-text-warning"}]]]])

(defn input-page []
  [:<>
   [:h2.title.is-2 "Input Components"]
   [:p
    "The " [:strong "theophilusx.yorick.input"] " namespace provides various "
    "components useful for collecting input from the user. Components in this "
    "group are typically used inside HTML forms, although some components are "
    "also useful in menus, toolbars and other areas where user input is "
    "required."]
   [:p
    "In " [:em "Bulma"] " input components are typically wrapped in a "
    [:code "<div>"] " element which has the " [:code "field"] " CSS class, with "
    "the actual input component in a " [:code "<div>"] " element with the "
    [:code "control"] " CSS class. This is done to ensure correct spacing and "
    "alignment of input fields. Often a " [:code "<label>"] " element is also "
    "added to the field to provide input labels. The components provided by "
    "this namespace ensure input fields meet these requirements and provide "
    "some higher level convenience components for common or useful input types."]
   [:div.columns
    [:div.column]
    [:div.column
     [:h4.title.is-4 "Example"]]]
   [field-component]])

