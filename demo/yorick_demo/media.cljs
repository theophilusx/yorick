(ns yorick-demo.media
  (:require [theophilusx.yorick.media :as m]
            [theophilusx.yorick.tab :as t]
            [theophilusx.yorick.utils :refer [spath]]
            [theophilusx.yorick.store :refer [cursor]]
            [yorick-demo.comp :refer [doc-comp example]]))

(def media-eg1 "
  [:p \"A basic media component\"]
  [m/media \"This is the main media content\"]")

(def media-eg2 "
  [:p \"A media component with left content\"]
  [m/media \"This is the main content\"
    :left {:content \"This is the left content\"}]")

(def media-eg3 "
  [:p \"A media component with right content\"]
  [m/media \"This is the main content\"
    :right {:content \"This is the right content\"}]")

(def media-eg4 "
  [:p \"A more complex media component example with colours\"]
  [m/media \"This is the main content\"
    :left {:content \"This is the left content\"
           :class \"has-background-danger has-text-white\"}
           :right {:content \"This is the right content\"
           :class \"has-background-info has-text-dark\"}
           :classes {:content \"has-text-success\"}]")

(defn media-component []
  [:div.columns
   [:div.column
    [doc-comp "media - a flexible media component"
     [:p "The " [:strong "media"] " component is a general purpose and flexible "
       "component for rendering almost any type of content. The "
       [:code "content"] " argument is any HTML or other component type to be "
       "rendered inside the media component. The component also supports the "
      "following optional keyword arguments:"]
     [["content" "Any HTML or another yorick or Bulma component which will be rendered inside the media component."]]
     [[":left" "A map containing data to be rendered on the left side of the media component."]
      [":right" "A map containing data to be rendered on the right side of the media component. Supported keys are :content :class and :attrs"]
      [":classes" "A map of strings or vectors of strings specifying CSS classes to add. Supported keys are :media and :content. Supported keys are :content, :class, :attrs"]
      [":attrs" "A map of HTML attributes. Keys are keywordized HTML attribute names"]]]]
   [:div.column
    [example media-eg1 [:<>
                        [:p "A basic media component"]
                        [m/media "This is the main media content"]]]
    [example media-eg2 [:<>
                        [:p "A media component with left content"]
                        [m/media "This is the main content"
                         :left {:content "This is the left content"}]]]
    [example media-eg3 [:<>
                        [:p "A media component with right content"]
                        [m/media "This is the main content"
                         :right {:content "This is the right content"}]]]
    [example media-eg4 [:<>
                        [:p "A more complex media component example with colours"]
                        [m/media "This is the main content"
                         :left {:content "This is the left content"
                                :class "has-background-danger has-text-white"}
                         :right {:content "This is the right content"
                                 :class "has-background-info has-text-dark"}
                         :classes {:content "has-text-success"}]]]]])

(def media-info1
  [:p "The " [:strong "theophilusx.yorick.media"] " namespace provides the "
   [:strong "media"] " component. This component is a general purpose and "
   "flexible component which can be used as a container for almost any type "
   "of content."])

(def media-info2
  [:p "The " [:strong "media"] " component has a main " [:strong "body"]
   " and optional " [:strong "left"] " and " [:strong "right"]
   " side content. "])

(defn media-page []
  (let [tab-cur (cursor (spath :ui.tabs.media-page.active-tab))]
    (fn []
      [:<>
       [:div.content
        [:h2.title.is-2 "The Media Component"]
        media-info1
        media-info2]
       [:hr]
       [t/tab-bar :ui.tabs.media-page [(t/deftab "media" :value :media)]
        :position :center :size :medium]
       (case @tab-cur
         :media [media-component]
         [media-component])]))
  )
