(ns testbed.paginate
  "Provides a data pagination component to present data in pages of information.
  Component accepts a vector of records and a render function used to display
  a page of records. Optional keyword arguments are supported for controlling
  number of records per page and appearance of page index."
  (:require [reagent.core :as r]
            [theophilusx.yorick.basic :as basic]
            [theophilusx.yorick.utils :refer [cs]]))

(defn- get-page
  "Return the page associated with a page number."
  [pages page]
  (get pages (keyword (str "page-" page))))

(defn- make-link
  "Create a link item for specified page."
  [current page]
  [:li
   [basic/a (str page) :class (cs "pagination-link"
                                  (when (= @current page)
                                    "is-current"))
    :on-click #(reset! current page)]])

(defn- partition-pages
  "Partition collection into pages with `size` records per page."
  [size coll]
  (reduce merge
          (map-indexed
           (fn [idx v]
             {(keyword (str "page-" (inc idx)))
              (vec v)})
           (partition-all size coll))))

(defn- prev-page [current]
  [basic/a "Previous" :class (cs "pagination-previous"
                                 (when (= @current 1) "is-disabled"))
   :on-click (when-not (= @current 1)
               #(swap! current dec))])

(defn- next-page [current max]
  [basic/a "Next" :class (cs "pagination-next"
                             (when (= @current max) "is-disabled"))
   :on-click (when-not (= @current max)
               #(swap! current inc))])

(defn make-index
  "Generate the pagination links for navigating pages of records.
  `current` is the current page number and `max-page` is the maximum
  page number."
  [current max-page]
  (cond
    (<= max-page 4) (into [:ul.pagination-list]
                          (for [i (range 1 (inc max-page))]
                            [make-link current i]))
    (< @current 4) (into [:ul.pagination-list]
                         (for [i [1 2 3 4 max-page]]
                           (if (= i 4)
                             [:li [basic/a "\u2026" :class "pagination-ellipsis"]]
                             [make-link current i])))
    (> @current (- max-page 3)) (into [:ul.pagination-list [make-link current 1]]
                                      (for [i (range (- max-page 4) (inc max-page))]
                                        (if (= i (- max-page 4))
                                          [:li [basic/a "\u2026" :class "pagination-ellipsis"]]
                                          [make-link current i])))
    :else
    (into [:ul.pagination-list
           [make-link current 1]]
          (for [i (concat (range (- @current 2) (+ @current 3)) [max-page])]
            (if (or (= i (- @current 2))
                    (= i (+ @current 2)))
              [:li [basic/a "\u2026" :class "pagination-ellipsis"]]
              [make-link current i])))))

(defn paginate
  "Generate a paginated page of records. The `records` argument is a
  sequence of records that will be partitioned into pages. Each record
  in the sequence will be rendered using `render-fn`. The following
  optional keyword arguments are also supported:

  | Keyword        | Description                                           |
  |----------------|-------------------------------------------------------|
  | `:page-size`   | number of records to show per page. Default 10        |
  | `:rounded?`     | if true, page navigation items have a rounded look    |
  | `:button-size` | sets the size of navigation buttons. Supported values |
  |                | are `:small`, `:medium` and `:large`                  |"
  [records _ & {:keys [page-size] :or {page-size 10}}]
  (let [current (r/atom 1)
        pages   (r/atom (partition-pages page-size records))
        max-page (r/atom (count (keys @pages)))]
    (fn [_ render-fn & {:keys [rounded? button-size]}]
      [:<>
       [:nav.pagination {:class      (cs (when button-size
                                           (str "is-" (name button-size)))
                                         (when rounded? "is-rounded"))
                         :role       "navigation"
                         :aria-label "pagination"}
        [prev-page current]
        [next-page current @max-page]
        [make-index current @max-page]]
       (render-fn (get-page @pages @current))])))
