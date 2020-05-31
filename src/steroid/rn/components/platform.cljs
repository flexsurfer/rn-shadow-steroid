(ns steroid.rn.components.platform
  (:require ["react-native" :as rn]))

(def platform
  (.-Platform rn))

(def os
  (when platform
    (.-OS ^js platform)))

(def version
  (when platform
    (.-Version ^js platform)))

(def android? (= os "android"))
(def ios? (= os "ios"))