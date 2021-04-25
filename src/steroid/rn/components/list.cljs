(ns steroid.rn.components.list
  (:require [reagent.core :as reagent]
            [steroid.rn.components.basic :as basic]
            ["react-native" :as rn]))

(defn- wrap-render-fn [f render-data]
  (fn [^js data]
    (reagent/as-element [f (.-item data) (.-index data) (.-separators data) render-data])))

(defn- wrap-key-fn [f]
  (fn [data index]
    {:post [(some? %)]}
    (f data index)))

(defn- base-list-props
  [{:keys [key-fn keyExtractor key-extractor
           render-fn renderItem render-item
           empty-component ListEmptyComponent list-empty-component
           header ListHeaderComponent list-header-component
           footer ListFooterComponent list-footer-component
           separator ItemSeparatorComponent item-separator-component render-data]}]
  (merge (let [key-fn (or key-fn keyExtractor key-extractor)]
           {:keyExtractor (if key-fn (wrap-key-fn key-fn) (fn [_ index] (str index)))})
         (when-let [render-fn (or render-fn renderItem render-item)]
           {:renderItem (wrap-render-fn render-fn render-data)})
         (when-let [separator (or separator ItemSeparatorComponent item-separator-component)]
           {:ItemSeparatorComponent (fn [] (reagent/as-element separator))})
         (when-let [empty-component (or empty-component ListEmptyComponent list-empty-component)]
           {:ListEmptyComponent (fn [] (reagent/as-element empty-component))})
         (when-let [header (or header ListHeaderComponent list-header-component)]
           {:ListHeaderComponent (reagent/as-element header)})
         (when-let [footer (or footer ListFooterComponent list-footer-component)]
           {:ListFooterComponent (reagent/as-element footer)})))

;; FLATLIST

(def flat-list-class (reagent/adapt-react-class rn/FlatList))

(defn flat-list [{:keys [data] :as props}]
  {:pre [(or (nil? data)
             (sequential? data))]}
  [flat-list-class
   (merge (base-list-props props)
          props
          {:data (to-array data)})])

;;SECTION LIST

(def section-list-class (reagent/adapt-react-class rn/SectionList))

(defn- wrap-render-section-header-fn [f]
  (fn [^js data]
    (let [^js section (.-section data)]
      (reagent/as-element (f {:title (.-title section)
                              :data  (.-data section)})))))

(defn- wrap-per-section-render-fn [props]
  (update
   (if-let [f (:render-fn props)]
     (assoc (dissoc props :render-fn) :renderItem (wrap-render-fn f (:render-data props)))
     props)
   :data to-array))

(defn- default-render-section-header [{:keys [title data]}]
  (when (seq data)
    [basic/view
     [basic/text
      title]]))

(defn section-list
  "A wrapper for SectionList.
   To render something on empty sections, use renderSectionFooter and conditionaly
   render on empty data
   See https://facebook.github.io/react-native/docs/sectionlist.html"
  [{:keys [sections render-section-header-fn render-section-footer-fn style] :as props
    :or {render-section-header-fn default-render-section-header
         style {}}}]
  [section-list-class
   (merge (base-list-props props)
          props
          (when render-section-footer-fn
            {:renderSectionFooter (wrap-render-section-header-fn render-section-footer-fn)})
          {:sections            (clj->js (map wrap-per-section-render-fn sections))
           :renderSectionHeader (wrap-render-section-header-fn render-section-header-fn)
           :style               style})])