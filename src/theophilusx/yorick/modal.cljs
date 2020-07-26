(ns theophilusx.yorick.modal
  "Provides a modal overlay window which can contain almost any content."
  (:require [theophilusx.yorick.utils :refer [cs spath]]
            [theophilusx.yorick.store :as store]))

(defn modal
  "A basic modal overlay component. The `body` argument is the content to put
  into the overlay. It can be HTML, a string or another component. The `sid`
  argument is a storage identifier keyword used to determine where to store
  the modal state (either active or inactive). The `model` argument is a
  reagent atom used as the document model store. This component also supports
  an optional keyword argument `:classes`, which is a map of strings or vectors
  of strings representing CSS class names. The supported keys are:

  | Keyword       | Description                                               |
  |---------------|-----------------------------------------------------------|
  | `:modal`      | CSS class names to associate with the enclosing modal div |
  | `:background` | CSS class names to associate with the modal background    |
  |               | transparency                                              |
  | `:content`    | CSS class names to associate with the content div         |
  | `:close`      | CSS class names to associate with the close div           |"
  [body sid model & {:keys [classes]}]
  [:div.modal {:class (cs (:modal classes)
                          (when (store/get-in model (spath sid))
                            "is-active"))}
   [:div.modal-background
    {:class (cs (:background classes))
     :on-click #(store/assoc-in! model (spath sid) false)}]
   [:div.modal-content {:class (cs (:content classes))}
    body]
   [:button.modal-close.is-large
    {:class (cs (:close classes))
     :aria-label "close"
     :on-click #(store/assoc-in! model (spath sid) false)}]])

(defn modal-card
  "A specialised modal overlay based around the `card` component. The `body`
  argument is the content for the body of the card. It can be a string, HTML
  or another component. The `sid` argument is a storage identifier keyword used
  to determine where state is managed for the modal. The `model` argument is a
  reagent atom used as the store for the modal state. The component also
  supports some optional keyword arguments:

  | Keyword    | Description                                                   |
  |------------|---------------------------------------------------------------|
  | `:header`  | The modal card header. Should be a component or Hiccup markup |
  | `:footer`  | The modal card footer. Should be a component or hiccup markup |
  | `:classes` | A map of strings or vectors of strings representing CSS class |
  |            | names. Supported keys are `:modal`, `:background`, `:card`    |
  |            | `:header`, `:body`, `:footer` and `:close`                    |"
  [body sid model & {:keys [header footer classes]}]
  [:div.modal {:class (cs (:modal classes)
                          (when (store/get-in model (spath sid))
                            "is-active"))}
   [:div.modal-background
    {:class (cs (:background classes))}]
   [:div.modal-card {:class (cs (:card classes))}
    (when header
      [:header.modal-card-head {:class (cs (:header classes))}
       [:div.modal-card-title header]
       [:button.delete {:aria-label "close"
                        :on-click #(store/assoc-in! model (spath sid) false)}]])
    [:section.modal-card-body {:class (cs (:body classes))}
     body]
    (when footer
      [:footer.modal-card-foot {:class (cs (:footer classes))}
       footer])]])
