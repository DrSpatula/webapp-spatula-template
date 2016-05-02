(ns leiningen.new.webapp-spatula
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "webapp-spatula"))

(defn webapp-spatula
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh ClojureScript project using the WebApp Spatula.")
    (->files data
             ["src/{{sanitized}}/core.cljs" (render "core.cljs" data)]
             ["resources/public/index.html" (render "index.html" data)]
             ["project.clj" (render "project.clj" data)]
             [".gitignore" (render ".gitignore" data)])))
