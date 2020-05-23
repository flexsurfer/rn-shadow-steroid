(ns steroid.rn.navigation.core)

(defmacro create-navigation-container-reload
  ([child] `(create-navigation-container-reload {} ~child))
  ([props child]
   `(do
      (when (or (:initialState ~props) (:initial-state ~props))
        (reset! steroid.rn.reloader/state (or (:initialState ~props) (:initial-state ~props))))
      (reagent.core/create-class
       {:component-did-mount
        (steroid.rn.navigation.core/create-mount-handler
         #(when (:on-ready ~props)
            ((:on-ready ~props))))
        :reagent-render
        (fn []
          ^{:key (str "root" @steroid.rn.core/cnt)}
          [steroid.rn.core/view {:style {:flex 1}}
           [steroid.rn.navigation.core/navigation-container
            (merge (dissoc ~props :on-ready)
                   {:ref #(do (steroid.rn.navigation.core/nav-ref-handler %)
                              (when (:ref ~props) ((:ref ~props) %)))}
                   (when steroid.rn.core/debug?
                     {:onStateChange (steroid.rn.reloader/on-state-change ~props)
                      :initialState  @steroid.rn.reloader/state}))
            ~child]
           (when steroid.rn.core/debug?
             [steroid.rn.reloader/reload-view])])}))))