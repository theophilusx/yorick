(ns yorick-demo.inputs
  (:require [theophilusx.yorick.input :as i]
            [reagent.core :as r]
            [theophilusx.yorick.icon :as icons]
            [theophilusx.yorick.tab :as t]
            [theophilusx.yorick.store :refer [cursor]]
            [theophilusx.yorick.utils :refer [spath]]
            [yorick-demo.comp :refer [doc-comp doc-args example]]))

(def field-eg "
[:p \"This is a basic field example\"]
[input/field [:p \"Field contents\"]]
[:p \"This is a field with a label\"]
[:input/field [:p \"Field contents\"] 
  :label \"field label\"]
[:p \"This is a basic field with label and additional classes\"]
[input/field [:p \"Field contents\"] :label \"field label\"
  :classes {:field \"has-background-danger\" 
  :label \"has-text-warning\"}]
")

(defn field-component []
  [:div.columns
   [:div.column
    [doc-comp "field - a general purpose container for input fields"
     [:p "The " [:strong "field"] " component is a general purpose component 
       used as a container for other input components."]
     [["body"
       "the contents of the field. This is typically an input component or a
<div> element with the 'control' CSS class."]]
     [[":label" "A text label to associate with the input component"]
      [":classes" "A map of CSS class names to add. Supported keys are :field and :label.
Values can be either a string or a vector of strings specifying CSS class names"]]]]
   [:div.column
    [example field-eg [:<> [:p "This is a basic field example"]
                       [i/field [:p "Field contents"]]
                       [:p "This is a field with a label"]
                       [i/field [:p "Field contents"] :label "field label"]
                       [:p "This is a basic field with label and additional classes"]
                       [i/field
                        [:p "Field contents"]
                        :label "field label"
                        :classes {:field "has-background-danger" :label "has-text-warning"}]]]]])

(def horiz-eg1 "
[:p \"A basic horizontal field example\"]
[input/horizontal-field \"Field Label\" 
  [:p \"field contents\"]]
[:p \"A basic horizontal field with additional CSS classes\"]
[input/horizontal-field \"Field Label\" [:p \"field contents\"]
  :classes {:field \"has-background-danger\"
            :label \"has-text-warning\"
            :body \"has-background-primary\"}]
")

(defn horizontal-field-component []
  [:div.columns
   [:div.column
    [doc-comp "horizontal-field - a horizontal field container"
     [:p "The " [:strong "horizontal-field"] " component is similar to the "
      [:strong "field"] " component, except that instead of putting label "
      "and field elements vertically, they are placed horizontally."]
     [["label" "Text to be used as the field label"]
      ["body" "Contents for the field"]]
     [[":classes" "A map specifying CSS classes to add. The map supports the keys :field and :label. The value
is either a string or a vector of stirings which specify CSS class names"]]]]
   [:div.column
    [example horiz-eg1 [:<>
                        [:p "A basic horizontal field exmaple"]
                        [i/horizontal-field "Field Label" [:p "field contents"]]
                        [:p "A basic horizontal field with additional CSS classes"]
                        [i/horizontal-field "Field Label" [:p "field contents"]
                         :classes {:field "has-background-danger"
                                   :label "has-text-warning"
                                   :body "has-background-primary"}]]]]])

(def input-eg1 "
[:p \"A basic text input field\"]
[input/field [input/input :text :value]]
(let [doc (reagent/atom {})]
      eg-fn (fn []
              [:<>
                [:p \"Basic password input field example\"]
                [input/field 
                  [input/input :password :pwd :store doc
                     :attrs {:placeholder \"enter password\"}]
                 :label \"Password:\"] 
                [:p (str \"Value entered: \" (:pwd @doc))]])]
  [eg-fn])
")

(defn input-component []
  [:div.columns
   [:div.column
    [doc-comp "input - general purpose input component"
     [:p "A basic text input component. This component is typically wrapped 
       inside a " [:code "field"] " or " [:code "horizontal-field"] " component."]
     [["type"
       "Specifies the input type. Supported types are keywords derived
from HTML5 input types e.g. :text"]
      ["sid" "A storage identifier."]]
     [[":store" "A reagent atom used to stroe the state (input) for the field"]
      [":change-fn" "A change function which is called when the content of the input changes."]
      [":classes" "Map of CSS class names. Supported keys are :control and :input.
Values can be either a stgring or a vector of strings specifying CSS class names."]
      [":icon-data" "An icon data map specifying an icon to add to the input field."]
      [":attrs" "A map of HTML attributes. Each key is a kewordized name of an HTML attribute and the
value is the value to set that attribute to."]]]]
   [:div.column
    [example input-eg1 [:<>
                        [:p "A basic text input field"]
                        [i/field [i/input :text :value]]
                        (let [doc (r/atom {})
                              eg-fn (fn []
                                      [:<>
                                       [:p "Basic password input field example"]
                                       [i/field [i/input :password :pwd :store doc
                                                 :attrs {:placeholder "enter password"}]
                                        :label "Password:"]
                                       [:p (str "Value entered: " (:pwd @doc))]])]
                          [eg-fn])]]]])

(def input-field-eg "
[:p \"Example input-field component\"]
(let [doc (r/atom {})
      frm (fn []
            [:<>
              [input-field \"First Name\" :text 
                 :name.first :model doc]
              [input-field \"Last Name\" :text 
                 :name.last :model doc]
              [:p (str \"Result: \" @doc)])]]
  [frm])")

(defn input-field-component []
  [:div.columns
   [:div.column.
    [doc-comp "input-field - an input field convenience component"
     [:p "The " [:strong "inut-field"] " component is a convenience component "
      "which combines the " [:strong "field"] " and " [:strong "input"]
      " components. "]
     [["label" "A text label to associate with the input field"]
      ["type" "The type of the input field. A keywrodised HTML input type e.g. :email"]
      ["sid" "A storage identifier which specifies the key to use when saving the input into the store"]]
     [[":classes" "A map of CSS class names. Supported keys are :field, :label and :input. Values
are either strings or vectors of strings specifying CSS class names"]
      [":icon-data" "An icon data map specifying an icon to add to the input field."]
      [":stroe" "A reagent atom used as the data store for this input."]
      [":change-fn" "A function of one argument which is executed when the content of the input changes.
Argument is the new input."]
      [":attrs" "Map of HTML attributes. The keys are keywordized HTML attribute names"]]]]
   [:div.column
    [example input-field-eg [:<>
                             [:p "Example input-field component"]
                             (let [doc (r/atom {})
                                   frm (fn []
                                         [:<>
                                          [i/input-field "First Name" :text :name.first :model doc]
                                          [i/input-field "Last Name" :text :name.last :model doc]
                                          [:p (str "Result: " @doc)]])]
                               [frm])]]]])

(def checkbox-eg "
[:p \"A basic checkbox example\"]
[input/checkbox \"I am a checkbox\" :value]
(let [doc (r/atom {})
      frm (fn []
            [:<>
              [:p \"Another checkbox example\"]
              [input/checkbox \"Check me!\" :form.checkme 
                 :store doc]
              [:p (str \"Result: \" @doc)]])]
  [frm])")

(defn checkbox-component []
  [:div.columns
   [:div.column
    [doc-comp "checkbox - a basic checkbox component"
     [:p "The " [:strong "checkbox"] " component provides a basic checkbox "
      "input component."]
     [["label" "A text label to associate with the checkbox input field."]
      ["sid" "A storage identifier which provides the path into the store where input will be recorded"]]
     [[":store" "A reagent atom which is used to store the state of the checkbox"]
      [":change-fn" "A function of one argument which is evaluated when the state of the checkbox chagnes"]
      [":classes" "A map of CSS class names. Supported keys are :field, :control, :label and input.
The values associated with each key can be a string or a vector of strings which specify CSS class names."]
      [":checked?" "A boolean which is true if this HTML checked attribute shold be set for this checkbox."]
      [":attrs" "A map of HTML attributes where keys are keywordized HTML attribute names."]]]]
   [:div.column
    [example checkbox-eg [:<>
                          [:p "A basic checkbox example"]
                          [i/checkbox "I am a checkbox" :value]
                          (let [doc (r/atom {})
                                frm (fn []
                                      [:<>
                                       [:p "another checkbox example"]
                                       [i/checkbox "Check me!" :form.checkme :store doc]
                                       [:p (str "Result: " @doc)]])]
                            [frm])]]]])

(def radio-ex "
  [:p \"A basic radio button group\"]
     [i/radio :value [{:title \"Red\"}
                      {:title \"Green\"}
                      {:title \"Blue\"}]]
     (let [doc (r/atom {})
           frm (fn []
                 [:<>
                  [:p \"Another radio button example\"]
                  [i/radio :pet.type [{:title \"Dog\"
                                       :value :dog
                                       :checked true}
                                      {:title \"Cat\"
                                       :value :cat}
                                      {:title \"Diamond Python\"
                                       :value :snake}]
                   :store doc]
                  [:p (str \"Result: \" @doc)]])]
       [frm])")

(defn radio-component []
  [:div.columns
   [:div.column
    [doc-comp "radio - a basic radio button component"
     [:<>
      [:p "The " [:strong "radio button"] " component provides a basic radio button "
       "style input component. The radio buttons are defined by maps with the following keys:"]
      [doc-args [[":title" "The text or label to associate with a button"]
                 [":value" "The value to be recorded in the state atom when the button is selected.
If not set, default to using the :title value converted to a keyword."]
                 [":checked?" "If true, set this button to checked by default when first rendered."]]]]
     [["sid" "A storage identifier used as the key for storing radio button state"]
      ["buttons" "A vector of maps defining the radio buttons."]]
     [[":store" "Specify a reagent atom to use as the store for tracking state. Defaults
to a private store if none specified."]
      [":change-fn" "A function of one argument called when the state of the component changes.
Default function updates the store with the new state."]
      [":classes" "A map of CSS class names. Supported keys are :control, :input and :label.
Value can be a string or vector of strings which specify CSS class names."]
      [":attrs" "Map of HTML attributes. The keys are keywordized versions of HTML attribute names."]]]]
   [:div.column
    [example radio-ex [:<>
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
                                     :store doc]
                                    [:p (str "Result: " @doc)]])]
                         [frm])]]]])

(def button-eg "
  [:p \"A simple button example\"]
      [i/button \"Click Me!\" #(js/alert \"You clicked me\")]
      [:p \"A small button\"]
      [i/button \"Small Button\" #(js/alert \"Clicked small button\")
        :classes {:button \"is-small\"}]
      [:p \"A large button\"]
      [i/button \"Large Button\" #(js/alert \"Clicked large button\")
       :classes {:button \"is-large\"}]
      [:p \"A button with colour\"]
      [i/button \"Coloured\" #(js/alert \"You clicked a coloured button\")
       :classes {:button \"is-primary\"}]")

(defn button-component []
  [:<>
   [:div.columns
    [:div.column
     [doc-comp "button - a basic button component"
      [:<>
       [:p "The " [:strong "button"] " component is a basic HTML button component "
        "This component wraps an HTML button inside a " [:code "control"]
        " and " [:code "field"] " div elements to support spacing and "
        "alignment."]
       [:p "The optional " [:strong ":classes"] " argument supports the following keys. The "
        "allowed value for each key is either a string or a vector of strings which specify "
        "CSS class names."]
       [doc-args [[":field" "CSS classes to add to the field element."]
                  [":control" "CSS classes to add to the control element."]
                  [":button" "CSS classes to add to the button element."]]]]
      [["title" "The text to be used in the button"]
       ["action" "A zero argument function which will be executed when the button is clicked."]]
      [[":classes" "A map of CSS class names to add to the elements that make up this
component (see above)"]
       [":attrs" "A map of HTML attributes to add to the button element. Map keys are
keywordized HTML attribute names e.g. :id"]]]]
    [:div.column
     [example button-eg [:<>
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
                          :classes {:button "is-primary"}]]]]]])

(def edit-field-eg "
  (let [doc (r/atom {:name {:first \"John\" :last \"Doe\"}})
           frm (fn []
                 [:<>
                  [:p \"Editable field example\"]
                  [i/editable-field :text doc :name.first
                   :label \"First Name:\"]
                  [i/editable-field :text doc :name.last
                   :label \"Last Name:\"]
                  [:p (str \"Result: \" @doc)]])]
       [frm])")

(defn editable-field-component []
  [:div.columns
   [:div.column
    [doc-comp "editable-field - a editable field component"
     [:<>
      [:p
       "The " [:strong "editable-field"] " component is a utility component "
       "which displays a value followed by a 'pencil' icon, which when clicked "
       "allows for editing of the value. When editing the value, buttons are "
       "provided to save or cancel the edit."]
      [:p
       "The component supports an optional " [:strong ":clases"] " keywrod argument which provides "
       "a mechanism for setting CSS classes on the elements used to build this component. The value "
       "associated with each key can be a string or a vector of strings. Each string specifies a CSS "
       "class name. The supported keys are:"]
      [doc-args
       [[":edit-input" "CSS classes to associate with the edit input element."]
        [":display-input" "CSS classes to associatge with the display input element."]
        [":save-btn-field" "CSS classes to add to the save button field element."]
        [":save-btn-control" "CSS classes to add to the save button control element."]
        [":save-btn-button" "CSS classes to add to the save button button element."]
        [":cancel-btn-field" "CSS classes to add to the cancel button field element"]
        [":cancel-btn-control" "CSS classes to add to the cancel button contgrol element."]
        [":cancel-btn-button" "CSS classes to add to the cancel button button element."]
        [":label" "CSS classes to add to the label element"]
        [":field-edit" "CSS classes to add to the field element when editing the value."]
        [":field-edit-body" "CSS classes to add to the body of the edit field when editing the value."]
        [":field-display" "CSS classes to add to the field element when display the value."]
        [":field-display-body" "CSS classes to add to the field body element when display the value."]]]]
     [["type" "A keyword which specifies the type of the input field e.g. :text, :password etc"]
      ["store" "A reagent atom where the original/master version of the data is located."]
      ["sid" "A storage identifier which identifies the location in the store the value is located"]]
     [[":lable" "A string used to label the input field."]
      [":classes" "A map of CSS class names to add to HTML elements which make up the component.
See above for description of supported keys."]]]]
   [:div.column
    [example edit-field-eg (let [doc (r/atom {:name {:first "John" :last "Doe"}})
                                 frm (fn []
                                       [:<>
                                        [:p "Editable field example"]
                                        [i/editable-field :text doc :name.first
                                         :label "First Name:"]
                                        [i/editable-field :text doc :name.last
                                         :label "Last Name:"]
                                        [:p (str "Result: " @doc)]])]
                             [frm])]]])

(def textarea-eg "
  [:p \"A basic textarea example\"]
     (let [doc (r/atom {})
           frm (fn []
                 [:<>
                  [i/textarea \"Text Area Label\" :text.value :store doc
                   :attrs {:placeholder \"Write text here\"}]
                  [:p (str \"Result: \" @doc)]])]
       [frm])")

(defn textarea-component []
  [:div.columns
   [:div.column
    [doc-comp "textarea - a basic text area component"
     [:p
       "The " [:strong "textarea"] " component provides a basic text area "
      "component for free-form text input. The input is stored in the defualt "
      "store or the store specified using a keywrod argument. The " [:code "sid"]
      " argument determines the location within the store where the input is saved."]
     [["label" "A text label to associate with the input field."]
      ["sid" "A storage identifier which specifies the location where the input data will be stored"]]
     [[":store" "A reagent atom used as the store for the component's input state. Will default to the global default store if not specified."]
      [":change-fn" "A funciton of one argument which is evaluated when the contents of the input field change. Typically used to save input to a store."]
      [":classes" "A string or vector of strings specifying CSS class names. Supported keys are :field, :label and :textarea."]
      [":atrs" "A map of HTML attributes. Keys are keywordized HTML attribute names."]]]]
   [:div.column
    [example textarea-eg [:<>
                          [:p "A basic textarea example"]
                          (let [doc (r/atom {})
                                frm (fn []
                                      [:<>
                                       [i/textarea "Text Area label" :text.value :store doc
                                        :attrs {:placeholder "Write text here"}]
                                       [:p (str "Result: " @doc)]])]
                            [frm])]]]])

(def select-eg1 "
  [:p \"A simple select box\"]
      (let [doc (r/atom {})
            frm (fn []
                  [:<>
                   [i/select :sel.value [(i/defoption \"One\")
                                         (i/defoption \"Two\")
                                         (i/defoption \"Three\")]
                    :store doc]
                   [:p (str \"Result: \" @doc)]])]
        [frm])")

(def select-eg2 "
  [:p \"A more complex select box example\"]
      (let [doc (r/atom {})
            frm (fn []
                  [:<>
                   [i/select :pet.value [(i/defoption \"A Dog\" :value :dog)
                                         (i/defoption \"A Cat\" :value :cat)
                                         (i/defoption \"A Mouse\" :value :mouse
                                           :selected? true)]
                    :store doc :rounded? true]
                   [:p (str \"Result: \" @doc)]])]
        [frm])")

(defn select-component []
  [:div.columns
   [:div.column
    [doc-comp "defoption - define an option for a selection list"
     [:p "The " [:strong "defoption"] " function is a convenience function which can help with defining "
      "the option maps used to define options for a selection list component. The function "
      "returns an option map. A vector of option maps is used to define the list of optons available in a "
      "selection list component."]
     [["title" "The title to use for the option."]]
     [[":value" "A value to use when this option is selected. Defaults to :label if :value is not supplied.
Defualts to title if :label and :value are not supplied. Defualts to a gensym starting with `option-` if neither title, :value or :label are not supplied."]
      [":option-class" "A string or vector of strings specifying CSS class names."]
      [":disabled?" "If true, this option will be disabled."]
      [":label" "A short form of the title which can be used internally."]
      [":selected?" "When true, this otion is marked as selected by default."]]]
    [doc-comp "select - a basic select box component"
     [:p "The " [:strong "select"] " component provides a basic selection list component where the "
      "user can select from a number of options in a drop-down selection box. The options are defined "
      "using the " [:code "defoptions"] " function."]
     [["sid" "A storage identifier used to determine where the selected option(s) state will be reocrded."]
      ["options" "A vector of option maps. The option maps are defined using the defoption function."]]
     [[":store" "A reagent atom to be used as the compnent state store. Defaults to the default global store if not specified."]
      [":change-fn" "A functon of one argument which is called when the state of the selection component changes. Argument is the changed state."]
      [":select-class" "A string or vector of strings specifying CSS class names to be added to the select element."]
      [":multiple?" "If true, allow multiple options to be selected at one time."]
      [":rounded?" "If true, use rounded corners on selection list box"]
      [":select-size" "Size of the selection box. Can be :large, :medium or :small"]
      [":icon-data" "An icon data map defining an icon to add to the selection box."]
      [":size" "Number of options to display in the selection box."]
      [":attrs" "A map of HTML attributes. Keys are HTML attribute names converted into keywords."]]]]
   [:div.column
    [example select-eg1 [:<>
                         [:p "A simple select box"]
                         (let [doc (r/atom {})
                               frm (fn []
                                     [:<>
                                      [i/select :sel.value [(i/defoption "One")
                                                            (i/defoption "Two")
                                                            (i/defoption "Three")]
                                       :store doc]
                                      [:p (str "Result: " @doc)]])]
                           [frm])]]
    [example select-eg2 [:<>
                         [:p "A more complex select box example"]
                         (let [doc (r/atom {})
                               frm (fn []
                                     [:<>
                                      [i/select :pet.value [(i/defoption "A Dog" :value :dog)
                                                            (i/defoption "A Cat" :value :cat)
                                                            (i/defoption "A Mouse" :value :mouse
                                                              :selected? true)]
                                       :store doc :rounded true]
                   [:p (str "Result: " @doc)]])]
        [frm])]]]])

(def select-field-eg "
  [:p \"A select-field example\"]
  (let [doc (r/atom {})
           frm (fn []
                 [:<>
                  [i/select-field :car.value [(i/defoption \"Holden\")
                                              (i/defoption \"Audi\")
                                              (i/defoption \"Saab\")]
                   :title \"My Car\" :select-size :large :store doc
                   :icon-data {:name \"fa-car\" :position :left}]
                  [:p (str \"Result: \" @doc)]])]
       [frm])")

(defn select-field-component []
  [:div.columns
   [:div.column
    [doc-comp "select-field - a select field component"
     [:p
       "The " [:strong "select-field"] " component is a convenience component "
       "which wraps a select box inside a " [:code "field"] " div and adds a "
       [:code "label"] " element. This helps to ensure appropriate spacing when "
      "rendering the select box."]
     [["sid" "A storage identifiier which specifies the storage location for this state of this component."]
      ["options" "A vector of option maps which define the selection list. See the defoption function for details on map format."]]
     [[":title" "A string title to use as the label for this input component."]
      [":classes" "A map of strings or vector of strings which specify CSS class names. The supported keys for the map are :field, :title and :select"]
      [":multiple?" "A boolean. If true, allow selection of multiple entries."]
      [":rounded?" "Use rounded corners in select box."]
      [":select-size" "Set the size of the select box. Possible values are :small, :medium and :large"]
      [":icon-data" "An icon data map which is used to set an icon as part of the select box."]
      [":store" "A reagent atom used as the store for the component state. If not specified, default to the global store."]
      [":change-fn" "A function of one argument called when the state of the component changes. The argument is the changed state."]
      [":attrs" "A map of HTML attribute values. Keys are keywordized HTML attribute names."]]]]
   [:div.column
    [example select-field-eg [:<>
                              [:p "A select-field example"]
                              (let [doc (r/atom {})
                                    frm (fn []
                                          [:<>
                                           [i/select-field :car.value [(i/defoption "Holden")
                                                                       (i/defoption "Audi")
                                                                       (i/defoption "Saab")]
                                            :title "My Car" :select-size :large :store doc
                                            :icon-data {:name "fa-car" :position :left}]
                                           [:p (str "Result: " @doc)]])]
                                [frm])]]]])

(def file-eg "
  [:p \"A basic file selector example\"]
  (let [doc (r/atom {})
        frm (fn []
               [:<>
                 [i/file :file1.value :store doc
                  :action #(js/alert (str \"You selected \" (.-name %)))]
                  [:p (str \"Result: \" @doc)]])]
       [frm])")

(defn file-component []
  [:div.columns
   [:div.column
    [doc-comp "file - file selector box component"
     [:p
       "The " [:strong "file"] " component provides a file select box."]
     [["sid" "A storage identifier which defines the location where state for this component is stored."]]
     [[":store" "A reagent atom used as the store for the component. If not supplied, use the default global store."]
      [":change-fn" "A function of one argument called when the state of the component changes. The argument is the changed state."]
      [":action" "A function of 1 argument called once a file has been selected. The argument is the selected file object"]
      [":classes" "A map of strings or vectors of strings which specify CSS class names. Suppported keys are :field, :file, :label, :input and :file-cta."]
      [":label" "The text label to associate with the file selection box."]
      [":right?" "Place label to the right of the selection box if true."]
      [":fullwidth?" "Make the selection box full width if true."]
      [":boxed?" "Use a boxed version of the selection box."]
      [":size" "Set the size of the selection box. Possible values are :small, :medium and :large."]
      [":position" "Set the position of the selection box. Possible values are :right and :center"]]]]
   [:div.column
    [example file-eg [:<>
                      [:p "A basic file selector example"]
                      (let [doc (r/atom {})
                            frm (fn []
                                  [:<>
                                   [i/file :file1.value :store doc
                                    :action #(js/alert (str "You selected " (.-name %)))]
                                   [:p (str "Result: " @doc)]])]
                        [frm])]]]])

(def search-eg1 "
  [:p \"A simple search box example\"]
  [i/search #(js/alert (str \"You are searching for \" %))]")

(def search-eg2 "
  [:p \"Search box with placeholder, icon and colour\"]
  [i/search #(js/alert (str \"You are searching for \" %))
   :placeholder \"enter search terms\" :button-text \"\"
   :icon-data (icons/deficon \"fas fa-search\")
   :classes {:button \"has-background-link has-text-white\"}]")

(defn search-component []
  [:div.columns
   [:div.column
    [doc-comp "search - a basic search box component"
     [:p
       "The " [:strong "search"] " component is a simple search box. "]
     [["action" "A function of one argument executed when the user clicks on the search button. The argument is the text entered into the search box."]]
     [[":classes" "A map of strings or vectors of strings specifying CSS class names. Supported keys are :field, :input and :button"]
      [":icon-data" "An icon data map used to specify an icon to add to the search box."]
      [":button-text" "Text to put on the serach box button. Defaults to Search"]
      [":attrs" "A map of HTML attributes. The keys are keywrodized HTML attribute names."]]]]
   [:div.column
    [example search-eg1 [:<>
                        [:p "A simple search box example"]
                        [i/search #(js/alert (str "You are searching for " %))]]]
    [example search-eg2 [:<>
                         [:p "Search box with placeholder, icon and colour"]
                         [i/search #(js/alert (str "You are searching for " %))
                          :placeholder "enter search terms" :button-text ""
                          :icon-data (icons/deficon "fas fa-search")
                          :classes {:button "has-background-link has-text-white"}]]]]])

(def range-eg1 "
  [:p \"A simple range example\"]
  (let [doc (r/atom {})
        frm (fn []
              [:<>
                [i/range-field :range1.value 0 100 :store doc]
                [:p (str \"Result: \" @doc)]])]
     [frm])")

(def range-eg2 "
  [:p \"Range with label, step and default value\"]
  (let [doc (r/atom {})
        frm (fn []
              [:<>
               [i/range-field :range2.value 0 1000 :store doc
                :value 100 :step 10 :label \"Quantity\"]
               [:p (str \"Result: \" @doc)]])]
     [frm])")

(defn range-component []
  [:div.columns
   [:div.column
    [doc-comp "range-field - a basic range input field"
     [:p
       "The " [:strong "range-field"] " component provides a basic range input "
      "field with a minimum and maximum range values."]
     [["sid" "A storage identifier which specifies where the input state will be stored."]
      ["min" "The minimum acceptable value for the input."]
      ["max" "The maximum accepted value for the input."]]
     [[":store" "A reagent atom which is used to store the component state."]
      [":change-fn" "A function of one argument which is called when the component state changes. The argument is the new state."]
      [":value" "A default initial value for the range."]
      [":label" "A label to associate with the range input field."]
      [":classes" "A map of strings or vectors of strings which specify CSS class names. The supported keys are :field, :input and :label"]
      [":step" "Set the step size for the range. Defaults to 1."]
      [":attrs" "A map of HTML attribute values. Keys are keywordized HTML attribute names."]]]]
   [:div.column
    [example range-eg1 [:<>
                        [:p "A simple range example"]
                        (let [doc (r/atom {})
                              frm (fn []
                                    [:<>
                                     [i/range-field :range1.value 0 100 :store doc]
                                     [:p (str "Result: " @doc)]])]
                          [frm])]]
    [example range-eg2 [:<>
                        [:p "Range with label, step and default value"]
                        (let [doc (r/atom {})
                              frm (fn []
                                    [:<>
                                     [i/range-field :range2.value 0 1000 :store doc
                                      :value 100 :step 10 :label "Quantity"]
                                     [:p (str "Result: " @doc)]])]
                          [frm])]]]])

(def number-eg1 "
  [:p \"A number input component\"]
  (let [doc (r/atom {})
        frm (fn []
              [:<>
               [i/field [i/number :num1.value :store doc]]
               [:p (str \"Result: \" @doc)]])]
    [frm])")

(defn number-component []
  [:div.columns
   [:div.column
    [doc-comp "number-input - a basic number input component"
     [:p
       "The " [:strong "number"] " component is a basic number input "
       "component. It is typically used inside a field component e.g. "
      [:code "field"] " or " [:code "horizontal-field"] "."]
     [["sid" "A storage identifier which determines where the input state for this component is recorded."]]
     [[":store" "A reagent atom used to store the state of this component."]
      [":change-fn" "A function of one argument which is evaluated when the state of the component changes. The argument is the new state."]
      [":value" "The default value used to initialise the component."]
      [":min" "A minimum value for this component. User cannot ennter a value below this amount."]
      [":max" "A maximum value for this component. User cannot enter a value above this amount."]
      [":step" "The step size for this component. Defaults to 1."]
      [":classes" "A map of strings or vectors of strings which specify CSS class names. Supported keys are :control and :inut."]
      [":attrs" "A map of HTML attributes. Keys are keywordized HTML attribute names."]]]]
   [:div.column
    [example number-eg1 [:<>
                         [:p "A number input component"]
                         (let [doc (r/atom {})
                               frm (fn []
                                     [:<>
                                      [i/field [i/number :num1.value :store doc]]
                                      [:p (str "Result: " @doc)]])]
                           [frm])]]]])

(def num-field-eg "
  [:p \"A number input field example\"]
  (let [doc (r/atom {})
        frm (fn []
              [:<>
               [i/number-field :num2.value :min 100 :max 1000
                :step 10 :store doc :label \"Your Number\"
                :value 500 :attrs {:maxLength \"4\"}]
               [:p (str \"Result: \" @doc)]])]
       [frm])")

(defn number-field-component []
  [:div.columns
   [:div.column
    [doc-comp "number-field - a basic number input field"
     [:p "The " [:strong "number-field"] " component is a basic number input "
       "field. It is essentially the " [:code "number-input"] " component "
       "wrapped in a " [:code "field"] " component."]
     [["sid" "Storage identifier which specifies where state will be stored within the data store."]]
     [[":store" "A reagent atom to use as the data store for this component. Defaults to the default global store if not specified."]
      [":change-fn" "A function of one argument which is called when the contgents of the component change. Argument is the updated contents."]
      [":value" "A default value to use for initial value."]
      [":min" "The minimum value which can be entered."]
      [":max" "The maximum value which can be entered."]
      [":step" "The step size for changes in the number value. Defaults to 1."]
      [":classes" "A map of strings or vectors of strings which specify CSS classes to be added. Supported keys are :field, :label, :control and :input"]
      [":label" "A text label to associate with the field."]
      [":attrs" "A map of HTML attributes. Keys are keywordized HTML attribute names."]]]]
    [:div.column
     [example num-field-eg [:<>
                            [:p "A number input field example"]
                            (let [doc (r/atom {})
                                  frm (fn []
                                        [:<>
                                         [i/number-field :num2.value :min 100 :max 1000
                                          :step 10 :store doc :label "Your Number"
                                          :value 500 :attrs {:maxLength "4"}]
                                         [:p (str "Result: " @doc)]])]
                              [frm])]]]])
  
(def page-info1
  [:p "The " [:strong "theopohilus.yorick.input"] " namespace provides components for
collecting user input. Two key concepts used when dealing wiht user input in " [:em "Yorick"] "
 are " [:em "fields"] " and " [:em "stores"] ". A field is a component used to associatge a label and
an input component and is used ot help manage placement, sizing and aliagnment. Functions are provided
for vertical and horizontal alignment of " [:em "fields"] "."])

(def page-info2
  [:p "The " [:em "store"] " refers to a data store object. This is a " [:code "reagent atom"] " used
to track state information. When collecting data from the user, it needs to be recorded somewhere so
that some other process can collect that information and process it in some way. " [:em "Yorick"] " has
a default global store, which is uses for tracking UI related state information. By default, input
components will also use this global store. However, usually, the client coce will define an input specific
store. For example, you might define a store associated with a user input form. All the fields in that form
would store tehir input into keys within that store. Once the user indicates they have finished data input,
other code might then take that store and process the data accordingly. The " [:em "storage identifier"] " or "
   [:code "sid"] " is used to specify the path into the store where the data will be recorded. The sid is
a keyword where periods are used as path separators. The path is a hierarchy of map keys which identify a
specific locaiton within the store. For example, the sid " [:code ":a.b.c"] " represents the path "
   [:code "[:a :b :c]"] ". Using this sid to retrieve data would be equivalent to " [:code "(get-in store [:a :b :c])"]
   ". "])

(defn input-page []
  (let [tab-cur (cursor (spath :ui.tabs.input-page.active-tab))]
    (fn []
      [:<>
       [:div.content
        [:h2.title.is-2 "Input Components"]
        page-info1
        page-info2]
       [:hr]
       [t/tab-bar :ui.tabs.input-page [(t/deftab "field" :value :field)
                                       (t/deftab "input" :value :input)
                                       (t/deftab "checkbox" :value :checkbox)
                                       (t/deftab "radio" :value :radio)
                                       (t/deftab "button" :value :button)
                                       (t/deftab "editable-field" :value :efield)
                                       (t/deftab "textarea" :value :textarea)
                                       (t/deftab "select" :value :select)
                                       (t/deftab "file" :value :file)
                                       (t/deftab "search" :value :search)
                                       (t/deftab "range" :value :range)
                                       (t/deftab "number" :value :number)]
        :size :medium :position :center]
       (case @tab-cur
         :field    [:div
                    [field-component]
                    [horizontal-field-component]]
         :input    [:<>
                    [input-component]
                    [input-field-component]]
         :checkbox [checkbox-component]
         :radio    [radio-component]
         :button   [button-component]
         :efield   [editable-field-component]
         :textarea [textarea-component]
         :select   [:<>
                    [select-component]
                    [select-field-component]]
         :file     [file-component]
         :search   [search-component]
         :range    [range-component]
         :number   [:<>
                    [number-component]
                    [number-field-component]]
         [field-component])])))

