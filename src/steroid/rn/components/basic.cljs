(ns steroid.rn.components.basic
  (:require [reagent.core :as reagent]
            ["react-native" :as rn]))

(def view (reagent/adapt-react-class rn/View))
(def text (reagent/adapt-react-class rn/Text))
(def image (reagent/adapt-react-class rn/Image))
(def text-input (reagent/adapt-react-class rn/TextInput))
(def scroll-view (reagent/adapt-react-class rn/ScrollView))