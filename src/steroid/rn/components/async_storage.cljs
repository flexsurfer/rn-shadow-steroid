(ns steroid.rn.components.async-storage
  (:require ["@react-native-community/async-storage" :default AsyncStorage]
            [steroid.rn.utils :as utils]))

(defn set-item [key value]
  (.setItem AsyncStorage key (utils/serialize value)))

(defn get-item [key cb]
  (.. AsyncStorage
      (getItem key)
      (then #(cb (utils/deserialize %)))))