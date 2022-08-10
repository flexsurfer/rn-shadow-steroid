(ns steroid.rn.components.vector-icons
  (:require [reagent.core :as reagent]
            ["@expo/vector-icons" :as vi]))

(def icon (reagent/adapt-react-class vi/Ionicons))
(def button (reagent/adapt-react-class (.-Button vi/FontAwesome)))
