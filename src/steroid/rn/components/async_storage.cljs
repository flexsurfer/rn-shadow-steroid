(ns steroid.rn.components.async-storage
  (:require ["@react-native-async-storage/async-storage" :default AsyncStorage]
            [steroid.rn.utils :as utils]))

(defn set-item [key value]
  (.setItem AsyncStorage key (utils/serialize value)))

(defn get-item [key cb]
  (.. AsyncStorage
      (getItem key)
      (then #(cb (utils/deserialize %)))))

(defn get-all-keys [cb]
  (.. AsyncStorage
      (getAllKeys)
      (then #(cb (js->clj %)))))

(defn remove-item
  ([key] (remove-item key #()))
  ([key cb]
   (.. AsyncStorage
       (removeItem key)
       (then #(cb)))))

(defn clear
  ([] (clear #()))
  ([cb]
   (.. AsyncStorage
       (clear)
       (then #(cb)))))
