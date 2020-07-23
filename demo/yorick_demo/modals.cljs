(ns yorick-demo.modals
  (:require [theophilusx.yorick.modal :as m]
            [theophilusx.yorick.card :as c]
            [theophilusx.yorick.utils :refer [spath]]
            [theophilusx.yorick.store :refer [get-in assoc-in! global-state]]
            [theophilusx.yorick.input :as i]
            [reagent.core :as r]
            [theophilusx.yorick.tab :as t]))

(defn modal-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.conent
      [:p
       "The " [:strong "modal"] " component is a general purpose modal "
       "overlay component which can hold any type of content. The "
       [:code "body"] " argument is a string, component or hiccup markup to be "
       "used as the body of the modal. The " [:code "sid"] " argument is a "
       "storage identifier keyword used to determine where the modal state will "
       "be stored within the document model store. The " [:code "model"]
       " argument is a reagent atom used as the document model store."]
      [:p
       "The " [:strong "modal"] " component also supports a " [:code ":classes"]
       " optional keyword argument to set CSS classes for the sub-components "
       "used in the modal. The argument is a map of strings or vectors of "
       "strings which represent CSS class names. The supported keys are:"]
      [:ul
       [:li [:strong ":modal"] " - CSS class names for the outer modal div"]
       [:li [:strong ":background"] " - CSS class names for the background div"]
       [:li [:strong ":content"] " - CSS class names for the content div"]
       [:li [:strong ":close"] " - CSS class names for the close div"]]]
     :header {:title "modal - a flexible modal overlay component"}]]
   [:div.column
    [:pre
     [:code
      "(let [doc (r/atom {})]" [:br]
      "  [:<>" [:br]
      "    [modal/modal [:div.box \"This is a simple modal overlay\"] " [:br]
      "       :modal1.value doc]" [:br]
      "    [input/button \"Open Modal Example\" " [:br]
      "      #(assoc-in! doc (spath sid) true)])"]]
    [:div.box
     (let [doc (r/atom {})]
       [:<>
        [m/modal [:div.box "This is a simple modal overlay"] :modal1.value doc]
        [i/button "Open Nodal Example"
         #(assoc-in! doc (spath :modal1.value) true)]])]]])

(defn modal-card-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "modal-card"] " component is a flexible modal overlay "
       "which is based around the " [:strong "card"] " component. The "
       [:code "body"] " argument is the main modal content. It can be a string, "
       "component or hiccup markup. The " [:code "sid"] " argument is a storage "
       "identifier keyword used to determine where to store the modal state "
       "within the document model store. The " [:code "model"] " argument is a "
       "reagent atom used as the document model store. The component supports "
       "the following optional keyword arguments:"]
      [:ul
       [:li [:strong ":header"] " - the card header. Can be a component, string "
        "or hiccup markup."]
       [:li [:strong ":footer"] " - the card footer. Can be a component, string "
        "or hiccup markup."]
       [:li [:strong ":classes"] " - a map of strings or vector of strings "
        "specifying CSS class names to be associated with sub-components of "
        "the main component. Supported keys are "
        [:code ":modal, :background, :card, :header, :footer"] " and "
        [:code ":close"]]]]
     :header {:title "modal-card - a modal overlay based on the card component"}]]
   [:div.column
    [:pre
     [:code
      "(let [doc (r/atom {})]" [:br]
      "  [:<>" [:br]
      "    [modal/modal-card [:<>" [:br]
      "                        [:p \"This is example content\"]" [:br]
      "                        [:ul" [:br]
      "                          [:li \"Item 1\"]" [:br]
      "                          [:li \"Item 2\"]" [:br]
      "                          [:li \"Item 3\"]]]" [:br]
      "      :modal2.value doc :header \"This is the header\"" [:br]
      "      :footer \"This is the footer\"]]" [:br]
      "    [input/button \"Open Example Modal\"" [:br]
      "      #(assoc-in! doc [:modal2 :value] true)]])"]]
    [:div.box
     (let [doc (r/atom {})]
       [:<>
        [m/modal-card [:<>
                       [:p "This is example content"]
                       [:ul
                        [:li "Item 1"]
                        [:li "Item 2"]
                        [:li "Item 3"]]]
         :modal2.value doc :header "This is the header"
         :footer "This is the footer"]
        [i/button "Open Example Modal"
         #(assoc-in! doc (spath :modal2.value) true)]])]]])

(defn modal-page []
  [:<>
   [:div.content
    [:h2.title.is-2 "The Modal Overlay Component"]
    [:p
     "The " [:strong "theophilusx.yorick.modal"] " namespace provides support "
     "for a modal overlay component. Two types of modal overlays components are "
     "provided, a general purpose modal overlay and a modal overlay based on the "
     "card component."]
    [:p
     "Unlike many other components, the modal components require the "
     "specification of a document model store, a reagent atom used to track the "
     "state of the modal overlay. There is also a limit of 640px for the modal "
     "width. You may also want to add the class " [:code "is-clipped"] " to the "
     "container element which holds the modal to prevent scroll overflow"]]
   [:hr]
   [t/tab :ui.tabs.modal-page [(t/deftab "modal" :id :modal)
                               (t/deftab "modal-card" :id :mcard)]
    :position :center :size :medium]
   (case (get-in global-state (spath :ui.tabs.modal-page))
     :modal [modal-component]
     :mcard [modal-card-component]
     [modal-component])])
