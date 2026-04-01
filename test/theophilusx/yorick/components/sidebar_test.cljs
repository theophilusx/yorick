(ns theophilusx.yorick.components.sidebar-test
  (:require [cljs.test :refer-macros [deftest testing is use-fixtures]]
            [reagent.core :as r]
            [theophilusx.yorick.test-utils :as tu]
            [theophilusx.yorick.sidebar :as sb]
            [theophilusx.yorick.store :as store]
            [theophilusx.yorick.utils :refer [spath]]))

(use-fixtures :each
  {:before tu/setup!
   :after  tu/teardown!})

;; sidebar signature: (sidebar sid menus & {:keys [default]})
;; defmenu: (defmenu entries & {:keys [value title id class icon]})
;; defentry: (defentry title & {:keys [value id class icon]})
;; active-item stored at (conj (spath sid) :active-item)

(def sample-menus
  [(sb/defmenu [(sb/defentry "Home"     :value :home)
                (sb/defentry "Settings" :value :settings)]
               :title "Navigation")])

(deftest sidebar-renders-test
  (testing "renders nav.menu"
    (tu/render! [sb/sidebar :test.sb sample-menus])
    (is (some? (tu/query "nav.menu"))))
  (testing "menu label rendered when :title provided"
    (tu/render! [sb/sidebar :test.sb2 sample-menus])
    (is (= "Navigation" (.-textContent (tu/query ".menu-label"))))))

(deftest sidebar-entries-test
  (testing "correct number of entries rendered"
    (tu/render! [sb/sidebar :test.sb3 sample-menus])
    (is (= 2 (.-length (tu/query-all ".menu-list li")))))
  (testing "clicking an entry updates active item in store"
    (tu/render! [sb/sidebar :test.sb4 sample-menus])
    (let [links (tu/query-all ".menu-list li a")]
      ;; Click "Settings" (second entry)
      (.click (aget links 1))
      (r/flush)
      (is (= :settings (store/get-in (conj (spath :test.sb4) :active-item)))))))
