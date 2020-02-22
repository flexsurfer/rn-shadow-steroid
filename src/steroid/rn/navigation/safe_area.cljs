(ns steroid.rn.navigation.safe-area
  (:require ["react-native-safe-area-context" :refer [SafeAreaProvider SafeAreaView]]
            [reagent.core :as reagent]))

(defn- adapt-class [class]
  (when class
    (reagent/adapt-react-class class)))

(def safe-area-provider (adapt-class SafeAreaProvider))
(def safe-area-view (adapt-class SafeAreaView))