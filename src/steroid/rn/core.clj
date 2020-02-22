(ns steroid.rn.core)

(defmacro reload-comp [comp]
  (let [props (gensym "props")]
    `(reagent.core/reactify-component
      (fn [~props]
        @steroid.rn.core/cnt
        [~comp ~props]))))

(defmacro register-reload-comp [name app-root]
  (let [props (gensym "props")]
    `(.registerComponent
      steroid.rn/app-registry
      ~name
      #(reagent.core/reactify-component
        (fn [~props]
          @steroid.rn.core/cnt
          [~app-root ~props])))))