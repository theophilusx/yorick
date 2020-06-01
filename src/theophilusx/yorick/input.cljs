(ns theophilusx.yorick.input
  (:require [theophilusx.yorick.utils :refer [spath value-of value->keyword]]
            [theophilusx.yorick.icon :as icons]
            [theophilusx.yorick.store :as store]
            [reagent.core :as r]))

(defn field
  "Generate a field container to hold input labels or fields.
  The `body` argument is typically a user input component or a `<div>` element
  with the `control` class. "

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

(defn input-field [label type id & {:keys [classes placeholder required icon-data
                                           model change-fn disabled min max
                                           maxlength minlength readonly size]}]
  [field [input type id :classes classes :placeholder placeholder
          :required required :icon-data icon-data :model model
          :change-fn change-fn :disabled disabled :min min :max max
          :maxlength maxlength :minlength minlength :readonly readonly
          :size size]
   :label label :classes classes])

(defn checkbox [_ sid & {:keys [model change-fn]}]
  (let [doc (or model
                (r/atom {}))
        chg-fn (if (fn? change-fn)
                 change-fn
                 #(store/update-in! doc (spath sid) not))]
    (fn [label sid & {:keys [classes checked required]}]
      [:div.field {:class (:field classes)}
       [:div.control {:class (:control classes)}
        [:label.checkbox {:class (:label classes)}
         [:input {:type "checkbox"
                  :id (name sid)
                  :name (name sid)
                  :checked checked
                  :required required
                  :on-change chg-fn}]
         (str " " label)]]])))

(defn radio [id _ & {:keys [model click-fn]}]
  (let [doc (or model
                (r/atom {}))
        clk-fn (if (fn? click-fn)
                 click-fn
                 #(store/assoc-in! doc (spath id)
                                   (value->keyword (value-of %))))]
    (fn [id labels & {:keys [classes]}]
      (into
       [:div.control {:class (:control classes)}]
       (for [l labels]
         [:label.radio {:class (:label classes)
                        :name (name id)
                        :value (or (:value l)
                                   (value->keyword (:title l)))
                        :checked (:checked l)
                        :on-click clk-fn}
          (:title l)])))))

(defn button [title action & {:keys [classes]}]
  [:div.field {:class (:field classes)}
   [:div.control {:class (:control classes)}
    [:button.button {:class (:button classes)
                     :type "button"
                     :on-click action}
     title]]])

(defn editable-field [_ src sid & _]
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
        [horizontal-field label [field [:<>
                                        [input type :value :model doc]
                                        [button "Save" save-fn
                                         :classes (:save-button classes)]
                                        [button "Cancel" cancel-fn
                                         :classes (:cancel-button classes)]]
                                 :classes {:field "has-addons"}]
         :classes (:field-edit classes)]
        [horizontal-field label [field [:<>
                                        (if (= type :password)
                                          "********"
                                          (str (:value @doc)))
                                        [:span.icon
                                         {:on-click #(store/update!
                                                      doc
                                                      :editing not)}
                                         [:i.fa.fa-pencil]]]]
         :classes (:field-view classes)]))))

(defn textarea [_ sid & {:keys [model change-fn]}]
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

(defn defoption [title & {:keys [value option-class disabled label]}]
  [:option {:class option-class
            :disabled disabled
            :label label
            :value (str (or value title))}
   title])

(defn select [sid options & {:keys [model change-fn selected]}]
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
