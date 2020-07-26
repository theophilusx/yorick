(ns theophilusx.yorick.paginate
  "Provides a data pagination component to present data in pages of information."
  (:require [reagent.core :as r]
            [theophilusx.yorick.basic :as basic]
            [theophilusx.yorick.utils :refer [cs]]))

(defn get-page
  "Return the page associated with a page number."
  [pages page]
  (get pages (keyword (str "page-" page))))

(defn make-link
  "Create a link item for specified page."
  [current page]
  [:li
   [basic/a (str page) :class (cs "pagination-link"
                                  (when (= @current page)
                                    "is-current"))
    :on-click #(reset! current page)]])

(defn paginate
  "Generate a paginated page of records. The `records` argument is a
  sequence of records that will be partitioned into pages. Each record
  in the sequence will be rendered using `page-render-fn`. The following
  optional keyword arguments are also supported:

  | Keyword        | Description                                           |
  |----------------|-------------------------------------------------------|
  | `:page-size`   | number of records to show per page. Default 10        |
  | `:rounded`  | if true, page navigation items have a rounded look    |
  | `:button-size` | sets the size of navigation buttons. Supported values |
  |                | are `:small`, `:medium` and `:large`                  |"
  [records page-render-fn & {:keys [page-size rounded button-size]
                             :or   {page-size 10}}]
  (let [current (r/atom 1)
        pages   (r/atom (reduce merge
                              (map-indexed
                               (fn [idx v]
                                 {(keyword (str "page-" (inc idx)))
                                  (vec v)})
                               (partition-all page-size records))))]
    (fn [data & _]
      (reset! pages (reduce merge
                            (map-indexed
                             (fn [idx v]
                               {(keyword (str "page-" (inc idx)))
                                (vec v)})
                             (partition-all page-size data))))
      (when (> @current (count (keys @pages)))
        (reset! current (count (keys @pages))))
      [:<>
       [:nav.pagination {:class      (cs (case button-size
                                           :small  "is-small"
                                           :medium "is-medium"
                                           :large  "is-large"
                                           nil)
                                    (when rounded "is-rounded"))
                         :role       "navigation"
                         :aria-label "pagination"}
        (when-not (= @current 1)
          [basic/a "Previous" :class "pagination-previous"
           :on-click #(swap! current dec)])
        (when-not (= @current (count (keys @pages)))
          [basic/a "Next" :class "pagination-next"
           :on-click #(swap! current inc)])
        (cond
          (<= (count (keys @pages)) 4)
          (into
           [:ul.pagination-list]
           (for [i (range 1 (inc (count (keys @pages))))]
             [make-link current i]))
          (< @current 4)
          (into
           [:ul.pagination-list]
           (for [i [1 2 3 4 (count (keys @pages))]]
             (if (= i 4)
               [:li [basic/a "\u2026" :class "pagination-ellipsis"]]
               [make-link current i])))
          (> @current (- (count (keys @pages)) 4))
          (into
           [:ul.pagination-list
            [make-link current 1]]
           (for [i (range (- (count (keys @pages)) 4))]
             (if (= (- (count (keys @pages)) 4) i)
               [:li [basic/a "\u2026" :class "pagination-ellipsis"]]
               [make-link current i])))
          :else
          (into
           [:ul.pagination-list
            [make-link current 1]]
           (for [i (range (- @current 2) (+ @current 3))]
             (if (or (= (- @current 2) i)
                     (= (+ @current 2) i))
               [:li [basic/a "\u2026" :class "pagination-ellipsis"]]
               (if (= (+ @current 3) i)
                 [make-link current (count (keys @pages))]
                 [make-link current i])))))]
       (page-render-fn (get-page @pages @current))])))
