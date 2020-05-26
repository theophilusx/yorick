(ns yorick-demo.inputs
  (:require [theophilusx.yorick.card :as c]
            [theophilusx.yorick.input :as i]))

(defn field-component []
  [:div.columns
   [:div.column.is-half
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

(defn horizontal-field-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:<>
      [:p
       "The " [:strong "horizontal-field"] " component is similar to the "
       [:strong "field"] " component, except that instead of putting label "
       "and field elements vertically, they are placed horizontally. The "
       "component has two mandatory arguments, `label` and `body`. The `label` "
       "is text to be used as the field label. The `body` is the contents of "
       "the field. The component also supports an optional keyword argument "
       "`:classes`, which is a map containing CSS class names or vectors of "
       "CSS class names. The supported keys in the map are `:field` for CSS "
       "classes to be associated with the outer field element, `:label` for "
       "CSS classes to associate with the field label and `:body` for CSS "
       "classes to associate with the contents of the field."]]
     :header {:title "horizontal-field - a horizontal field container"}]]
   [:div.column
    [:pre
     [:code
      "[:p \"A basic horizontal field example\"]" [:br]
      "[input/horizontal-field \"Field Label\" [:p \"field contents\"]]" [:br]
      "[:p \"A basic horizontal field with additional CSS classes\"]" [:br]
      "[input/horizontal-field \"Field Label\" [:p \"field contents\"]" [:br]
      "  :classes {:field \"has-background-danger\"" [:br]
      "            :label \"has-text-warning\"" [:br]
      "            :body \"has-background-primary\"}]"]]
    [:div.box
     [:p "A basic horizontal field exmaple"]
     [i/horizontal-field "Field Label" [:p "field contents"]]
     [:p "A basic horizontal field with additional CSS classes"]
     [i/horizontal-field "Field Label" [:p "field contents"]
      :classes {:field "has-background-danger"
                :label "has-text-warning"
                :body "has-background-primary"}]]]])

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
   [:hr]
   [:div.columns.is-half
    [:div.column
     [:h4.title.is-4 "Description"]]
    [:div.column
     [:h4.title.is-4 "Example"]]]
   [field-component]
   [horizontal-field-component]])

