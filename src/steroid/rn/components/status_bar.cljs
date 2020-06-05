(ns steroid.rn.components.status-bar
  (:require ["react-native" :as rn]))

(def status-bar rn/StatusBar)

(defn set-bar-style [bar-style]
  (.setBarStyle ^js status-bar (clj->js bar-style)))

(defn set-background-color [background-color]
  (.setBackgroundColor ^js status-bar (clj->js background-color)))

(defn set-hidden [hidden]
  (.setHidden ^js status-bar (clj->js hidden)))

(defn set-network-activity-indicator-visible [network-activity-indicator-visible]
  (.setNetworkActivityIndicatorVisible
   ^js status-bar
   (clj->js network-activity-indicator-visible)))

(defn set-translucent [translucent]
  (.setTranslucent ^js status-bar (clj->js translucent)))