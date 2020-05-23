(ns steroid.rn.navigation.bottom-tabs
  (:require ["@react-navigation/bottom-tabs" :refer (createBottomTabNavigator)]
            [reagent.core :as reagent]
            [steroid.rn.utils :as utils]))

(defn create-bottom-tab-navigator []
  (let [^js tab (createBottomTabNavigator)]
    [(reagent/adapt-react-class (.-Navigator tab))
     (reagent/adapt-react-class (.-Screen tab))]))

(defn bottom-tab [& params]
   (let [[navigator screen] (create-bottom-tab-navigator)]
     (utils/prepare-navigator navigator screen)))