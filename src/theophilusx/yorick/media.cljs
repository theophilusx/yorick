(ns theophilusx.yorick.media
  "Provides a general purpose media presentation component."
  (:require [theophilusx.yorick.utils :refer [cs]]))

(defn media-left
  "A simple container for media content which is placed to the left. The
  `content` argument is the content for the left. This component is usually
  called as part of the main media component rather than separately as a
  stand-alone component. The component also supports the following optional
  keywords:

  | Keyword | Description |
  | `:class` | A string or vector of strings representing CSS class names  |
  | `:attrs` | a map of HTML attribute values. The keys are HTML attribute |
  |          | names as keywords e.g. `:id`                                |"
  [content & {:keys [class attrs]}]
  [:aside.media-left (merge attrs {:class (cs class)})
   content])

(defn media-right
  "A simple container for media content which is placed to the right. The
  `content` argument is the content for the left. This component is usually
  called as part of the main media component rather than separately as a
  stand-alone component. The component also supports the following optional
  keywords:

  | Keyword | Description |
  | `:class` | A string or vector of strings representing CSS class names  |
  | `:attrs` | a map of HTML attribute values. The keys are HTML attribute |
  |          | names as keywords e.g. `:id`                                |"
  [content & {:keys [class attrs]}]
  [:aside.media-right (merge attrs {:class (cs class)})
   content])

(defn media
  "A general purpose media component. The component allows for a main media
  sections and left and right media sections. The `contents` argument is used
  for the main media content. The component supports the following optional
  keywords:

  | Keyword    | Description                                                  |
  |-------==---|--------------------------------------------------------------|
  | `:classes` | a map of strings or vectors of strings representing CSS      |
  |            | class names to be associated with the component. Supported   |
  |            | keys are `:media` and `:content`                             |
  | `:attrs`   | a map of HTML attribute values. The keys are HTML attribute  |
  |            | names as keywords e.g. `:id`                                 |
  | `:left`    | a map defining content for the left of the media component.  |
  |            | supported keys are `:content`, `:class` and `:attrs`         |
  | `:right`   | a map defining content for the right of the media component. |
  |            | Supported keys are `:content`, `:class` and `:attrs`         |

  The keys for the `:left` and `:right` maps are

  | Keyword    | Description                                                |
  |------------|------------------------------------------------------------|
  | `:content` | the content to render                                      |
  | `:class`   | a string or vector of strings representing CSS class names |
  | `:attrs`   | a map of HTML attribute values. Keys are HTML attribute    |
  |            | names as keywords e.g. `:id`                               |"
  [content & {:keys [left right classes attrs]}]
  [:article.media (merge attrs {:class (cs (:media classes))})
   (when left
     [media-left (:content left) :class (:class left) :attrs (:attrs left)])
   [:div.media-content (merge attrs {:class (cs (:content classes))})
    content]
   (when right
     [media-right (:content right) :class (:class right) :attrs (:attrs right)])])

