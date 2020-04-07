(ns theophilusx.yorick.paginate
  (:require [reagent.core :as r]
            [theophilusx.yorick.basic :as basic]))

(defn get-page
  "Return the page associated with a page number."
  [pages page]
  (get pages (keyword (str "page-" page))))

(defn make-link
  "Create a link item for specified page."
  [current page]
  [:li
   [basic/a (str page) :class ["pagination-link"
                               (when (= @current page)
                                 "is-current")]
    :on-click #(reset! current page)]])

(defn paginate
  "Generate a paginated page of records. The `records` argument is a
  sequence of records that will be partitioned into pages. Each record
  in the sequence will be rendered using `page-render-fn`. The optional
  key `:page-size` can be used to set how many records are shown per page
  (defaults to 10). The `is-rounded` key can be set to true to have the
  page navigation items rendered with rounded corners. The keys `:button-size`
  sets the size of navigation buttons and can have the value `:small`,
  `:medium` or `:large`"
  [records page-render-fn & {:keys [page-size is-rounded button-size]
                             :or {page-size 10}}]
  (let [current (r/atom 1)
        pages (r/atom (reduce merge
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
       [:nav.pagination {:class [(case button-size
                                   :small "is-small"
                                   :medium "is-medium"
                                   :large "is-large"
                                   nil)
                                 (when is-rounded "is-rounded")]
                         :role "navigation"
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
