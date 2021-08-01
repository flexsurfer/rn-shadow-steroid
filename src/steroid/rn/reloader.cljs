(ns steroid.rn.reloader
  (:require [reagent.core :as reagent]
            [steroid.rn.components.basic :as basic]
            [steroid.rn.components.ui :as ui]))

(def debug? ^boolean js/goog.DEBUG)

(defonce warning? (reagent/atom false))
(defonce visible (reagent/atom false))
(defonce timeout (atom false))
(defonce label (reagent/atom ""))

(defn build-completed []
  (reset! label "reloading")
  (reset! warning? false)
  (reset! visible true))

(defn build-failed [warnings]
  (reset! warning? true)
  (reset! label (str "building failed"
                     (when (seq warnings)
                       (str "\n" (count warnings) " warnings"))))
  (reset! visible true))

(defn build-start []
  (reset! warning? false)
  (reset! label "building")
  (reset! visible true))

(defn reload-view []
  (fn []
    (when @timeout (js/clearTimeout @timeout))
    (when (= @label "reloading")
      (reset! timeout (js/setTimeout #(do (reset! visible false) (reset! label "")) 1000)))
    (when @visible
      [basic/view {:style         {:position        :absolute :top 0 :left 0 :right 0 :bottom 0
                                   :justify-content :center :align-items :center}}
       [basic/view {:style (merge {:width            64
                                   :height           64
                                   :border-color     :white
                                   :background-color "rgba(67,128,219,1)"
                                   :border-radius    32
                                   :align-items      :center
                                   :justify-content  :center
                                   :border-width     2}
                                  (when @warning?
                                    {:opacity          0.8
                                     :border-color     :red
                                     :background-color "rgba(255,0,0,0.5))"}))}
        [basic/text {:style {:color :white :font-size 30}} "S"]]
       [basic/text {:style {:margin-top 10 :color :white
                            :background-color (if @warning? "rgba(255,0,0,0.5))" "rgba(67,128,219,1)")}}
        @label]
       [ui/button {:title "close"
                   :on-press #(reset! visible false)}]])))

(defonce state (atom nil))

(defn persist-state [state-obj]
  (js/Promise.
   (fn [resolve _]
     (reset! state state-obj)
     (resolve true))))

(defn on-state-change [props]
  (let [handler (or (:onStateChange props) (:on-state-change props))]
    (fn [state]
      (when debug?
        (when handler (handler))
        (persist-state state)))))

(defn build-notify [{:keys [type info]}]
  (cond (= :build-start type)
        (build-start)
        (or (= :build-failure type)
            (and (= :build-complete type) (seq (:warnings info))))
        (build-failed (:warnings info))
        (= :build-complete type)
        (build-completed)))
