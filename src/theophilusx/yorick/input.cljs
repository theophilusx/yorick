(ns theophilusx.yorick.input
  (:require [theophilusx.yorick.utils :refer [spath value-of value->keyword]]
            [theophilusx.yorick.icon :as icons]
            [theophilusx.yorick.store :as store]
            [reagent.core :as r]))

(defn field
  "Generate a field container to hold input labels or fields.
  The `body` argument is typically a user input component or a `<div>` element
  with the `control` class. Also supports two optional keyword arguments

  | Keyword    | Description                                            |
  |------------|--------------------------------------------------------|
  | `:label`   | A string label to use with the field                   |
  | `:classes` | A map of strings or vector of strings representing CSS |
  |            | class names. Supported keys are `:field` and `:label`  |"
  [body & {:keys [label classes]}]
  [:div.field {:class [(:field classes)]}
   (when label
     [:label.label {:class [(:label classes)]} label])
   body])

(defn horizontal-field
  "Generate a horizontal field with `label` as the field label and `body` as
  the contents of the field. An optional keyword argument of `:classes` is
  supported. The value is a map which can contain keys for `:label`, `:field`
  and `:body`. The value for the keys is either a CSS class name or a vector
  of CSS class names. CSS class names are strings. The `:label` key lists
  classes to be associated with the field label, the `:field` key for classes
  associated with the outer field container and the `:body` for classes
  associated with the field body element."
  [label body & {:keys [classes]}]
  [:div.field.is-horizontal {:class (:field classes)}
   [:div.field-label
    [:label.label {:class (:label classes)}
     label]]
   [:div.field-body {:class [(:body classes)]}
    body]])

(defn input-helper
  "This function is a helper for text input field components. It is not meant
  to be called directly, but rather used by other components. The function has
  the following mandatory arguments:

  | Argument  | Description                                                     |
  |-----------|-----------------------------------------------------------------|
  | `type`    | The input field type. One of the HTML input types               |
  | `sid`     | A storage id keyword value used as an `spath` when storing      |
  |           | input into the doc atom                                         |
  | `doc`     | A reagent `atom` object used to store user input                |
  | `chg-fun` | A function of 1 argument which is called whenever input data    |
  |           | changes. The argument is the new value of the input data.       |
  |-----------|-----------------------------------------------------------------|

  The function also accepts a number of optional keyword arguments:

  | Keyword        | Description                                   |
  |----------------|-----------------------------------------------|
  | `:class`       | CSS class names or vector of CSS class names  |
  |                | to add to the input element                   |
  | `:placeholder` | Text to use in the HTML placeholder attribute |
  | `:required`    | True if the input field is a required field   |
  | `:disabled`    | True if the field should be disabled          |
  | `:min`         | Number for the HTML min attribute             |
  | `:max`         | Number for the HTML max attribute             |
  | `:maxlength`   | Number for the HTML maxlength attribute       |
  | `:minlength`   | Number for the HTML minLength attribute       |
  | `:readonly`    | True if the input element is to be read only  |
  | `:size`        | Number for the HTML size attribute            |
  |----------------|-----------------------------------------------|"
  [type sid doc chg-fn & {:keys [class placeholder required disabled
                                 min max maxlength minlength readonly size]}]
  [:input.input {:type (name type)
                 :class class
                 :id (name sid)
                 :name (name sid)
                 :placeholder placeholder
                 :required required
                 :value (str (store/get-in doc (spath sid)))
                 :on-change chg-fn
                 :disabled disabled
                 :min (when min (str min))
                 :max (when max (str max))
                 :maxLength (when maxlength (str maxlength))
                 :minLength (when minlength (str minlength))
                 :readOnly readonly
                 :size (when size (str size))}])

(defn input
  "A basic text input component. This component is often used as the body for
  a field or horizontal-field component. Mandatory arguments are:

  | Argument | Description                                                   |
  |----------|---------------------------------------------------------------|
  | `type`   | Input type. An HTML input type name as a keyword e.g. `:text` |
  | `sid`    | A storage path keyword e.g. `:data.value`.                    |
  |----------|---------------------------------------------------------------|

  This component also supports a number of optional keyword arguments:

  | Keyword        | Description                                                |
  |----------------|------------------------------------------------------------|
  | `:model`       | A reagent atom to be used for storing user input           |
  | `:change-fn`   | A function of 1 argument called when user enters data in   |
  |                | the field.                                                 |
  | `:classes`     | A map of CSS classes to add to elements in the component.  |
  |                | The value for each key is either a string CSS class name   |
  |                | or a vector of CSS class name strings. Supported keys are  |
  |                | `:control` and `:input`                                    |
  | `:icon-data`   | Either an icon data map or vector of icon data maps. See   |
  |                |`theophilusx.yorick.icon` for details on icon data map.     |
  |                | Add sability to add icons to an input component            |
  | `:placeholder` | Text to use in the HTML placeholder attribute              |
  | `:required`    | True if the input field is a required field                |
  | `:disabled`    | True if the field should be disabled                       |
  | `:min`         | Number for the HTML min attribute                          |
  | `:max`         | Number for the HTML max attribute                          |
  | `:maxlength`   | Number for the HTML maxlength attribute                    |
  | `:minlength`   | Number for the HTML minLength attribute                    |
  | `:readonly`    | True if the input element is to be read only               |
  | `:size`        | Number for the HTML size attribute                         |
  |----------------|------------------------------------------------------------|"
  [_ sid & {:keys [model change-fn]}]
  (let [doc (or model
                (r/atom {}))
        chg-fn (if (fn? change-fn)
                 change-fn
                 (fn [e]
                   (store/assoc-in! doc (spath sid) (value-of e))))]
    (fn [type sid & {:keys [classes icon-data]
                    :as args}]
      (if icon-data
        (into
         [:div.control {:class [(:control classes)
                                (icons/icon-control-class icon-data)]}
          (apply input-helper type sid doc chg-fn :class (:input classes)
                 (into [] cat args))]
         (for [i (icons/icons icon-data)]
           i))
        [:div.control {:class (:control classes)}
         (apply input-helper type sid doc chg-fn :class (:input classes)
                (into [] cat args))]))))

(defn input-field
  "Convenience component which combines `field` and `input` components to
  create a simple input component. The `label` argument is text to be used assoc
  the field label. The `type` argument specifies the input field type. It is a
  keyword representing one of the HTML5 input types e.g. :text, :email, etc.
  The `sid` argument is a storage identifier which specifies the key(s) to use
  when storing the input in the document model atom. This component also
  supports a number of optional keyword arguments.

  | Keyword        | Description                                                |
  |----------------|------------------------------------------------------------|
  | `:classes`     | a map of CSS class name strings or vectors of class name   |
  |                | strings. Supported keys are `:field`, `:label` and `:input`|
  | `:placeholder` | string to use for HTML placeholder attribute               |
  | `:required`    | boolean used for HTML required attribute                   |
  | `:icon-data`   | an icon data map or vector of icon data maps. See the      |
  |                | `theophilusx.yorick.icon` namespace for details            |
  | `:model`       | A reagent atom to be used as the document model            |
  | `:change-fn`   | a function of one argument called to update the value      |
  |                | in the document model atom when input changes              |
  | `:disabled`    | boolean used to set HTML disabled attribute                |
  | `:min`         | number used to set the HTML min attribute                  |
  | `:max`         | number used to set the HTML max attribute                  |
  | `:minlength`   | number used to set the HTML minLength attribute            |
  | `:maxlength`   | number used to set the HTML maxLength attribute            |
  | `:readonly`    | boolean used to set the HTML readonly attribute            |
  | `:size`        | number used to set the HTML size attribute                 |"
  [label type sid & {:keys [classes placeholder required icon-data
                            model change-fn disabled min max maxlength
                            minlength readonly size]}]
  [field [input type sid :classes classes :placeholder placeholder
          :required required :icon-data icon-data :model model
          :change-fn change-fn :disabled disabled :min min :max max
          :maxlength maxlength :minlength minlength :readonly readonly
          :size size]
   :label label :classes classes])

(defn checkbox
  "Provides a basic checkbox component. The `label` argument is a string used
  as the label to be associated with the checkbox. The `sid` argument is a
  keyword storage identifier used to define the storage key within the document
  model atom. A number of optional keyword arguments are also supported

  | Keyword      | Description                                               |
  |--------------|-----------------------------------------------------------|
  | `:model`     | a reagent atom to use as the document model for this      |
  |              | checkbox                                                  |
  | `:change-fn` | a function of 1 argument called when the input changes to |
  |              | update the data in the document model                     |
  | `:classes`   | a map of string or vectors of strings representing CSS    |
  |              | class names. Supported keys are `:field`, `:control`,     |
  |              | `:label` and `:input`                                     |
  | `:checked`   | boolean used to set the HTML attribute checked            |
  | `:required`  | boolean used to set the HTML attribute required           |"
  [_ sid & {:keys [model change-fn]}]
  (let [doc (or model
                (r/atom {}))
        chg-fn (if (fn? change-fn)
                 change-fn
                 #(store/update-in! doc (spath sid) not))]
    (fn [label sid & {:keys [classes checked required]}]
      [:div.field {:class (:field classes)}
       [:div.control {:class (:control classes)}
        [:label.checkbox {:class (:label classes)}
         [:input {:classs (:input classes)
                  :type "checkbox"
                  :id (name sid)
                  :name (name sid)
                  :checked checked
                  :required required
                  :on-change chg-fn}]
         (str " " label)]]])))

(defn radio
  "A radio button component. The `sid` argument is a keyword used as the storage
  identifier to store data in the document model atom. The `labels` argument
  is a vector of maps specifying each radio button. The map has the following keys

  | Key        | Description                                                  |
  |------------|--------------------------------------------------------------|
  | `:title`   | the text or label to associate with a radio button           |
  | `:value`   | the value to store in the document model atom when a button  |
  |            | sis elected. If not provided, the title, converted to a      |
  |            | keyword, is used                                             |
  | `:checked` | if `true`, the button will be rendered checked. Only one of  |
  |            | the button definitions should have this value set to `true`  |

  The component also supports a number of optional keyword arguments

  | Keyword      | Description                                              |
  |--------------|----------------------------------------------------------|
  | `:model`     | a reagent atom used as the document model for this       |
  |              | component                                                |
  | `:change-fn` | a function of one argument called when the input changes |
  |              | to update the document model atom                        |
  | `:classes`   | A map of strings or vector of strings specifying CSS     |
  |              | class names. Supported keys are `:control`, `input` and  |
  |              | `:label`                                                 |"
  [sid labels & {:keys [model click-fn]}]
  (let [doc (or model
                (r/atom {}))
        btns (mapv (fn [b]
                     (let [v (or (:value b)
                                 (value->keyword (:title b)))]
                       (when (:checked b)
                         (store/assoc-in! doc (spath sid) v))
                       {:title (:title b)
                        :value v})) labels)
        change-fn (if (fn? click-fn)
                 click-fn
                 #(store/assoc-in! doc (spath sid)
                                   (value->keyword (value-of %))))]
    (fn [sid _ & {:keys [classes]}]
      (into
       [:div.control {:class (:control classes)}]
       (for [b btns]
         [:label.radio {:class (:label classes)}
          [:input.radio {:type "radio"
                         :class (:input classes)
                         :name (name sid)
                         :value (:value b)
                         :defaultChecked (when (= (store/get-in doc (spath sid))
                                           (:value b))
                                    true)
                         :on-click change-fn}]
          (str " " (:title b))]))))) 

(defn button
  "A basic button component. The `title` argument is a string which will be
  used for the button text. The `action` argument is a function with no
  arguments that is called when the button is clicked. The component also
  supports an optional `:classes` keyword argument, which is a map of
  strings or vectors of strings representing CSS class names. The map supports
  the keys `:field`, `:control` and `:button`."
  [title action & {:keys [classes]}]
  [:div.field {:class (:field classes)}
   [:div.control {:class (:control classes)}
    [:button.button {:class (:button classes)
                     :type "button"
                     :on-click action}
     title]]])

(defn editable-field
  "Provides a utility component which will display a value with a 'pencil'
  icon, which when clicked, will change the displayed value to be a text
  input area where the value can be edited. Adds a 'save' and 'cancel' button
  to save or cancel the edit. The `type` argument is a keyword representing one
  of the HTML input types e.g. :text, :email. The `src` argument is a reagent
  atom representing the document model atom holding the data to be edited. The
  `sid` argument is a storage identifier keyword which specifies the path into
  the document model atom where the value to edit is stored. This component also
  supports two optional keyword arguments, `:label` and `:classes`. The `:label`
  keyword argument is a string which specifies a text label to associate with
  the field. The `:classes` keyword argument is a map of strings or vectors of
  strings representing CSS class names. The map supports the following keys

  | Key                   | Description                                         |
  |-----------------------|-----------------------------------------------------
  | `:edit-input`         | CSS classes to associate with the edit input        |
  |                       | element                                             |
  | `:display-input`      | CSS classes to associated with the div around       |
  |                       | display of the value                                |
  | `:save-btn-field`     | CSS classes to associate with the field div         |
  |                       | wrapping the save button                            |
  | `:save-btn-control`   | CSS classes to associate with the control div that  |
  |                       | wraps the save button                               |
  | `:save-btn-button`    | CSS classes to associate with the save button       |
  |                       | element                                             |
  | `:cancel-btn-field`   | CSS classes to associate with the field div wrapping|
  |                       | the cancel button                                   |
  | `:cancel-btn-control` | CSS classes to associate with the control div       |
  |                       | wrapping the cancel button                          |
  | `:cancel-btn-button`  | CSS classes to associate with the cancel button     |
  | `:label`              | CSS classes to associate with the label             |
  | `:field-edit`         | CSS classes to associate with the field when editing|
  |                       | values                                              |
  | `:field-edit-body`    | CSS classes to associate with the field body when   |
  |                       | editing values                                      |
  | `:field-display`      | CSS classes to associate with the field when        |
  |                       | displaying values                                   |
  | `:field-display-body` | CSS classes to associate with the field body when   |
  |                       | displaying values                                   |"
  [_ src sid & _]
  (let [doc (r/atom {:value (store/get-in src (spath sid))
                     :editing false})
        save-fn (fn []
                  (store/assoc-in! src (spath sid) (:value @doc))
                  (store/update! doc :editing not))
        cancel-fn (fn []
                    (store/put! doc :value (store/get-in src (spath sid)))
                    (store/update! doc :editing not))]
    (fn [type _ _ & {:keys [label classes]}]
      (if (:editing @doc)
        [horizontal-field label [:<>
                                 [input type :value :model doc
                                  :class (:edit-input classes)]
                                 [button "Save" save-fn
                                  :classes {:field (:save-btn-field classes)
                                            :control (:save-btn-control classes)
                                            :button (:save-btn-button classes)}]
                                 [button "Cancel" cancel-fn
                                  :classes {:field (:cancel-btn-field classes)
                                            :control (:cancel-btn-control classes)
                                            :button (:cancel-btn-button classes)}]]
         :classes {:field (:field-edit classes)
                   :label (:label classes)
                   :body (:field-edit-body classes)}]
        [horizontal-field label [:<>
                                 [:div {:class (:display-input classes)}
                                  (if (= type :password)
                                    "******** "
                                    (str (:value @doc) " "))
                                  [:span.icon
                                   {:on-click #(store/update! doc :editing not)}
                                   [:i.fas.fa-edit]]]]
         :classes {:field (:field-display classes)
                   :label (:label classes)
                   :body (:field-display-body classes)}]))))

(defn textarea
  "A basic text area component. The `label` argument is a string containing
  the text to be used as a label for the text area. The `sid` argument is a
  storage identifier keyword used to determine where the data entered is
  stored within the document model atom. The component supports the following
  optional keyword arguments

  | Keyword        | Description                                               |
  |----------------|-----------------------------------------------------------|
  | `:model`       | A reagent `atom` representing the document model used to  |
  |                | store input                                               |
  | `:change-fn`   | A function of one argument called when the data changes.  |
  |                | The argument is the new data.                             |
  | `:classes`     | A `map` of CSS class names or vectors of CSS class names  |
  |                | which are added to component elements. Supported keys are |
  |                | `:field`, `:label` and ':textarea`                        |
  | ':placeholder` | A `string` of text used as a placeholder in the text area |"
  [_ sid & {:keys [model change-fn]}]
  (let [doc (or model
                (r/atom {}))
        chg-fn (if (fn? change-fn)
                 change-fn
                 #(store/assoc-in! doc (spath sid) (value-of %)))]
    (fn [label sid & {:keys [classes placeholder]}]
      [:div.field {:class (:field classes)}
       (when label
         [:label.label {:class (:label classes)} label])
       [:p.control {:class (:control classes)}
        [:textarea.textarea {:class (:textarea classes)
                             :placeholder placeholder
                             :id (name sid)
                             :name (name sid)
                             :on-change chg-fn}]]])))

(defn defoption
  "Define an option for a selection list. The `title` argument is a string used 
  as the text label for the option. This component supports a number of optional 
  keyword arguments

  | Keyword         | Description                                            |
  |-----------------|--------------------------------------------------------|
  | `:value`        | A value to use when this option is selected            |
  | `:option-class` | A string or vector of strings representing CSS classes |
  | `:disabled`     | boolean. If true, this option will be disabled         |
  | `:label`        | A shorter label to be used rather than the title       |"
  [title & {:keys [value option-class disabled label]}]
  [:option {:class option-class
            :disabled disabled
            :label label
            :value (str (or value title))}
   title])

(defn select
  "A basic select list component. The `sid` argument is a storage identifier 
  keyword. The `options` argument is a vector of option component representing 
  each option for the select list. Each option can be defined using the 
  `defoption` function. The component also supports a number of optional keyword
  arguments

  | Keyword         | Description                                             |
  |-----------------|---------------------------------------------------------|
  | `:model`        | A reagent atom to be used as the document model where   |
  |                 | the selected value will be stored                       |
  | `:change-fn`    | A function of one argument that is called when a value  |
  |                 | is selected. The argument is the new data value selected|
  | `:selected`     | The value to be selected by default when the list is    |
  |                 | rendered                                                |
  | `:select-class` | A string or vector of strings representing CSS class    |
  |                 | names to associate with the select element              |
  | `:multiple`     | Boolean. If true, allow multiple options to be selected |
  | `:rounded`      | Boolean. If true, use rounded corners                   |
  | `:select-size`  | Sets the size of the select box. Can be `:large`,       |
  |                 | `:medium` or `:small`                                   |
  | `:icon-data`    | An icon-data map defining an icon to include with the   |
  |                 | select box (see `theophilusx/yorick/icon`               |"
  [sid options & {:keys [model change-fn selected]}]
  (let [doc (or model
                (r/atom {}))
        chg-fn (if (fn? change-fn)
                 change-fn
                 #(store/assoc-in! doc (spath sid) (value-of %)))]
    (store/assoc-in! doc (spath sid) (or selected
                                         (:value (second (first options)))))
    (fn [sid options & {:keys [select-class multiple rounded select-size
                        icon-data selected]}]
      [:div.select {:class [select-class
                            (when rounded "is-rounded")
                            (case select-size
                              :small "is-small"
                              :medium "is-medium"
                              :large "is-large"
                              nil)
                            (when multiple "is-multiple")]}
       (into
        [:select {:id (name sid)
                  :name (name sid)
                  :multiple (when multiple true false)
                  :size (when multiple
                          (str multiple))
                  :defaultValue (if selected
                                  selected
                                  (:value (second (first options))))
                  :on-change chg-fn}]
        (for [o options]
          o))
       [icons/icon-component icon-data]])))

(defn select-field [sid options & {:keys [title classes multiple rounded
                                          select-size icon-data model
                                          change-fn selected]}]
  [:div.field {:class (:field classes)}
   (when title
     [:div.label title])
   [select sid options :classes classes :multiple multiple
    :rounded rounded :select-size select-size :icon-data icon-data
    :selected selected :model model :change-fn change-fn]])

(defn file [sid & {:keys [action model change-fn]}]
  (let [doc (or model
                (r/atom {}))
        chg-fn (if (fn? change-fn)
                 change-fn
                 (fn [e]
                   (let [file-list (-> e .-target .-files)]
                     (when (.-length file-list)
                       (let [file (first (array-seq file-list))]
                         (store/assoc-in! doc (spath sid) (.-name file))
                         (when (fn? action)
                           (action file)))))))]
    (fn [id & {:keys [classes label right fullwidth boxed size position]}]
      [:div.field {:class (:field classes)}
   [:div.file.has-name {:class [(:file classes)
                                (when right "is-right")
                                (when fullwidth "is-fullwidth")
                                (when boxed "is-boxed")
                                (case size
                                  :small "is-small"
                                  :medium "is-medium"
                                  :large "is-large"
                                  nil)
                                (case position
                                  :center "is-centered"
                                  :right "is-right"
                                  nil)]}
    [:label.file-label {:class (:label classes)}
     [:input.file-input {:class (:input classes)
                         :id (name id)
                         :name (name id)
                         :type "file"
                         :on-change chg-fn}]
     [:span.file-cta {:class (:file-cta classes)}
      [:span.file-icon
       [:i.fa.fa-upload]]
      [:span.file-label
       (or label
           "Choose a file")]
      [:span.file-name
       (if (store/get-in doc (spath id))
         (str (store/get-in doc (spath id)))
         "No file chosen")]]]]])))

(defn search [_ & _]
  (let [doc (r/atom {})]
    (fn [action & {:keys [placeholder]}]
      [:<>
       [field [:<>
               [input :text :search :placeholder placeholder :model doc]
               [button "Search" #(action (:search @doc))]]
        :classes {:field "has-addons"}]])))

(defn range-field [sid _ _ & {:keys [model change-fn value]}]
  (let [doc (or model
                (r/atom {}))
        chg-fn (if (fn? change-fn)
                 change-fn
                 (fn [e]
                   (store/assoc-in! doc (spath sid) (value-of e))))]
    (store/assoc-in! doc (spath sid) value)
    (fn [sid min max & {:keys [label classes required disabled step]}]
      [field [:div.field.has-addons
              [:span {:style {:paddingRight "5px"}}(str min " ")]
              [:input {:type "range"
                       :class (:input classes)
                       :required required
                       :min (str min)
                       :max (str max)
                       :step (if step
                               (str step)
                               "1")
                       :id (name sid)
                       :name (name sid)
                       :value (str (store/get-in doc (spath sid)))
                       :on-change chg-fn
                       :disabled disabled}]
              [:span {:style {:paddingRight "5px"
                              :paddingLeft "5px"}}
               (str " " max " ")]
              [:span {:style {:paddingLeft "5px"}}
               (str " " (store/get-in doc (spath sid)))]]
       :label label :classes classes])))

(defn number-input [sid & {:keys [model change-fn value]}]
  (let [doc (or model
                (r/atom {}))
        chg-fn (if (fn? change-fn)
                 change-fn
                 (fn [e]
                   (store/assoc-in! doc (spath sid) (js/parseInt (value-of e)))))]
    (when value
      (store/assoc-in! doc (spath sid) value))
    (fn [sid & {:keys [min max step required disabled maxlength minlength
                      readonly size classes]}]
      [:div.control {:class (:control classes)}
       [:input {:type "number"
                :id (name sid)
                :name (name sid)
                :min (when min (str min))
                :max (when max (str max))
                :step (if step
                        (str step)
                        "1")
                :on-change chg-fn
                :required required
                :disabled disabled
                :maxLength (when maxlength (str maxlength))
                :minLength (when minlength (str minlength))
                :readOnly readonly
                :size (when size (str size))
                :value (when (store/get-in doc (spath sid))
                         (str (store/get-in doc (spath sid))))
                :class (:input classes)}]])))

(defn number-field [sid & {:keys [model change-fn value min max step maxlength
                                  minlength readonly size required disabled
                                  classes label]}]
  [field [number-input sid :model model :change-fn change-fn :min min
          :max max :step step :maxlength maxlength :minlength minlength
          :readonly readonly :size size :required required :disabled disabled
          :classes classes :value value]
   :label label :classes classes])
