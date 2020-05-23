(ns steroid.rn.navigation.stack
  (:require ["@react-navigation/stack" :refer [createStackNavigator]]
            [reagent.core :as reagent]
            [steroid.rn.core :as rn]
            [steroid.rn.utils :as utils]))

(defn create-stack-navigator []
  (let [^js stack (createStackNavigator)]
    [(reagent/adapt-react-class (.-Navigator stack))
     (reagent/adapt-react-class (.-Screen stack))]))

(defn stack [& params]
  (let [[navigator screen] (create-stack-navigator)]
    (utils/prepare-navigator navigator screen)))