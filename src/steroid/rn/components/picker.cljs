(ns steroid.rn.components.picker
  (:require [reagent.core :as reagent]
            ["@react-native-community/picker" :refer (Picker)]))

(def picker (reagent/adapt-react-class Picker))
(def item (reagent/adapt-react-class (.-Item Picker)))