(ns {{name}}.core
  (:require [ajax.core :refer [GET POST]] 
            [secretary.core :as secretary :refer-macros [defroute]]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [reagent.core :as r])
  (:import goog.History))

;; ======= ROUTING SETUP ======
;; Atom to hold current page selection
(def app-state (r/atom {}))


;; Hook into browser navigation for routing
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
      EventType/NAVIGATE
      (fn [event]
        (secretary/dispatch! (.-token event))))
    (.setEnabled true)))


;; Define routes
(defn routes []
  (secretary/set-config! :prefix "#")
  
  (defroute "/" []
    (swap! app-state assoc :page :page1))
  
  (defroute "/page2" []
    (swap! app-state assoc :page :page2))
  
  (hook-browser-navigation!))


;; Sample components to render for routes
(defn page1 [] 
  [:div
   [:h1 "Page 1"]
   [:a {:href "#/page2"} "Page Two"]])


(defn page2 [] 
  [:div
   [:h1 "Page 2"]
   [:a {:href "#/"} "Page One"]])


;;  Mulitmethod to handle change of current page in app-state
(defmulti current-page #(:page @app-state))
(defmethod current-page :page1 []
  [page1])
(defmethod current-page :page2 []
  [page2])
(defmethod current-page :default []
  [:div ])


;; ====== AJAX Sample Handlers ======
(defn handler [response]
  (.log js/console (str response)))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console (str "somthing went wrong with your ajax request: " status " " status-text)))

;; Initialize routing
(routes)

;; Initialize rendering
(r/render [current-page] (.getElementById js/document "app"))

;; Make sample AJAX call
(GET "http://jsonplaceholder.typicode.com/posts/1" {:handler handler
                                                    :error-handler error-handler})


