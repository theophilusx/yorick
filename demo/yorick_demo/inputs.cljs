(ns yorick-demo.inputs
  (:require [theophilusx.yorick.card :as c]
            [theophilusx.yorick.input :as i]
            [reagent.core :as r]
            [theophilusx.yorick.icon :as icons]
            [theophilusx.yorick.tab :as t]
            [theophilusx.yorick.store :refer [get-in global-state]]
            [theophilusx.yorick.utils :refer [spath]]))

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
      "[:input/field [:p \"Field contents\"] " [:br]
      "  :label \"field label\"]" [:br]
      "[:p \"This is a basic field with label and additional classes\"]" [:br]
      "[input/field [:p \"Field contents\"] :label \"field label\"" [:br]
      "  :classes {:field \"has-background-danger\" " [:br]
      "  :label \"has-text-warning\"}]"]]
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
      "[input/horizontal-field \"Field Label\" " [:br]
      "  [:p \"field contents\"]]" [:br]
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
       [:li [:strong ":attrs"] " - a map of HTML attribute values. Keys are "
        "the HTML attribute names as keywords e.g. `:placeholder`"]]]
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
      "                [input/field " [:br]
      "                  [input/input :password :pwd :model doc" [:br]
      "                     :attrs {:placeholder \"enter password\"}]" [:br]
      "                 :label \"Password:\"] " [:br]
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
                              :attrs {:placeholder "enter password"}]
                     :lable "Password:"]
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
       [:li [:strong ":icon-data"] " - an icon-data map or vector of icon-data "
        "maps. See " [:code "theophilusx.yorick.icon"] " for details"]
       [:li [:strong ":model"] " - a reagent atom to be used as the document "
        "model"]
       [:li [:strong ":change-fn"] " - a function of one argument which is "
        "called when the input data changes to update the data stored in the "
        "document model atom at the location specified by the " [:code "sid"]]
       [:li [:strong ":attrs"] " - a map of HTML attribute values. Keys are "
        "the HTML attribute names as keywords e.g. `:placeholder`"]]]
     :header {:title "input-field - an input field convenience component"}]]
   [:div.column
    [:pre
     [:code
      "[:p \"Example input-field component\"]" [:br]
      "(let [doc (r/atom {})" [:br]
      "      frm (fn []" [:br]
      "            [:<>" [:br]
      "              [input-field \"First Name\" :text " [:br]
      "                 :name.first :model doc]" [:br]
      "              [input-field \"Last Name\" :text " [:br]
      "                 :name.last :model doc]" [:br]
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
       [:li [:strong ":attrs"] " - a map of HTML attribute values. Keys are the "
        "HTML attribute names as a keyword e.g. `:required`"]]]
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
      "              [input/checkbox \"Check me!\" :form.checkme " [:br]
      "                 :model doc]" [:br]
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
       [:code ":control"] ", " [:code ":input"] " and " [:code ":label"]
       [:li [:strong ":attrs"] " - a map of HTML attribute values. Keys are the "
        "HTML attribute name as a keyword e.g. `:disabled`"]]]
     :header {:title "radio - a basic radio button component"}]]
   [:div.column
    [:pre
     [:code
      "[:p \"A basic radio button group\"]" [:br]
      "[input/radio :value [{:title \"Red\"}" [:br]
      "                     {:title \"Green\"}" [:br]
      "                     {:title \"Blue\"}]]" [:br]
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
        "component also supports optional keyword arguments"]
       [:ul
        [:li [:strong ":classes"] " - a map of strings or vectors of strings "
         "representing CSS class names. The supported keys in the map are "
         [:code ":field"] ", " [:code ":control"] " and " [:code ":button"] "."]
        [:li [:strong ":attrs"] " - a map of HTML attribute values. The keys "
         "are the HTML attribute names as keywords e.g. " [:code ":disabled"]]]]
      :header {:title "button - a basic button component"}]]
    [:div.column
     [:pre
      [:code
       "[:p \"A simple button example\"]" [:br]
       "[input/button \"Click Me!\" #(js/alert \"You clicked me\")]" [:br]
       "[:p \"A small button\"]" [:br]
       "[input/button \"Small Button\" " [:br]
       "  #(js/alert \"Clicked small button\")" [:br]
       "  :classes {:button \"is-small\"}]" [:br]
       "[:p \"A large button\"]" [:br]
       "[input/button \"Large Button\" " [:br]
       "  #(js/alert \"Clicked large button\")" [:br]
       "  :classes {:button \"is-large\"}]" [:br]
       "[:p \"A button with colour\"]" [:br]
       "[input/button \"Coloured\" " [:br]
       "   #(js/alert \"You clicked a coloured button\")" [:br]
       "   :classes {:button \"is-primary\"}]"]]
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
       [:li [:strong ":attrs"] " - a map of HTML attribute values. The keys are "
        "the HTML attribute name as a keyword e.g. " [:code ":placeholder"]]]]
     :header {:title "textarea - a basic text area component"}]]
   [:div.column
    [:pre
     [:code
      "[:p \"A basic textarea example\"]" [:br]
      "(let [doc (r/atom {})" [:br]
      "      frm (fn []" [:br]
      "            [:<>" [:br]
      "              [input/textarea \"Text Area Label\" :text.value " [:br]
      "                :attrs {:placeholder \"Write text here\"}" [:br]
      "                :model doc]" [:br]
      "              [:p (str \"Result: \" @doc)]])]" [:br]
      "  [fmt])"]]
    [:div.box
     [:p "A basic textarea example"]
     (let [doc (r/atom {})
           frm (fn []
                 [:<>
                  [i/textarea "Text Area Label" :text.value :model doc
                   :attrs {:placeholder "Write text here"}]
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
        "associate with the select box. See " [:strong "theophilusx/yorick/icon"]]
       [:li [:strong ":attrs"] " - a map of HTML attribute values. The keys are "
        "HTML attribute names as keywords e.g. " [:code ":disabled"]]]]
     :header {:title "select - a basic select box component"}]]
   [:div.column
    [:div.content
     [:pre
                [:code
        "[:p \"A simple select box\"]" [:br]
        "(let [doc (r/atom {})" [:br]
        "      frm (fn []" [:br]
        "            [:<>" [:br]
        "              [input/select :sel.value " [:br]
        "                 [(defoption \"One\")" [:br]
        "                  (defoption \"Two\")" [:br]
        "                  (defoption \"Three\")]" [:br]
        "                 :model doc]" [:br]
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
        [frm])]
     [:pre
      [:code
       "[:p \"A more complex select box example\"]" [:br]
       "(let [doc (r/atom {})" [:br]
       "      frm (fn []" [:br]
       "            [:<>" [:br]
       "              [input/select :pet.value " [:br]
       "                 [(input/defoption \"A Dog\" :value :dog)" [:br]
       "                  (input/defoption \"A Cat\" :value :cat)" [:br]
       "                  (input/defoption \"A Mouse\" :value :mouse" [:br]
       "                     :selected true)]" [:br]
       "                :model doc :rounded true]" [:br]
       "             [:p (str \"Result: \" @doc)]])]" [:br]
       "  [frm])"]]
     [:div.box
      [:p "A more complex select box example"]
      (let [doc (r/atom {})
            frm (fn []
                  [:<>
                   [i/select :pet.value [(i/defoption "A Dog" :value :dog)
                                         (i/defoption "A Cat" :value :cat)
                                         (i/defoption "A Mouse" :value :mouse
                                           :selected true)]
                    :model doc :rounded true]
                   [:p (str "Result: " @doc)]])]
        [frm])]]]])

(defn select-field-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "select-field"] " component is a convenience component "
       "which wraps a select box inside a " [:code "field"] " div and adds a "
       [:code "label"] " element. This helps to ensure appropriate spacing when "
       "rendering the select box. The " [:code "sid"] " argument is a storage "
       "identifier keyword. The " [:code "options"] " argument is a vector of "
       "option elements for the select box. (see " [:strong "defoption"]
       " for definition of select options. The component also supports a number "
       "optonal keyword arguments:"]
      [:ul
       [:li [:strong ":title"] " - a string used as the label to associate with "
        "the select box."]
       [:li [:strong ":classes"] " - a map of strings or vectors of strings "
        "representing CSS class names. The following keys are supported - "
        [:code ":field, :title, and :select"]]
       [:li [:strong ":multiple"] " - boolean. If true, allow selection of "
        "multiple options"]
       [:li [:strong ":rounded"] " - Boolean. If true, use rounded corners "
        "for the select box"]
       [:li [:strong ":select-size"] " - Keyword. Set the size of the select "
        "box. Possible values are " [:code ":small, :medium and :large"]]
       [:li [:strong ":icon-data"] " - an icon data map. (see "
        [:strong "theophilusx/yorick/icon"] " for details. Adds an icon to the "
        "select box."]
       [:li [:strong ":model"] " - a reagent atom used as the document model "
        "store."]
       [:li [:strong ":change-fn"] " - a function of one argument called when "
        "the input data changes. Argument is the new data. Used to update the "
        "document model store."]
       [:li [:strong ":attrs"] " - a map of HTML attribute values. The keys are "
        "HTML attribute names as keywords e.g. " [:code ":disabled"]]]]
     :header {:title "select-field - a select field component"}]]
   [:div.column
    [:pre
     [:code
      "[:p \"A select-field example\"" [:br]
      "(let [doc (r/atom {})" [:br]
      "      frm (fn []" [:br]
      "            [:<>" [:br]
      "              [input/select-field :car.value " [:br]
      "                 [(input/defoption \"Holden\")" [:br]
      "                  (input/defoption \"Audi\")" [:br]
      "                  (input/defoption \"Saab\")]" [:br]
      "                :title \"My Car\" :select-size :large " [:br]
      "                :model doc]" [:br]
      "              [:p (str \"Result: \" @doc)]])]" [:br]
      "  [frm])"]]
    [:div.box
     [:p "A select-field example"]
     (let [doc (r/atom {})
           frm (fn []
                 [:<>
                  [i/select-field :car.value [(i/defoption "Holden")
                                              (i/defoption "Audi")
                                              (i/defoption "Saab")]
                   :title "My Car" :select-size :large :model doc
                   :icon-data {:name "fa-car" :position :left}]
                  [:p (str "Result: " @doc)]])]
       [frm])]]])

(defn file-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "file"] " component provides a file select box. The "
       "mandatory argument " [:code "sid"] " is a storage identifier keyword "
       "used to determine the storage location for entered data. The component "
       "also supports a number of optional keyword arguments"]
      [:ul
       [:li [:strong ":model"] " - a reagent atom to be used as the document "
        "model store"]
       [:li [:strong ":change-fn"] " - a function of one argument which is "
        "called when the input data changes. The argument is the event object "
        "associated with the component. The function should test for the "
        [:code ":action"] " keyword argument and if found, execute it with the "
        "File object of the selected file if one has been selected."]
       [:li [:strong ":action"] " - a function of 1 argument which is to be "
        "called if a file has been selected. This function can be used to "
        "perform some action if a file is selected."]
       [:li [:strong ":classes"] " - a map of strings or vectors of strings "
        "representing CSS class names. Supported keys include "
        [:code ":field, :file, :label, :input and :file-cta"]]
       [:li [:strong ":label"] " - a string to be used as the label to "
        "associate with the file select box"]
       [:li [:strong ":right"] " - a boolean which if true, will cause the "
        "label to be placed on the right of the select box."]
       [:li [:strong ":fullwidth"] " - if true, make the file select box the "
        "full width of the enclosing container."]
       [:li [:strong ":boxed"] " - enclose the component inside a box"]
       [:li [:strong ":size"] " - set the size of the file upload box. "
        "Available values are " [:code ":small, :medium and :large"]]
       [:li [:strong ":position"] " - set the position of the file upload "
        "box within the enclosing container. Possible values are "
        [:code ":center and :right"]]]]
     :header {:title "file - file selector box component"}]]
   [:div.column
    [:pre
     [:code
      "[:p \"A basic file selector example\"]" [:br]
      "(let [doc (r/atom {})" [:br]
      "      frm (fn []" [:br]
      "            [:<>" [:br]
      "              [input/file :file1.value :model doc" [:br]
      "                :action #(js/alert (str \"You selected \" %))]" [:br]
      "              [:p (str \"Result: \" @doc)]])]" [:br]
      "  [frm])"]]
    [:div.box
     [:p "A basic file selector example"]
     (let [doc (r/atom {})
           frm (fn []
                 [:<>
                  [i/file :file1.value :model doc
                   :action #(js/alert (str "You selected " (.-name %)))]
                  [:p (str "Result: " @doc)]])]
       [frm])]
    [:pre
     [:code
      "[:p \"A more complex file selector example\"]" [:br]
      "(let [doc (r/atom {})" [:br]
      "      frm (fn []" [:br]
      "            [:<>" [:br]
      "              [input/file :file2.value :model doc " [:br]
      "                :label \"My File\" :boxed true" [:br]
      "                :action #(js/alert (str \"You selected \" %))]" [:br]
      "              [:p (str \"Result: \" @doc)]])]" [:br]
      "  [frm])"]]
    [:div.box
     [:p "A more complex file selector example"]
     (let [doc (r/atom {})
           frm (fn []
                 [:<>
                  [i/file :file2.value :model doc :label "My File"
                   :boxed true :action #(js/alert (str "You selected "
                                                       (.-name %)))]
                  [:p (str "Result: " @doc)]])]
       [frm])]]])

(defn search-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "search"] " component is a simple search box. The "
       [:code "action"] " argument is a function of 1 argument which is called "
       "when the user clicks on the search button. The argument is the string "
       "entered into the search box by the user. The component also supports "
       "a number of optional keyword arguments:"]
      [:ul
       [:li [:strong ":classes"] " - a map of strings or vectors of strings "
        "representing CSS class names. Supported keys are "
        [:code ":field, :input"] " and " [:code ":button"]]
       [:li [:strong ":icon-data"] " - an icon data map. See "
        [:code "theophilusx/yorick/icon"] " for details regarding the icon data "
        "map format"]
       [:li [:strong ":button-text"] " - the text to be used on the search "
        "button. Will default to " [:code "Search"] " if not specified. Use "
        [:code "nil"] " as the value to have no text and just an icon "
        "(specifying the icon with " [:code ":icon-data"] ")"]
       [:li [:strong ":attrs"] " - a map of HTML attribute values. The keys are "
        "HTML attribute names as keywords e.g. " [:code ":disabled"]]]]
     :header {:title "search - a basic search box component"}]]
   [:div.column
    [:pre
     [:code
      "[:p \"A simple search box exmaple\"]" [:br]
      "[input/search #(js/alert (str \"You are searching for \" %))]"]]
    [:div.box
     [:p "A simple search box example"]
     [i/search #(js/alert (str "You are searching for " %))]]
    [:pre
     [:code
      "[:p \"Search box with placeholder, icon and colour\"]" [:br]
      "[input/search #(js/alert (str \"You are searching for \" %))" [:br]
      "  :placeholder \"enter search terms\" :button-text \"\"" [:br]
      "  :icon-data (icons/deficon \"fas fa-search\")" [:br]
      "  :classes {:button \"has-background-link has-text-white\"}]"]]
    [:div.box
     [:p "Search box with placeholder, icon and colour"]
     [i/search #(js/alert (str "You are searching for " %))
      :placeholder "enter search terms" :button-text ""
      :icon-data (icons/deficon "fas fa-search")
      :classes {:button "has-background-link has-text-white"}]]]])

(defn range-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "range-field"] " component provides a basic range input "
       "field with a minimum and maximum range values. The " [:code "sid"]
       " argument is a storage identifier keyword. The " [:code "min"]
       " and " [:code "max"] " arguments set the minimum and maximum values "
       "for the range. The component also supports a number of optional "
       "keyword arguments:"]
      [:ul
       [:li [:strong ":model"] " a reagent atom to use as the document model "
        "store"]
       [:li [:strong ":change-fn"] " a function of one argument to use as the "
        "function to update the value in the document model store. The argument "
        "is the new value entered by the user"]
       [:li [:strong ":value"] " a default initial value for the range"]
       [:li [:strong ":label"] " a text label to associate with the range field"]
       [:li [:strong ":classes"] " a map of strings or vectors of strings "
        "representing CSS class names. The allowed keys are "
        [:code ":field, :input"] " and " [:code ":label"]]
       [:li [:strong ":step"] " set the step size for the range. Defaults to "
        "1 if not supplied"]
       [:li [:strong ":attrs"] " - a map of HTML attribute values. The keys are "
        "HTML attribute names as keywords e.g. " [:code ":disabled"]]]]
     :header {:title "range-field - a basic range input field"}]]
   [:div.column
    [:pre
     [:code
      "[:p \"A simple range example\"]" [:br]
      "(let [doc (r/atom {})" [:br]
      "      frm (fn []" [:br]
      "            [:<>" [:br]
      "              [input/range-field :range1.value 0 100 " [:br]
      "                 :model doc]" [:br]
      "              [:p (str \"Result: \" @doc)]])]" [:br]
      "  [frm])"]]
    [:div.box
     [:p "A simple range example"]
     (let [doc (r/atom {})
           frm (fn []
                 [:<>
                  [i/range-field :range1.value 0 100 :model doc]
                  [:p (str "Result: " @doc)]])]
       [frm])]
    [:pre
     [:code
      "[:p \"Range with label, step and default value\"]" [:br]
      "(let [doc (r/atom {})" [:br]
      "      frm (fn []" [:br]
      "            [:<>" [:br]
      "              [input/range-field :range2.value 0 1000 " [:br]
      "                 :model doc :value 100 :step 10" [:br]
      "                 :label \"Quantity\"]" [:br]
      "              [:p (str \"Result: \" @doc)]])]" [:br]
      "  [frm])"]]
    [:div.box
     [:p "Range with label, step and default value"]
     (let [doc (r/atom {})
           frm (fn []
                 [:<>
                  [i/range-field :range2.value 0 1000 :model doc
                   :value 100 :step 10 :label "Quantity"]
                  [:p (str "Result: " @doc)]])]
       [frm])]]])

(defn number-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "number"] " component is a base number input "
       "component. It is typically used inside a field component e.g. "
       [:code "field"] " or " [:code "horizontal-field"] ". The " [:code "sid"]
       " argument is a storage identifier keyword which determines where the "
       "input value is stored within the document model store. The component "
       "also supports a number of optional keyword arguments:"]
      [:ul
       [:li [:strong ":model"] " - a reagent atom to use as the document "
        "model store"]
       [:li [:strong ":change-fn"] " - a function of 1 argument called when "
        "the input data changes. Used to update the document model store"]
       [:li [:strong ":value"] " - a default value used to initialise the "
        "component"]
       [:li [:strong ":min"] " - set  the minimum acceptable input value"]
       [:li [:strong ":max"] " - set the maximum acceptable input value"]
       [:li [:strong ":step"] " - set the step value for input numbers"]
       [:li [:strong ":classes"] " - a map of strings or vectors of strings "
        "representing CSS class names. Allowed keys are " [:code ":control"]
        " and " [:code ":input"]]
       [:li [:strong ":attrs"] " - a map of HTML attribute values. The keys "
        "are HTML attribute names as keywords e.g. " [:code ":id"]]]]
     :header {:title "number-input - a basic number input component"}]]
   [:div.column
    [:pre
     [:code
      "[:p \"A number input component example\"]" [:br]
      "(let [doc (r/atom {})" [:br]
      "      frm (fn []" [:br]
      "            [:<>" [:br]
      "              [input/field [input/number :num1.value " [:br]
      "                 :model doc]]" [:br]
      "              [:p (str \"Result: \" @doc)]])]" [:br]
      "  [frm])"]]
    [:div.box
     [:p "A number input component"]
     (let [doc (r/atom {})
           frm (fn []
                 [:<>
                  [i/field [i/number :num1.value :model doc]]
                  [:p (str "Result: " @doc)]])]
       [frm])]]])

(defn number-field-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "number-field"] " component is a basic number input "
       "field. It is essentially the " [:code "number-input"] " component "
       "wrapped in a " [:code "field"] " component. The " [:code "sid"]
       " argument is a storage identifier keyword used to determine where to "
       "store input data within the document model store. The component "
       "supports a number of optional keyword arguments:"]
      [:ul
       [:li [:strong ":model"] " - a reagent atom to use as the document model "
        "store."]
       [:li [:strong ":change-fn"] " - a function of one argument which is "
        "called when the input data changes. The argument is the new input data. "
        "Used to update the value in the document model store."]
       [:li [:strong ":value"] " a default value used to initialise the "
        "component"]
       [:li [:strong ":min"] " - set a minimum acceptable input value"]
       [:li [:strong ":max"] " - set a maximum acceptable input value"]
       [:li [:strong ":step"] " - set the increment/decrement step size"]
       [:li [:strong ":classes"] " - a map of strings or vectors of strings "
        "representing CSS class names. Supported keys are "
        [:code ":field, :label, :control"] " and " [:code ":input"]]
       [:li [:strong ":label"] " - a string to use as the field label"]
       [:li [:strong ":attrs"] " - a map of HTML attribute values. Keys are "
        "HTML attribute names as keywords e.g. " [:code ":id"]]]]
     :header {:title "number-field - a basic number input field"}]]
   [:div.column
    [:pre
     [:code
      "[:p \"A number input field example\"]" [:br]
      "(let [doc (r/atom {})" [:br]
      "      frm (fn []" [:br]
      "            [:<>" [:br]
      "              [input/number-field :num2.value :min 100 :max 1000" [:br]
      "                 :step 10 :model doc :label \"Your Number\"" [:br]
      "                 :value 500 :attrs {:maxLength \"4\"}]" [:br]
      "             [:p (str \"Result: \" @doc)]])]" [:br]
      "  [frm])"]]
    [:div.box
     [:p "A number input field example"]
     (let [doc (r/atom {})
           frm (fn []
                 [:<>
                  [i/number-field :num2.value :min 100 :max 1000
                   :step 10 :model doc :label "Your Number"
                   :value 500 :attrs {:maxLength "4"}]
                  [:p (str "Result: " @doc)]])]
       [frm])]]])

(defn input-page []
  [:<>
   [:div.content
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
     [:em "storage identifiers"] " or " [:code "sid"] " is introduced. A "
     [:code "sid"] " is a " [:code "keyword"] " which specifies the path into a "
     "document model map atom where the data should be stored. To support key "
     "hierarchies in the map, the " [:code "sid"] " uses a period (.) as a path "
     "separator. For example, a sid with the value " [:code ":value"] " will "
     "store the input in the document model map under the key " [:code ":value"]
     " and a sid with the value " [:code ":my-form.value"] " will store the "
     "input in the document model map under the key " [:code ":value"] " which is "
     "under the key " [:code ":my-form"] "."]
    [:p
     "Most of the components in this namespace which accept user input have a "
     "local document model reagent atom which will be used if none is specified "
     "with the " [:code ":model"] " keyword argument. You can also specify a change function "
     "of one argument for more complex storage requirements. The " [:code ":change-fn"] 
     " optional argument allows you to specify the function that will be called "
     "whenever the input data changes. The argument represents the new input "
     "value."]]
   [:hr]
   [t/tab :ui.tabs.input-page [(t/deftab "field" :id :field)
                               (t/deftab "input" :id :input)
                               (t/deftab "checkbox" :id :checkbox)
                               (t/deftab "radio" :id :radio)
                               (t/deftab "button" :id :button)
                               (t/deftab "editable-field" :id :efield)
                               (t/deftab "textarea" :id :textarea)
                               (t/deftab "select" :id :select)
                               (t/deftab "file" :id :file)
                               (t/deftab "search" :id :search)
                               (t/deftab "range" :id :range)
                               (t/deftab "number" :id :number)]
    :size :medium :position :center]
   (case (get-in global-state (spath :ui.tabs.input-page))
     :field [:div
             [field-component]
             [horizontal-field-component]]
     :input [:<>
             [input-component]
             [input-field-component]]
     :checkbox [checkbox-component]
     :radio [radio-component]
     :button [button-component]
     :efield [editable-field-component]
     :textarea [textarea-component]
     :select [:<>
              [select-component]
              [select-field-component]]
     :file [file-component]
     :search [search-component]
     :range [range-component]
     :number [:<>
              [number-component]
              [number-field-component]]
     [field-component])])

