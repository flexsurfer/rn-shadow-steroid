(ns steroid.rn.utils
  (:require [reagent.core :as reagent]))

(defn clj->json [data]
  (.stringify js/JSON (clj->js data)))

(defn json->clj [json]
  (when-not (= json "undefined")
    (try
      (js->clj (.parse js/JSON json))
      (catch js/Error _ (when (string? json) json)))))

(def serialize clj->json)

(defn deserialize [o]
  (try (json->clj o)
       (catch :default _ nil)))

(defn prepare-navigator [navigator screen]
  (fn [& params]
    (let [[props children] (if (map? (first params))
                             [(first params) (second params)]
                             [{} (first params)])]
      (into [navigator props]
            (mapv (fn [props]
                    [screen (update props :component reagent/reactify-component)])
                  children)))))