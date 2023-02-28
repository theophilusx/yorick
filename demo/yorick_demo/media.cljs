(ns yorick-demo.media
  (:require [theophilusx.yorick.media :as m]
            [yorick-demo.comp :refer [doc-fn doc-ns]]))

(def media-title  "media - a flexible media component")

(def media-desc
  [:p "The " [:strong "media"] " component is a general purpose and flexible "
       "component for rendering almost any type of content. The "
       [:code "content"] " argument is any HTML or other component type to be "
       "rendered inside the media component. The component also supports the "
      "following optional keyword arguments:"])

(def media-args
  [["content" "Any HTML or another yorick or Bulma component which will be rendered inside the media component."]])

(def media-opt-args
  [[":left" "A map containing data to be rendered on the left side of the media component."]
      [":right" "A map containing data to be rendered on the right side of the media component. Supported keys are :content :class and :attrs"]
      [":classes" "A map of strings or vectors of strings specifying CSS classes to add. Supported keys are :media and :content. Supported keys are :content, :class, :attrs"]
      [":attrs" "A map of HTML attributes. Keys are keywordized HTML attribute names"]])

(def media-examples
  [{:text  "
  [:p \"A basic media component\"]
  [m/media \"This is the main media content\"]"
    :code (fn []
            [:<>
             [:p "A basic media component"]
             [m/media "This is the main media content"]])}
   {:text  "
  [:p \"A media component with left content\"]
  [m/media \"This is the main content\"
    :left {:content \"This is the left content\"}]"
    :code (fn []
            [:<>
             [:p "A media component with left content"]
             [m/media "This is the main content"
              :left {:content "This is the left content"}]])}
   {:text  "
  [:p \"A media component with right content\"]
  [m/media \"This is the main content\"
    :right {:content \"This is the right content\"}]"
    :code (fn []
            [:<>
             [:p "A media component with right content"]
             [m/media "This is the main content"
              :right {:content "This is the right content"}]])}
   {:text  "
  [:p \"A more complex media component example with colours\"]
  [m/media \"This is the main content\"
    :left {:content \"This is the left content\"
           :class \"has-background-danger has-text-white\"}
           :right {:content \"This is the right content\"
           :class \"has-background-info has-text-dark\"}
           :classes {:content \"has-text-success\"}]"
    :code (fn []
            [:<>
             [:p "A more complex media component example with colours"]
             [m/media "This is the main content"
              :left {:content "This is the left content"
                     :class "has-background-danger has-text-white"}
              :right {:content "This is the right content"
                      :class "has-background-info has-text-dark"}
              :classes {:content "has-text-success"}]])}])

(defn media-page []
  [doc-fn {:title media-title
           :desc media-desc
           :args media-args
           :opt-args media-opt-args
           :examples media-examples}])

(def ns-sid :ui.sidebar.media-ns)

(def ns-title  "The Media Component")

(def ns-desc
  [:<>
   [:p "The " [:strong "theophilusx.yorick.media"] " namespace provides the "
   [:strong "media"] " component. This component is a general purpose and "
   "flexible component which can be used as a container for almost any type "
    "of content."]
   [:p "The " [:strong "media"] " component has a main " [:strong "body"]
   " and optional " [:strong "left"] " and " [:strong "right"]
   " side content. "]])

(def ns-pages [{:title "media"
                :value :media
                :page media-page}])

(defn ns-page []
  [doc-ns ns-sid ns-title ns-desc ns-pages])


