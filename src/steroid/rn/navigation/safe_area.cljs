(ns steroid.rn.navigation.safe-area
  (:require ["react-native-safe-area-context"
             :refer (SafeAreaView SafeAreaProvider SafeAreaConsumer)]
            [reagent.core :as reagent]))

(def safe-area-provider (reagent/adapt-react-class SafeAreaProvider))
(def safe-area-consumer (reagent/adapt-react-class SafeAreaConsumer))
(def safe-area-view (reagent/adapt-react-class SafeAreaView))