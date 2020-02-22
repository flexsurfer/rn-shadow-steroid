(ns steroid.rn.navigation.core
  (:require ["@react-navigation/native" :refer [NavigationContainer]]
            [reagent.core :as reagent]
            [re-frame.core :as re-frame]))

(defn adapt-class [class]
  (when class
    (reagent/adapt-react-class class)))

(def navigation-container (adapt-class NavigationContainer))

(def nav-ref (atom nil))
(def callback (atom nil))

(defn nav-ref-handler [ref]
  (reset! nav-ref ref)
  (when @callback
    (@callback)))

(defn create-mount-handler [cb]
  (fn []
    (if @nav-ref
      (when cb (cb))
      (reset! callback cb))))