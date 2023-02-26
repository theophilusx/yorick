(defproject theophilusx/yorick "2.0.0"
  :description "A library of Reagent components styled using Bulma"
  :url "https://github.com/theophilusx/yorick"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/clojurescript "1.11.60"]
                 [reagent "1.2.0"]]
  :source-paths ["src"]
  :plugins [[lein-codox "0.10.8"]]
  :codox {:source-url "https://github.com/theophilusx/yorick/blob/master/{filepath}#{line}"
          :metadata {:doc/format :markdown}
          :output-path "docs"
          :namespaces :all
          :language :clojurescript})

