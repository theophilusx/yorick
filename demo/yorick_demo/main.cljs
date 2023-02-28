(ns yorick-demo.main
  (:require [goog.dom :as gdom]
            [reagent.dom :as rdom]
            [theophilusx.yorick.sidebar :as sb]
            [theophilusx.yorick.utils :as utils]
            [theophilusx.yorick.store :as store]
            [yorick-demo.basic :as basic]
            [yorick-demo.cards :as card]
            [yorick-demo.icons :as icons]
            [yorick-demo.inputs :as inputs]
            [yorick-demo.media :as media]
            [yorick-demo.modals :as modal]
            [yorick-demo.navbars :as navbars]
            [yorick-demo.pagination :as pagination]
            [yorick-demo.sidebars :refer [sidebar-page]]
            [yorick-demo.tables :as tables]
            [yorick-demo.toolbars :as tbar]
            [yorick-demo.tabs :as tabs]))

(println "Reloading code")

(defn get-element [name]
  (gdom/getElement name))

(defn mount [el component]
  (rdom/render component el))

(def demo-sidebar [(sb/defmenu [(sb/defentry "Introduction" :value :intro)
                               (sb/defentry "State Management" :value :state)
                               (sb/defentry "Bulma & Classes" :value :css)
                               (sb/defentry "Getting Started" :value :start)
                               (sb/defentry "Feedback & Bug Reports" :value :bugs)
                               (sb/defentry "Changelog" :value :changlog)
                               (sb/defmenu [(sb/defentry "Basic" :value :basic)
                                            (sb/defentry "Cards" :value :cards)
                                            (sb/defentry "Icons" :value :icons)
                                            (sb/defentry "Input" :value :input)
                                            (sb/defentry "Media" :value :media)
                                            (sb/defentry "Modal" :value :modal)
                                            (sb/defentry "Navbar" :value :navbar)
                                            (sb/defentry "Paginate" :value :paginate)
                                            (sb/defentry "Sidebar" :value :sidebar)
                                            (sb/defentry "Tables" :value :tables)
                                            (sb/defentry "Toolbar" :value :toolbar)
                                            (sb/defentry "Tabs" :value :tabs)]
                                 :title "Components")])])

(defn placeholder-page []
  [:div.columns
   [:div.column
    [:h2.title.is-2 "Placeholder Page"]
    [:p "This is the placeholder page for "
     [:strong (str (store/get-in (utils/spath :ui.menu)))]]]])

(defn current-page []
  (case (store/get-in (utils/spath :ui.menu.active-item))
    :basic [basic/ns-page]
    :cards [card/ns-page]
    :icons [icons/ns-page]
    :input [inputs/ns-page]
    :media [media/ns-page]
    :modal [modal/ns-page]
    :navbar [navbars/ns-page]
    :paginate [pagination/ns-page]
    :sidebar [sidebar-page]
    :tables [tables/ns-page]
    :toolbar [tbar/ns-page]
    :tabs [tabs/ns-page]
    [placeholder-page]))

(defn greeting []
  [:<>
   [:section.section.has-background-primary
    [:div.container
     [:header.header
      [:h1.title.is-1.has-text-white "Yorick Examples"]
      [:h3.subtitle.is-3
       "Example of " [:strong "Reagent"] " components styled with "
       [:strong "Bulma"]]]]]
   [:section.section
    [:div.container
     [:div.columns
      [:div.column.is-2
       [sb/sidebar :ui.menu demo-sidebar :default :basic]]
      [:div.column
       [current-page]]]]]])

(defn mount-app []
  (when-let [el (get-element "app")]
    (mount el [greeting])))

(defn ^:dev/after-load init []
  (println "Hello World")
  (mount-app))

@store/default-store
