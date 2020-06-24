(ns yorick-demo.inputs
  (:require [theophilusx.yorick.card :as c]
            [theophilusx.yorick.input :as i]
            [reagent.core :as r]))

(defn field-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
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
     [:div.content
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
     [:div.content
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
      "                [input/field [input/input :password :pwd :model doc" [:br]
      "                              :placeholder \"enter password\"] :label \"Password:\"]" [:br]
      "                [:p (str \"Value entered: \" (:pwd @doc))]])]" [:br]
      "  [eg-fn])"]]
    [:div.box
     [:p "A basic text input field"]
     [i/field [i/input :text :value]]
     (let [doc (r/atom {})
           eg-fn (fn []
                   [:<>
                    [:p "Basic password input field example"]
                    [i/field [i/input :password :pwd :model doc
                              :placeholder "enter password"] :lable "Password:"]
                    [:p (str "Value entered: " (:pwd @doc))]])]
       [eg-fn])]]])

(defn input-field-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "inut-field"] " component is a convenience component "
       "which combines the " [:strong "field"] " and " [:strong "input"]
       " components. The " [:code "label"] " argument specifies a string to "
       "use as the label for the field. The " [:code "type"] " argument is a "
       "keyword representing the input type and corresponds to the HTML input "
       "element types e.g. :text, :email, :password etc. The " [:code "sid"]
       " argument is a storage identifier keyword which specifies the location "
       "to store data within the document model atom e.g. :my-form.field-value "
       "This component also supports a number of optional keyword arguments"]
      [:ul
       [:li [:strong ":classes"] " - a map of strings or vector of strings "
        "that represent CSS class names. Supported keys are "
        [:code ":field, :label and :input"]]
       [:li [:strong ":placeholder"] " - string used as the HTML placeholder "
        "attribute"]
       [:li [:strong ":required"] " - boolean representing the HTML required "
        "attribute"]
       [:li [:strong ":icon-data"] " - an icon-data map or vector of icon-data "
        "maps. See " [:code "theophilusx.yorick.icon"] " for details"]
       [:li [:strong ":model"] " - a reagent atom to be used as the document "
        "model"]
       [:li [:strong ":change-fn"] " - a function of one argument which is "
        "called when the input data changes to update the data stored in the "
        "document model atom at the location specified by the " [:code "sid"]]
       [:li [:strong ":disabled"] " - boolean used to set the HTML disabled "
        "attribute"]
       [:li [:strong ":min"] " - number used to set the HTML min attribute"]
       [:li [:strong ":max"] " - number used to set the HTML max attribute"]
       [:li [:strong ":minlength"] " - number used to set the HTML minLength "
        "attribute"]
       [:li [:strong ":maxlength"] " - number used to set the HTML maxLength "
        "attribute"]
       [:li [:strong ":readonly"] " - boolean used to set the HTML readonly "
        "attribute"]
       [:li [:strong ":size"] " - number used to set the HTML size attribute"]]]
     :header {:title "input-field - an input field convenience component"}]]
   [:div.column
    [:pre
     [:code
      "[:p \"Example input-field component\"]" [:br]
      "(let [doc (r/atom {})" [:br]
      "      frm (fn []" [:br]
      "            [:<>" [:br]
      "              [input-field \"First Name\" :text :name.first :model doc]" [:br]
      "              [input-field \"Last Name\" :text :name.last :model doc]" [:br]
      "              [:p (str \"Result: \" @doc)])]]" [:br]
      "  [frm])"]]
    [:div.box
     [:p "Example input-field component"]
     (let [doc (r/atom {})
           frm (fn []
                 [:<>
                  [i/input-field "First Name" :text :name.first :model doc]
                  [i/input-field "Last Name" :text :name.last :model doc]
                  [:p (str "Result: " @doc)]])]
       [frm])]]])

(defn checkbox-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "checkbox"] " component provides a basic checkbox "
       "input component. The mandatory argument " [:code "label"] " is a string "
       "used as the label to associate with the checkbox. The " [:code "sid"]
       " argument is a storage identifier which determines where the input will "
       "be stored within the document model atom. This component also supports "
       "a number of optional keyword arguments"]
      [:ul
       [:li [:strong ":model"] " - a reagent atom used as the document model"]
       [:li [:strong ":change-fn"] " - a function of 1 argument which is called "
        "when the input changes to update the document model atom"]
       [:li [:strong ":classes"] " - a map of strings or vector of strings "
        "representing CSS class names. Supported keys are "
        [:code ":field, :control, :label and :input"]]
       [:li [:strong ":checked"] " - boolean value used to set the HTML checked "
        "attribute"]
       [:li [:strong ":required"] " - boolean value used to set the HTML required"
        " attribute"]]]
     :header {:title "checkbox - a basic checkbox component"}]]
   [:div.column
    [:pre
     [:code
      "[:p \"A basic checkbox example\"]" [:br]
      "[input/checkbox \"I am a checkbox\" :value]" [:br]
      "(let [doc (r/atom {})" [:br]
      "      frm (fn []" [:br]
      "            [:<>" [:br]
      "              [:p \"Another checkbox example\"]" [:br]
      "              [input/checkbox \"Check me!\" :form.checkme :model doc]" [:br]
      "              [:p (str \"Result: \" @doc)]])]" [:br]
      "  [frm])"]]
    [:div.box
     [:p "A basic checkbox example"]
     [i/checkbox "I am a checkbox" :value]
     (let [doc (r/atom {})
           frm (fn []
                 [:<>
                  [:p "another checkbox example"]
                  [i/checkbox "Check me!" :form.checkme :model doc]
                  [:p (str "Result: " @doc)]])]
       [frm])]]])

(defn radio-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "radio"] " component provides a basic radio button "
       "component. The " [:code "sid"] " argument is a keyword which specifies "
       "where to store the input value within the document model atom. "
       "The " [:code "labels"] " argument is a vector of maps which define the "
       "buttons for the component. The following keys are supported "]
      [:ul
       [:li [:strong ":title"] " - a string to be used as the label for a "
        "button"]
       [:li [:strong ":value"] " - the value to be stored in the document model "
        "atom when this button is selected. If not defined, the :title value, "
        "converted to a keyword, will be used"]
       [:li [:strong ":checked"] " - a boolean value. If true, that button will "
        "be rendered checked. Only one button should have this attribute set to "
        "true"]]
      [:p
       "The component also supports a number of optional keyword arguments"]
      [:ul
       [:li [:strong ":model"] " - a reagent atom used as the document model "
        "for this component"]
       [:li [:strong ":change-fn"] " - a function of 1 argument which is called "
        "when input for the component changes. The argument is the new value to "
        "be stored in the document model atom"]
       :li [:strong ":classes"] " - a map of strings or vectors of strings "
       "that represent CSS class names. Supported keys are "
       [:code ":control"] ", " [:code ":input"] " and " [:code ":label"]]]
     :header {:title "radio - a basic radio button component"}]]
   [:div.column
    [:pre
     [:code
      "[:p \"A basic radio button group\"]" [:br]
      "[input/radio :value [{:title \"Red\"} {:title \"Green\"} {:title \"Blue\"}]]"
      [:br]
      "(let [doc (r/atom {})" [:br]
      "      frm (fn []" [:br]
      "            [:<>" [:br]
      "              [:p \"Another radio button example\"]" [:br]
      "              [input/radio :pet.type [{:title \"Dog\"" [:br]
      "                                       :value :dog" [:br]
      "                                       :checked true}" [:br]
      "                                      {:title \"Cat\"" [:br]
      "                                       :value :cat}" [:br]
      "                                      {:title \"Diamond Python\"" [:br]
      "                                       :value :snake}]" [:br]
      "                :model doc]" [:br]
      "              [:p (str \"Result: \" @doc)]])]" [:br]
      "   [frm])"]]
    [:div.box
     [:p "A basic radio button group"]
     [i/radio :value [{:title "Red"} {:title "Green"} {:title "Blue"}]]
     (let [doc (r/atom {})
           frm (fn []
                 [:<>
                  [:p "Another radio button example"]
                  [i/radio :pet.type [{:title "Dog"
                                       :value :dog
                                       :checked true}
                                      {:title "Cat"
                                       :value :cat}
                                      {:title "Diamond Python"
                                       :value :snake}]
                   :model doc]
                  [:p (str "Result: " @doc)]])]
       [frm])]]])

(defn button-component []
  [:<>
   [:div.columns
    [:div.column.is-half
     [c/card
      [:div.content
       [:p
        "The " [:strong "button"] " component is a basic HTML button component "
        "This component wraps an HTML button inside a " [:code "control"]
        " and " [:code "field"] " div elements to support spacing and "
        "alignment. The " [:code "title"] " is a string which is used as the "
        "text on the button. The " [:code "action"] " argument is a function "
        "of no arguments which is called when the button is clicked. This "
        "component also supports the optional keyword argument "
        [:code ":classes"] " which is a map of strings or vectors of strings "
        "representing CSS class names. The supported keys in the map are "
        [:code ":field"] ", " [:code ":control"] " and " [:code ":button"] "."]]
      :header {:title "button - a basic button component"}]]
    [:div.column
     [:pre
      [:code
       "[:p \"A simple button example\"]" [:br]
       "[input/button \"Click Me!\" #(js/alert \"You clicked me\")]" [:br]
       "[:p \"A small button\"]" [:br]
       "[input/button \"Small Button\" #(js/alert \"Clicked small button\")" [:br]
       "  :classes {:button \"is-small\"}]" [:br]
       "[:p \"A large button\"]" [:br]
       "[input/button \"Large Button\" #(js/alert \"Clicked large button\")" [:br]
       "  :classes {:button \"is-large\"}]" [:br]
       "[:p \"A button with colour\"]" [:br]
       "[input/button \"Coloured\" #(js/alert \"You clicked a coloured button\")"
       [:br] "   :classes {:button \"is-primary\"}]"]]
     [:div.box
      [:p "A simple button example"]
      [i/button "Click Me!" #(js/alert "You clicked me")]
      [:p "A small button"]
      [i/button "Small Button" #(js/alert "Clicked small button")
        :classes {:button "is-small"}]
      [:p "A large button"]
      [i/button "Large Button" #(js/alert "Clicked large button")
       :classes {:button "is-large"}]
      [:p "A button with colour"]
      [i/button "Coloured" #(js/alert "You clicked a coloured button")
       :classes {:button "is-primary"}]]]]])

(defn editable-field-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "editable-field"] " component is a utility component "
       "which displays a value followed by a 'pencil' icon, which when clicked "
       "allows for editing of the value. When editing the value, buttons are "
       "provided to save or cancel the edit."]
      [:p
       "The " [:code "type"] " argument is a keyword representing the HTML "
       "input type e.g. :text, :email, :password. The " [:code "src"]
       " argument is a reagent atom representing the data to be edited. "
       "The " [:code "sid"] " argument is a storage identifier keyword which "
       "defines the path into the reagent document model atom where the data "
       "to edit is stored."]
      [:p
       "This component also supports two optional keyword arguments. The "
       [:code ":label"] " argument is a string representing a label to be used "
       "as a label for the data being displayed or edited. The "
       [:code ":classes"] " argument is a map of string or vectors of strings "
       "representing CSS class names. The following keys are supported: "]
      [:ul
       [:li [:strong ":edit-input"] " - CSS class names to be associated with "
        "the input element when values are being edited"]
       [:li [:strong ":display-input"] " - CSS class names to be associated with "
        "the div wrapping the data when being displayed"]
       [:li [:strong ":save-btn-field"] " - CSS class names to be associated "
        "with the field div that wraps the save button component"]
       [:li [:strong ":save-btn-control"] " - CSS class names associated with "
        "the control div which wraps the save button component."]
       [:li [:strong ":save-btn-button"] " - CSS class names associated with "
        "the button element within the save button component."]
       [:li [:strong ":cancel-btn-field"] " - CSS class names associated with "
        "the field div that wraps the cancel button component"]
       [:li [:strong ":cancel-btn-control"] " - CSS class names associated with "
        "the control div that wraps the cancel button component"]
       [:li [:strong ":cancel-btn-button"] " - CSS class names associated with "
        "the button element used in the cancel button component"]
       [:li [:strong ":label"] " - CSS class names to associate with the label "
        "element associated with the data"]
       [:li [:strong ":field-edit"] " - CSS class names to associate with the "
        "field div used to wrap the edit component"]
       [:li [:strong ":field-edit-body"] " - CSS class names to associate with "
        "the field body div used in the edit component"]
       [:li [:strong ":field-display"] " - CSS class names to associate with "
        "the field div used to wrap the data display component"]
       [:li [:strong ":field-display-body"] " - CSS class names to associate "
        "with the field body div used to wrap display of data"]]]
     :header {:title "editable-field - a editable field component"}]]
   [:div.column
    [:pre
     [:code
      "(let [doc (r/atom {:name {:first \"John\" :last \"Doe\"}})" [:br]
      "      frm (fn []" [:br]
      "            [:<>"
      "              [:p \"Editable field exmaple\"]" [:br]
      "              [input/editable-field :text doc :name.first" [:br]
      "                :label \"First Name:\"]" [:br]
      "              [input/editable-field :text doc :name.last" [:br]
      "                :label \"Last Name:\"]" [:br]
      "              [:p (str \"Result: \" @doc)])]]" [:br]
      "  [frm])"]]
    [:div.box
     (let [doc (r/atom {:name {:first "John" :last "Doe"}})
           frm (fn []
                 [:<>
                  [:p "Editable field example"]
                  [i/editable-field :text doc :name.first
                   :label "First Name:"]
                  [i/editable-field :text doc :name.last
                   :label "Last Name:"]
                  [:p (str "Result: " @doc)]])]
       [frm])]]])

(defn textarea-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "textarea"] " component provides a basic text area "
       "component for free-form text input. The " [:code "label"] " argument "
       "is a string of text used as a label for the text area. The "
       [:code "sid"] " argument is a storage identifier keyword used to "
       "determine the location within the document model atom to store the "
       "data entered into the text area. The component also supports a number "
       "of optional keyword arguments"]
      [:ul
       [:li [:strong ":model"] " - A reagent atom used as the document model"]
       [:li [:strong ":change-fn"] " - A function of one argument called when "
        "the input data changes. The argument is the updated data. Used to "
        "determine where the data will be stored."]
       [:li [:strong ":classes"] " - A map of CSS class names or vectors of CSS "
        "class names to be associated with elements within the component. "
        "Supported keys are " [:code ":field, :label and :textarea"]]
       [:li [:strong ":placeholder"] " - A string of text to be used as the "
        "placeholder text in the text area box."]]]
     :header {:title "textarea - a basic text area component"}]]
   [:div.column
    [:pre
     [:code
      "[:p \"A basic textarea example\"]" [:br]
      "(let [doc (r/atom {})" [:br]
      "      frm (fn []" [:br]
      "            [:<>" [:br]
      "              [input/textarea \"Text Area Label\" :text.value :model doc]"
      [:br]
      "              [:p (str \"Result: \" @doc)]])]" [:br]
      "  [fmt])"]]
    [:div.box
     [:p "A basic textarea example"]
     (let [doc (r/atom {})
           frm (fn []
                 [:<>
                  [i/textarea "Text Area Label" :text.value :model doc]
                  [:p (str "Result: " @doc)]])]
       [frm])]]])

(defn select-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "select"] " component provides a basic select list. The "
       [:code "sid"] " argument is a storage identifier keyword. The "
       [:code "options"] " argument is a vector of option maps which define "
       "each of the options for the select list. The " [:strong "defoption"]
       "function can be used to assist in defining the option definition maps"]
      [:p
       "The " [:strong "defoption"] " function is a helper function which "
       "assists in defining the option maps used in a select component. It has "
       "one compulsory argument, " [:code "title"] ", which defines the option "
       "title. The function also accepts a number of optional keyword arguments"]
      [:ul
       [:li [:strong ":value"] " - sets the value to be used when the option "
        "is selected. If not specified, the " [:code "title"] " argument is used"]
       [:li [:strong ":option-class"] " - a string or vector of strings "
        "representing CSS class names to add to the option element"]
       [:li [:strong ":disabled"] " - boolean which if true disables this option"]
       [:li [:strong ":label"] " - a shorthand label which is used if supported "
        "by the browser instead of the title"]]
      [:p
       "The " [:strong "select"] " component also supports a number of optional "
       "keyword arguments"]
      [:ul
       [:li [:strong ":model"] " - a reagent atom used as the document model "
        "for data storage"]
       [:li [:strong ":change-fn"] " - a function of one argument which is "
        "called when the input data changes. The argument is the new data."]
       [:li [:strong ":selected"] " - the value to use as the default selected "
        "option."]
       [:li [:strong ":multiple"] " - boolean. If true, allow multiple options "
        "to be selected"]
       [:li [:strong ":rounded"] " - boolean. If true, use rounded corners on "
        "the selection box"]
       [:li [:strong ":select-size"] " - sets the size of the select box. Can "
        "be " [:code ":large, :medium or :small"]]
       [:li [:strong ":icon-data"] " - an icon data map defining an icon to "
        "associate with the select box. See " [:strong "theophilusx/yorick/icon"]]]]
     :header {:title "select - a basic select box component"}]]
   [:div.column
    [:div.content
     [:pre
                [:code
        "[:p \"A simple select box\"]" [:br]
        "(let [doc (r/atom {})" [:br]
        "      frm (fn []" [:br]
        "            [:<>" [:br]
        "              [input/select :sel.value [(defoption \"One\")" [:br]
        "                                        (defoption \"Two\")" [:br]
        "                                        (defoption \"Three\")]" [:br]
        "                            :model doc]" [:br]
        "              [:p (str \"Result: \" @doc)]])]" [:br]
         "  [frm])"]]
     [:div.box
      [:p "A simple select box"]
      (let [doc (r/atom {})
            frm (fn []
                  [:<>
                   [i/select :sel.value [(i/defoption "One")
                                         (i/defoption "Two")
                                         (i/defoption "Three")]
                    :model doc]
                   [:p (str "Result: " @doc)]])]
        [frm])]]]])

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
   [input-component]
   [input-field-component]
   [checkbox-component]
   [radio-component]
   [button-component]
   [editable-field-component]
   [textarea-component]
   [select-component]])

