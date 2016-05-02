(defproject {{name}} "0.1.0-SNAPSHOT"
  :description "ClojureScript project setup to be templatized"
  
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.8.51"]
                 [reagent "0.6.0-alpha"]
                 [secretary "1.2.3"]
                 [cljs-ajax "0.5.4"]]

  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-figwheel "0.5.2"]]

  :clean-targets ^{:protect false} ["resources/public/js/compiled"]
  
  :cljsbuild {
              :builds [{:id "dev"
                        :source-paths ["src"]
                        :figwheel true
                        :compiler {:main "{{name}}.core"
                                   :output-to "resources/public/js/compiled/main.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :asset-path "js/compiled/out"
                                   :optimizations :none
                                   :source-map true}}
                       {:id "prod"
                        :source-paths ["src"]
                        :compiler {:main "{{name}}.core"
                                   :output-to "resources/public/js/compiled/main.js"
                                   :optimizations :advanced}}]})
