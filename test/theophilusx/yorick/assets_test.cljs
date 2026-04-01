(ns theophilusx.yorick.assets-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [cljs.nodejs :as nodejs]))

(def fs (nodejs/require "fs"))
(def path (nodejs/require "path"))

(deftest css-symlink-test
  (testing "resources/public/css symlink exists"
    (is (.existsSync fs "resources/public/css")
        "resources/public/css is missing — recreate with: ln -s ../../node_modules/bulma/css resources/public/css"))
  (testing "resources/public/css is a symlink"
    (let [stat (.lstatSync fs "resources/public/css")]
      (is (.isSymbolicLink stat)
          "resources/public/css should be a symlink to ../../node_modules/bulma/css")))
  (testing "resources/public/css symlink target is correct"
    (is (= "../../node_modules/bulma/css"
           (.readlinkSync fs "resources/public/css"))
        "Symlink target has changed — expected ../../node_modules/bulma/css"))
  (testing "bulma.min.css is accessible via the symlink"
    (is (.existsSync fs "resources/public/css/bulma.min.css")
        "bulma.min.css not found — node_modules/bulma may not be installed")))
