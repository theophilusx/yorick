(ns yorick-demo.inputs
  (:require [theophilusx.yorick.card :as c]
            [theophilusx.yorick.input :as i]
            [reagent.core :as r]))

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

(defn input-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:<>
      [:p
       "A basic text input component. This component is typically wrapped "
       "inside a " [:em "field"] " or " [:em "horizontal-field"] " component. "
       "The " [:code "type"] " argument specifies the type of input field. The "
       "supported types are keywords derived from the HTML5 input types e.g. "
       [:code ":text"] ", " [:code ":email"] ", " [:code ":password"] ", etc."
       "The " [:code ":sid"] " argument specifies a storage identifier for the "
       "user input. A storage identifier is a " [:em "keyword"] " that specifies "
       "where the user input data will be stored within the document model "
       "atom. Any period in the keyword will be treated as a path separator to "
       "create key hierarchies for storage e.g. " [:code ":value"] ", "
       [:code ":my-form.value"] "."]
      [:p
       "The " [:code "input"] " component also supports a number of optional "
       "keyword arguments"]
      [:ul
       [:li [:strong ":model"] " - a reagent atom used as the document model "
        "for storage"]
       [:li [:strong "change-fn"] " - a function of one argument which will be "
        "called when the input data changes. Manages the storage of input "
        "data in the document model atom"]
       [:li [:strong ":classes"] " - a map of CSS class names to add to HTML "
        "elements which make up the component. Each value is either a string "
        "representing a CSS class name or a vector of strings representing "
        "CSS class names. Supported keys are " [:code ":control"] " and "
        [:code ":input"] "."]
       [:li [:strong ":icon-data"] " - either an icon-data map or a vector of "
        "icon-data maps. See " [:strong "theophilusx.yorick.icon"] " for "
        "details on the " [:code "icon-data"] " map."]
       [:li [:strong ":placeholder"] " - placeholder text to use in the input"]
       [:li [:strong ":required"] " - set HTML required attribute (boolean)"]
       [:li [:strong ":disabled"] " - set HTML disabled attribute (boolean)"]
       [:li [:strong ":min"] " - set the HTML min attribute (number)"]
       [:li [:strong ":max"] " - set the HTML max attribute (number)"]
       [:li [:strong ":minlength"] " - set the HTML minLength attribute (number)"]
       [:li [:strong ":maxlength"] " - set the HTML maxLength attribute (number)"]
       [:li [:strong ":readonly"] " - set the HTML readonly attribute (boolean)"]
       [:li [:strong ":size"] " - set the HTML size attribute (number)"]]]
     :header {:title "input - general purpose input component"}]]
   [:div.column
    [:pre
     [:code
      "[:p \"A basic text input field\"]" [:br]
      "[input/field [input/input :text :value]]" [:br]
      "(let [doc (reagent/atom {})]" [:br]
      "      eg-fn (fn []" [:br]
      "              [:<>" [:br]
      "                [:p \"Basic password input field example\"]" [:br]
      "                [input/field [input/input :password :value :model doc" [:br]
      "                              :placeholder \"enter password\"] :label \"Password:\"]" [:br]
      "                [:p (str \"Value entered: \" (:value @doc))]])]" [:br]
      "  [eg-fn])"]]
    [:div.box
     [:p "A basic text input field"]
     [i/field [i/input :text :value]]
     (let [doc (r/atom {})
           eg-fn (fn []
                   [:<>
                    [:p "Basic password input field example"]
                    [i/field [i/input :password :value :model doc
                              :placeholder "enter password"] :lable "Password:"]
                    [:p (str "Value entered: " (:value @doc))]])]
       [eg-fn])]]])

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
   [:h4.title.is-4 "Document Models and Storage identifiers"]
   [:p
    "Input data obtained from the user must be stored somewhere to be of use. "
    "The components in " [:em "Yorick"] " use reagent atoms for this storage. "
    "This library uses the term " [:em "document model"] " to refer to the "
    "Reagent atoms used to store user input. A " [:em "document model"] " is an "
    "atom witht the shape of a Clojurescript " [:em "map"] ". This means that "
    "multiple values can be stored within one document model (for example, all "
    "the inputs from a form can be stored in one document model). To control "
    "where data is stored within a document model, the concept of "
    [:em "storage identifiers"] " or " [:em "sid"] " is introduced. A "
    [:em "sid"] " is a " [:em "keyword"] " which specifies the path into a "
    "document model map atom where the data should be stored. To support key "
    "hierarchies in the map, the " [:em "sid"] " uses a period (.) as a path "
    "separator. For example, a sid with the value " [:code ":value"] " will "
    "store the input in the document model map under the key " [:em ":value"]
    " and a sid with the value " [:code ":my-form.value"] " will store the "
    "input in the document model map under the key " [:em ":value"] " which is "
    "under the key " [:em ":my-form"] "."]
   [:p
    "Most of the components in this namespace which accept user input have a "
    "local document model reagent atom which will be used if none is specified "
    "with the `:model` keyword argument. You can also specify a change function "
    "of one argument for more complex storage requirements. The `:change-fn` "
    "optional argument allows you to specify the function that will be called "
    "whenever the input data changes. The argument represents the new input "
    "value."]
   [:hr]
   [:div.columns.is-half
    [:div.column
     [:h4.title.is-4 "Description"]]
    [:div.column
     [:h4.title.is-4 "Example"]]]
   [field-component]
   [horizontal-field-component]
   [input-component]])

