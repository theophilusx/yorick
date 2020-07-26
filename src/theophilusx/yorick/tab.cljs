(ns theophilusx.yorick.tab
  "Provide basic page tabs for navigating between pages of information."
  (:require [theophilusx.yorick.basic :refer [a]]
            [theophilusx.yorick.utils :refer [cs spath value->keyword]]
            [theophilusx.yorick.store :refer [get-in assoc-in! global-state]]))

(defn deftab
  "Returns a tab definition map for a tab navigation bar. The `title` argument
  is the text label for the tab. The function accepts the following optional
  keyword arguments:

  | Keyword      | Description                                                |
  |--------------|------------------------------------------------------------|
  | `:id`        | a unique value used as the value set in the global state   |
  |              | when a tab is selected. If not specified, the title,       |
  |              | converted to a keyword, will be used as the default        |
  | `:icon-data` | an icon data map defining an icon to add to the tab.       |
  |              | see `theophilusx.yorick.icon` for details on map structure |
  | `:class`     | a string or vector of strings specifying additional CSS    |
  |              | class names to add to the tab link                         |"
  [title & {:keys [id icon-data class]}]
  {:title title
   :id (or id (value->keyword title))
   :icon-datat icon-data
   :class class})

(defn tab
  "Generates a horizontal 'tab' navigation bar. The `sid` storage identifier
  keyword specifies the key where the selected tab id will be stored in the
  global state defined by `theophilusx.yorick.store/globale-state`. The `tabs`
  argument is a vector of tab definition maps. See `deftab` function for a way
  to generate tab definition maps and the keys supported in the map. The
  following optional keyword arguments are also supported:

  | Keyword     | Description                                                 |
  |-------------|-------------------------------------------------------------|
  | `:position` | Set the position of the tab bar. Possible values are        |
  |             | `:center` and `:right`. If not specified, tab bar is        |
  |             | aligned to the left.                                        |
  | `:class`    | A string or vector of strings specifying CSS class names    |
  |             | for classes to add to the tab bar                           |
  | `:size`     | Set the size of the tab bar. Possible values are `:small`   |
  |             | `:medium` and `:large`                                      |
  | `:boxed`    | If true, put borders around tabs to give them a traditional |
  |             | boxed appearance.                                           |
  | `:toggle`   | If true, make tab bar toggle e.g. like a radio button       |
  | `:rounded`  | If true and if `:boxed`, round the edge and corners of the  |
  |             | left most and right most tabs.                              |"
  [sid tabs & _]
  (assoc-in! global-state (spath sid) (:id (first tabs)))
  (fn [sid tabs & {:keys [position class size boxed toggle rounded]}]
    [:div.tabs {:class (cs class
                           (case position
                             :center "is-centered"
                             :right  "is-right"
                             nil)
                           (case size
                             :small  "is-small"
                             :medium "is-medium"
                             :large  "is-large"
                             nil)
                           (when boxed "is-boxed")
                           (when toggle "is-toggle")
                           (when rounded "is-toggle-rounded"))
                }
     (into
      [:ul]
      (for [t tabs]
        [:li {:class (cs (when (= (get-in global-state (spath sid)) (:id t))
                           "is-active"))}
         [a (:title t) :attrs {:id (:id t)} :icon-data (:icon-data t)
          :class (:class t) :on-click #(assoc-in! global-state
                                                  (spath sid) (:id t))]]))]))


