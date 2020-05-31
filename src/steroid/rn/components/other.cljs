(ns steroid.rn.components.other
  (:require [reagent.core :as reagent]
            ["react-native" :as rn]))

(def activity-indicator (reagent/adapt-react-class rn/ActivityIndicator))
(def alert (reagent/adapt-react-class rn/Alert))
(def dimensions rn/Dimensions)
(def keyboard-avoiding-view (reagent/adapt-react-class rn/KeyboardAvoidingView))
(def modal (reagent/adapt-react-class rn/Modal))
(def refresh-control (reagent/adapt-react-class rn/RefreshControl))
(def status-bar (reagent/adapt-react-class rn/StatusBar))
