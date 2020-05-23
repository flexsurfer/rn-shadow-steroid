(ns steroid.rn.components.touchable
  (:require [reagent.core :as reagent]
            ["react-native" :as rn]))

(def touchable-highlight (reagent/adapt-react-class rn/TouchableHighlight))
(def touchable-native-feedback (reagent/adapt-react-class rn/TouchableNativeFeedback))
(def touchable-opacity (reagent/adapt-react-class rn/TouchableOpacity))
(def touchable-without-feedback (reagent/adapt-react-class rn/TouchableWithoutFeedback))