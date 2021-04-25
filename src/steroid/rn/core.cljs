(ns steroid.rn.core
  (:require-macros steroid.rn.core)
  (:require ["react-native" :as rn]
            [reagent.core :as reagent]
            [steroid.rn.components.basic :as basic]
            [steroid.rn.components.touchable :as touchable]
            [steroid.rn.reloader :as reloader]))

(def debug? ^boolean js/goog.DEBUG)

(def app-registry rn/AppRegistry)

(def view basic/view)
(def text basic/text)
(def image basic/image)
(def text-input basic/text-input)
(def scroll-view basic/scroll-view)

(def touchable-highlight touchable/touchable-highlight)
(def touchable-native-feedback touchable/touchable-native-feedback)
(def touchable-opacity touchable/touchable-opacity)
(def touchable-without-feedback touchable/touchable-without-feedback)

(def reload-view reloader/reload-view)

;; REACtIFY
(defn react-comp [value]
  (reagent/reactify-component value))

(defn register-comp [name app-root]
  (.registerComponent
   app-registry
   name
   #(react-comp app-root)))

;; HOTRELOAD
(defonce cnt (reagent/atom 0))

(defn reload []
  (swap! cnt inc))

(def build-notify reloader/build-notify)