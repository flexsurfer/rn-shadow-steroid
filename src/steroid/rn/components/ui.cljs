(ns steroid.rn.components.ui
  (:require [reagent.core :as reagent]
            ["react-native" :as rn]))

(def button (reagent/adapt-react-class rn/Button))
(def switch (reagent/adapt-react-class rn/Switch))