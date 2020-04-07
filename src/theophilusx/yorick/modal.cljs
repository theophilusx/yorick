(ns theophilusx.yorick.modal
  (:require [theophilusx.yorick.utils :refer [spath]]
            [reagent.session :as session]))

(defn modal [body id & {:keys [modal-class background-class content-class
                               close-class]}]
  [:div.modal {:class [modal-class
                       (when (session/get-in (spath id))
                         "is-active")]}
   [:div.modal-background {:class background-class
                           :on-click #(session/assoc-in! (spath id) false)}]
   (into
    [:div.modal-content {:class content-class}]
    (for [c body]
      c))
   [:button.modal-close.is-large {:class close-class
                                  :aria-label "close"
                                  :on-click #(session/assoc-in! (spath id) false)}]])

(defn modal-card [body id & {:keys [modal-class background-class card-class
                                    header header-class body-class
                                    footer footer-class close-class]}]
  [:div.modal {:class [modal-class
                       (when (session/get-in (spath id))
                         "is-active")]}
   [:div.modal-background {:class background-class
                           :on-click #(session/assoc-in! (spath id) false)}]
   [:div.modal-card {:class card-class}
    (when header
      (into
       [:header.modal-card-head {:class header-class}]
       (for [h header]
         h)))
    (into
     [:section.modal-card-body {:class body-class}]
     (for [b body]
       b))
    (when footer
      (into
       [:footer.modal-card-foot {:class footer-class}]
       (for [f footer]
         f)))]
   [:button.modal-close.is-large {:class close-class
                                  :aria-label "close"
                                  :on-click #(session/assoc-in! (spath id) false)}]])
