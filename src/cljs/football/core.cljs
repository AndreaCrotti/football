(ns football.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)

(def players
  [{:name "holly" :shoot 10}
   {:name "benji" :shoot 8}])

(nth players 1)


(defonce app-state (atom {:text "Football selection"}))

(defn new-player-view [dest owner]
  (om/component
   (dom/div nil
            (dom/h3 nil "Add new player"))))

(defn main []
  (om/root
    (fn [app owner]
      (reify
        om/IRender
        (render [_]
          (dom/h1 nil (:text app)))))
    app-state
    {:target (. js/document (getElementById "app"))}))
