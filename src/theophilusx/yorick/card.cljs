(ns theophilusx.yorick.card
  (:require [theophilusx.yorick.icon :as icons]
            [theophilusx.yorick.basic :as basic]))

(defn card-header [title & {:keys [icon icon-action class]}]
  [:header.card-header {:class [class]}
   [:p.card-header-title title]
   (when icon
     [basic/a [icons/icon-component icon]
      :class "card-header-icon"
      :href "#"
      :on-click (when (fn? icon-action)
                  #(icon-action %))])])

(defn card-footer [items & {:keys [classes]}]
  (into
   [:footer.card-footer {:class [(:footer classes)]}]
   (for [i items]
     [:p.card-footer-item {:class [(:footer-item classes)]}
      i])))

(defn card [body & {:keys [header footer classes]}]
  [:div.card {:class [(:card classes)]}
   (when header
     header)
   [:div.card-content {:class [(:card-content classes)]}
    body]
   (when footer
     footer)])
