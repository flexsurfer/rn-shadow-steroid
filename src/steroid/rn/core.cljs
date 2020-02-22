(ns steroid.rn.core
  (:require-macros steroid.rn.core)
  (:require ["react-native" :as rn]
            [reagent.core :as reagent]))

(defn- adapt-class [class]
  (when class
    (reagent/adapt-react-class class)))

(def activity-indicator (adapt-class rn/ActivityIndicator))
(def button (adapt-class rn/Button))
(def flat-list-class (adapt-class rn/FlatList))
(def image (adapt-class rn/Image))
(def image-background (adapt-class rn/ImageBackground))
(def input-accessory-view (adapt-class rn/InputAccessoryView))
(def modal (adapt-class rn/Modal))
(def picker (adapt-class rn/Picker))
(def refresh-control (adapt-class rn/RefreshControl))
(def safe-area-view (adapt-class rn/SafeAreaView))
(def scroll-view (adapt-class rn/ScrollView))
(def section-list (adapt-class rn/SectionList))
(def status-bar (adapt-class rn/StatusBar))
(def switch (adapt-class rn/Switch))
(def text (adapt-class rn/Text))
(def text-input (adapt-class rn/TextInput))
(def toolbar-android (adapt-class rn/ToolbarAndroid))
(def touchable-highlight (adapt-class rn/TouchableHighlight))
(def touchable-native-feedback (adapt-class rn/TouchableNativeFeedback))
(def touchable-opacity (adapt-class rn/TouchableOpacity))
(def touchable-without-feedback (adapt-class rn/TouchableWithoutFeedback))
(def view (adapt-class rn/View))
(def virtualized-list (adapt-class rn/VirtualizedList))

(def app-registry rn/AppRegistry)

;; FLATLIST
(defn- wrap-render-fn [f]
  (fn [data]
    (reagent/as-element (f (.-item data) (.-index data) (.-separators data)))))

(defn- wrap-key-fn [f]
  (fn [data index]
    {:post [(some? %)]}
    (f data index)))

(defn- base-list-props
  [{:keys [key-fn keyExtractor key-extractor
           render-fn renderItem render-item
           empty-component ListEmptyComponent list-empty-component
           header ListHeaderComponent lList-header-component
           footer ListFooterComponent list-footer-component
           separator ItemSeparatorComponent item-separator-component]}]
  (merge (let [key-fn (or key-fn keyExtractor key-extractor)]
           {:keyExtractor (if key-fn (wrap-key-fn key-fn) (fn [data index] (str index)))})
         (when-let [render-fn (or render-fn renderItem render-item)]
           {:renderItem (wrap-render-fn render-fn)})
         (when-let [separator (or separator ItemSeparatorComponent item-separator-component)]
           {:ItemSeparatorComponent (fn [] (reagent/as-element separator))})
         (when-let [empty-component (or empty-component ListEmptyComponent list-empty-component)]
           {:ListEmptyComponent (fn [] (reagent/as-element empty-component))})
         (when-let [header (or header ListHeaderComponent lList-header-component)]
           {:ListHeaderComponent (reagent/as-element header)})
         (when-let [footer (or footer ListFooterComponent list-footer-component)]
           {:ListFooterComponent (reagent/as-element footer)})))

(defn flat-list [{:keys [data] :as props}]
  {:pre [(or (nil? data)
             (sequential? data))]}
  [flat-list-class
   (merge (base-list-props props)
          props
          {:data (to-array data)})])

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