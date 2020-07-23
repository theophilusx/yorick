(ns yorick-demo.media
  (:require [theophilusx.yorick.media :as m]
            [theophilusx.yorick.card :as c]
            [theophilusx.yorick.tab :as t]
            [theophilusx.yorick.utils :refer [spath]]
            [theophilusx.yorick.store :refer [get-in global-state]]))

(defn media-component []
  [:div.columns
   [:div.column.is-half
    [c/card
     [:div.content
      [:p
       "The " [:strong "media"] " component is a general purpose and flexible "
       "component for rendering almost any type of content. The "
       [:code "content"] " argument is any HTML or other component type to be "
       "rendered inside the media component. The component also supports the "
       "following optional keyword arguments:"]
      [:ul
       [:li [:strong ":left"] " - a map defining content to be placed to the left "
        "of the main content. Supported keys for the map are "
        [:code ":content, :class"] " and " [:code ":attrs"] ". See below for "
        "description of the keys"]
       [:li [:strong ":right"] " - a map defining content to be placed to the "
        "right of the main content. Supported keys for the map are "
        [:code ":content, :class"] " and " [:code ":attrs"] ". See below for "
        "description of the keys"]
       [:li [:strong ":classes"] " - a map of strings or vectors of strings "
        "representing CSS class names. Supported keys are " [:code ":media"]
        " and " [:code ":content"]]
       [:li [:strong ":attrs"] " - a map of HTML attribute values. Keys are "
        "HTML attribute names as keywords e.g " [:code ":id"]]]
      [:p
       "The supported keys for the " [:code ":left"] " and " [:code ":right"]
       "keyword argument maps are:"]
      [:ul
       [:li [:strong ":content"] " - the content to place to the side of the "
        "main content"]
       [:li [:strong ":class"] " - a string or vector of strings representing "
        "CSS class names"]
       [:li [:strong ":attrs"] " - a map of HTML attribute values. The keys are "
        "HTML attribute names as keywords e.g. " [:code ":id"]]]]
     :header {:title "media - a flexible media component"}]]
   [:div.column
    [:pre
     [:code
      "[:p \"A basic media component\"]" [:br]
      "[m/media \"This is the main media content\"]"]]
    [:div.box
     [:p "A basic media component"]
     [m/media "This is the main media content"]]
    [:pre
     [:code
      "[:p \"A media component with left content\"]" [:br]
      "[media/media \"This is the main content\"" [:br]
      "  :left {:content \"This is the left content\"}]"]]
    [:div.box
     [:p "A media component with left content"]
     [m/media "This is the main content"
      :left {:content "This is the left content"}]]
    [:pre
     [:code
      "[:p \"A media component with right content\"]" [:br]
      "[media/media \"This is the main content\"" [:br]
      "  :right {:content \"This is the right content\"}]"]]
    [:div.box
     [:p "A media component with right content"]
     [m/media "This is the main content"
      :right {:content "This is the right content"}]]
    [:pre
     [:code
      "[:p \"A more complex media component example with colours\"]" [:br]
      "[media/media \"This is the main content\"" [:br]
      "  :left {:content \"This is the left content\"" [:br]
      "         :class \"has-background-danger has-text-white\"}" [:br]
      "  :right {:content \"This is the right content\"" [:br]
      "          :class \"has-background-info has-text-dark\"}" [:br]
      "  :classes {:content \"has-text-success\"}]"]]
    [:div.box
     [:p "A more complex media component example with colours"]
     [m/media "This is the main content"
      :left {:content "This is the left content"
             :class "has-background-danger has-text-white"}
      :right {:content "This is the right content"
              :class "has-background-info has-text-dark"}
      :classes {:content "has-text-success"}]]]])

(defn media-page []
  [:<>
   [:div.content
    [:h2.title.is-2 "The Media Component"]
    [:p
     "The " [:strong "theophilusx.yorick.media"] " namespace provides the "
     [:strong "media"] " component. This component is a general purpose and "
     "flexible component which can be used as a container for almost any type "
     "of content."]
    [:p
     "The " [:strong "media"] " component has a main " [:strong "body"]
     " and optional " [:strong "left"] " and " [:strong "right"]
     " side content. "]]
   [:hr]
   [t/tab :ui.tabs.media-page [(t/deftab "media" :id :media)]
    :position :center :size :medium]
   (case (get-in global-state (spath :ui.tabs.media-page))
     :media [media-component]
     [media-component])])
