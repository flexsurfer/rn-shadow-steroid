(ns steroid.rn.navigation.events
  (:require [re-frame.core :as re-frame]
            [steroid.fx :as fx]
            [steroid.rn.navigation.core :as core]))

(re-frame/reg-fx
 :navigate-to
 (fn [view]
   (when @core/nav-ref
     (.navigate @core/nav-ref view))))

(re-frame/reg-fx
 :navigate-back
 (fn []
   (when @core/nav-ref
     (.goBack @core/nav-ref))))

(fx/defn navigate-to
   {:events [:navigate-to]}
   [_ view]
   {:navigate-to (name view)})

(fx/defn navigate-back
   {:events [:navigate-back]}
   [_]
   {:navigate-back nil})