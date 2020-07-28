(defproject theophilusx/yorick "1.0.1"
  :description "A library of Reagent components styled using Bulma"
  :url "https://github.com/theophilusx/yorick"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.773"]
                 [reagent "0.10.0"]]
  :source-paths ["src"]
  :plugins [[lein-codox "0.10.7"]]
  :codox {:source-url "https://github.com/theophilusx/yorick/blob/master/{filepath}#{line}"
          :metadata {:doc/format :markdown}
          :output-path "docs"
          :namespaces :all
          :language :clojurescript})

