(ns steroid.rn.core)

(defmacro reload-comp [comp]
  (let [props (gensym "props")]
    `(reagent.core/reactify-component
      (fn [~props]
        (when steroid.rn.core/debug?
          @steroid.rn.core/cnt)
        [~comp ~props]))))

(defmacro register-reload-comp [name app-root]
  (let [props (gensym "props")]
    `(.registerComponent
      steroid.rn.core/app-registry
      ~name
      #(reagent.core/reactify-component
        (fn [~props]
          (when steroid.rn.core/debug?
            @steroid.rn.core/cnt)
          [steroid.rn.core/view {:style {:flex 1}}
           [~app-root ~props]
           (when steroid.rn.core/debug?
             [steroid.rn.core/reload-view])])))))