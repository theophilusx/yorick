(ns yorick-demo.modals
  (:require [theophilusx.yorick.modal :as m]
            [theophilusx.yorick.utils :refer [spath]]
            [theophilusx.yorick.store :refer [assoc-in!]]
            [theophilusx.yorick.input :as i]
            [yorick-demo.comp :refer [doc-fn doc-args doc-ns]]))

(def modal-title  "modal - a flexible modal overlay component")

(def modal-desc
  [:<>
      [:p "The " [:strong "modal"] " component is a general purpose modal "
       "overlay component which can hold any type of content. The " [:code "sid"]
       " is used to identify where to track the modal state. When the value is " [:code "true"]
       " the window is displayed and when it is " [:code "false"] " it is hidden."]
      [:p "The optonal " [:code ":classes"] " keyword argument provides a way to add "
       "additional CSS classes to the elements which make up the modal component. The value "
       "is a map of strings or vectors of strings specifying CSS class names. The following "
       "keys are supported."]
      [doc-args [[":modal" "CSS classes to add to the main modal element."]
                 [":background" "CSS classes to associate with the background transparency."]
                 [":content" "CSS classes to associate with the content of the modal window."]
                 [":close" "CSS classes to associate with the close div."]]
       "Supported Keys in :clases Map"]])

(def modal-args
  [["body" "The content to put into the component. Can be a string, another component or some hiccup markup."]
   ["sid" "A storage identifier used to specify where the state of the modal is tracked."]])

(def modal-opt-args
  [[":classes" "A map of strings or vectors of strings specifying CSS class names. See above for list of supported keys"]])

(def modal-examples
  [{:text  "
(let [sid :ui.modal.eg1]
   [:<>
     [m/modal [:div.box \"This is a simple modal overlay\"] sid]
     [i/button \"Open Nodal Example\"
       #(assoc-in! (spath sid) true)]])"
    :code (fn []
            [:<>
             (let [sid :ui.modal.eg1]
               [:<>
                [m/modal [:div.box "This is a simple modal overlay"] sid]
                [i/button "Open Nodal Example"
                 #(assoc-in! (spath sid) true)]])])}])

(defn modal-page []
  [doc-fn {:title modal-title
           :desc modal-desc
           :args modal-args
           :opt-args modal-opt-args
           :examples modal-examples}])

(def mc-title  "modal-card - a modal overlay based on the card component")

(def mc-desc
  [:p "The " [:strong "modal-card"] " component is a flexible modal overlay "
   "which is based around the " [:strong "card"] " component. The "
   [:code "sid"] " argument determines where the state for the modal window is tracked. "
   "Setting the state to " [:code "true"] " reveals the overlay window and setting it to "
   [:code "false"] " will hide it."])

(def mc-args
  [["body" "The main content for the body of the modal's card component."]
   ["sid" "A storage identifier which determines wehre the modal state is tracked."]])

(def mc-opt-args
  [[":classes" (str "A map whose values are a string or vector of strings specifying CSS class names. "
                    "The map supports :modal, :background, :card, :header, :footer and :close keys")]
   [":header" "A component or hiccup markup to use as the header content for the modal card."]
   [":footer" "A component or hiccup markup to use as the footer content for the modal card."]])

(def mc-examples
  [{:text  "
  (let [sid :ui.modal.modal-card-eg1]
       [:<>
        [m/modal-card [:<>
                       [:p \"This is example content\"]
                       [:ul
                        [:li \"Item 1\"]
                        [:li \"Item 2\"]
                        [:li \"Item 3\"]]] sid
         :header \"This is the header\"
         :footer \"This is the footer\"]
        [i/button \"Open Example Modal\"
         #(assoc-in! (spath sid) true)]])"
    :code (fn []
            [:<>
             (let [sid :ui.modal.modal-card-eg]
               [:<>
                [m/modal-card [:<>
                               [:p "This is example content"]
                               [:ul
                                [:li "Item 1"]
                                [:li "Item 2"]
                                [:li "Item 3"]]] sid
                 :header "This is the header"
                 :footer "This is the footer"]
                [i/button "Open Example Modal"
                 #(assoc-in! (spath sid) true)]])])}])

(defn modal-card-page []
  [doc-fn {:title mc-title
           :desc mc-desc
           :args mc-args
           :opt-args mc-opt-args
           :examples mc-examples}])

(def ns-sid :ui.sidebar.modal-ns)

(def ns-title  "The Modal Overlay Component")

(def ns-desc
  [:<>
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
    "container element which holds the modal to prevent scroll overflow"]])

(def ns-pages [{:title "modal"
                :value :modal
                :page modal-page}
               {:title "modal-card"
                :value :modal-card
                :page modal-card-page}])

(defn ns-page []
  [doc-ns ns-sid ns-title ns-desc ns-pages])


