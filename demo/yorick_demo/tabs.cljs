(ns yorick-demo.tabs
  (:require [theophilusx.yorick.tab :as t]
            [theophilusx.yorick.utils :refer [spath]]
            [theophilusx.yorick.card :as c]
            [theophilusx.yorick.store :refer [get-in global-state]]))

(defn deftab-function []
  [:div.columns
   [:div.column.is-half
    [:h4.title.is-4 "Description"]
    [c/card
     [:div.content
      [:p
       "Description of deftab"]]
     :header {:title "deftab - convenience function for defining tabs"}]]
   [:div.column
    [:h4.title.is-4 "Example"]
    [:pre
     [:code
      "Example Code"]]
    [:div.box
     "Example"]]])

(defn tab-component []
  [:div.columns
   [:div.column.is-half
    [:h4.title.is-4 "Description"]
    [c/card
     [:div.content
      [:p
       "Description of tab component"]]
     :header {:title "tab - a horizontal tab navigation bar component"}]]
   [:div.column
    [:h4.title.is-4 "Example"]
    [:pre
     [:code
      "Example code"]]
    [:div.box
     "Example"]]])

(defn tabs-page []
  [:<>
   [:div.content
    [:h2.title.is-2 "The Tab Component"]
    [:p
     "The " [:strong "theophilusx.yorick.tab"] " namespace provides support "
     "for a horizontal tab navigation bar. The component supports flat modern "
     "and traditional boxed style tab bars, can be aligned to the left, centre "
     "or right of the page and is available in three different sizes."]
    [:p
     "The " [:code "tab"] " component tracks the current selected tab in the "
     "global state atom defined by " [:code "theophilusx.yorick.store/global-state"]
     " under the key specified by the " [:code "sid"] " argument, allowing other "
     "components and ClojureScript code to react to tab changes. A convenience "
     "function " [:code "deftab"] " is provided to support the definition of "
     "tab bar navigation tabs."]]
   [:hr]
   [t/tab :ui.tabs.tab-page [(t/deftab "deftab Function" :id :deftab)
                             (t/deftab "tab Component" :id :tab)]]
   (case (get-in global-state (spath :ui.tabs.tab-page))
     :deftab [deftab-function]
     :tab [tab-component]
     [deftab-function])])
