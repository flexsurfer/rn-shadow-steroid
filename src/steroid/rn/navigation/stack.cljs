(ns steroid.rn.navigation.stack
  (:require ["@react-navigation/stack" :refer [createStackNavigator]]
            [reagent.core :as reagent]))

(defn- adapt-class [class]
  (when class
    (reagent/adapt-react-class class)))

(defn create-stack-navigator []
  (let [stack (createStackNavigator)]
    [(adapt-class (.-Navigator stack)) (adapt-class (.-Screen stack))]))