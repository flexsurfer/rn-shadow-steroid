(ns steroid.rn.navigation.bottom-tabs
  (:require ["@react-navigation/bottom-tabs" :refer [createBottomTabNavigator]]
            [reagent.core :as reagent]))

(defn adapt-class [class]
  (when class
    (reagent/adapt-react-class class)))

(defn create-bottom-tab-navigator []
  (let [tab (createBottomTabNavigator)]
    [(adapt-class (.-Navigator tab)) (adapt-class (.-Screen tab))]))