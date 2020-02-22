# rn-shadow-steroid

A Clojure library with a few functions-steroids for react native app, **can be used only with shadow-cljs**

[![Clojars](https://img.shields.io/clojars/v/rn-shadow-steroid.svg)](https://clojars.org/rn-shadow-steroid)

## Usage

```clojure
{:dependencies [[rn-shadow-steroid "0.1.0"]]}
```

Register root reagent component in app registry

```clojure
(:require [steroid.rn.core :as rn])

(defn root-comp []
  [rn/view
   [rn/text "HELLO WORLD"]])

(defn init []
  (rn/register-comp "app-name" root-comp))
```

Register root component with **HOT RELOAD**

```clojure
(:require [steroid.rn.core :as rn])

(defn root-comp []
  [rn/view
   [rn/text "HELLO WORLD"]])

(defn init []
  (rn/register-reload-comp "app-name" root-comp))
```

shadow-cljs.edn
```clojure
{:source-paths ["src"]

 :dependencies [[rn-shadow-steroid "0.1.0"]]

 :builds       {:dev
                {:target     :react-native
                 :init-fn    app.core/init
                 :output-dir "app"
                 :devtools   {:autoload true
                              :after-load steroid.rn.core/reload}}}}
```

Now each time you change your code it will be hot reloaded keeping an app state. **NOTE react native fast refresh should be turned off**


React Native components

```clojure
(:require [steroid.rn.core :as rn])

(defn item-render [{:keys [title image-url description]}]
  [rn/touchable-opacity {:on-press #(do "smth")}
   [rn/view {:style {:padding 5 :flex-direction :row :align-items :center}}
    [rn/image {:style {:width 50 :height 50 :margin 5 :border-radius 25 :margin-right 10}
               :source {:uri image-url}}]
    [rn/view {:style {:flex 1}}
     [rn/text {:style {:font-size 18 :font-family "Inter" :flex-shrink 1}} title]
     [rn/text {:style {:font-family "Inter" :flex-shrink 1 :color :gray :margin-top 5}
               :number-of-lines 2}
      description]]]])

(defn myapp []
    [rn/view {:style {:flex 1 :background-color :white}}
     [rn/text {:style {:font-size 22 :align-self :center :margin 10 :font-family "Inter"}}
      "MyApp"]
     [rn/flat-list {:data data-vector :render-fn item-render}]])
```

All components
```clojure
activity-indicator, button, image, image-background, input-accessory-view, modal, picker, refresh-control
safe-area-view, scroll-view, section-list, status-bar, switch, text, text-input, toolbar-android
touchable-highlight, touchable-native-feedback, touchable-opacity, touchable-without-feedback
view, virtualized-list, flat-list, app-registry
```

React Navigation with HOT RELOAD

```clojure
(:require [steroid.rn.core :as rn]
            [steroid.rn.navigation.core :as rnn]
            [steroid.rn.navigation.safe-area :as safe-area]
            [steroid.rn.navigation.bottom-tabs :as bottom-tabs]
            [steroid.rn.navigation.stack :as stack]
            [app.modal.views :as modal.views]
            [app.home.views :as home.vews]
            [app.map.views :as map.views])

(defn tabs-screen []
  (let [[navigator screen] (bottom-tabs/create-bottom-tab-navigator)
        home-comp (rn/reload-comp home.vews/home)
        map-comp (rn/reload-comp map.views/map-view)]
    (fn []
      [navigator
       [screen {:name "Home" :component home-comp}]
       [screen {:name "Map" :component map-comp}]])))

(defn root-stack []
  (let [[navigator screen] (stack/create-stack-navigator)
          tabs-comp (rn/react-comp tabs-screen)
          modal-comp (rn/reload-comp moadl.views/modal)]
    (fn []
        [safe-area/safe-area-provider
         [rnn/navigation-container
          [navigator {:mode :modal :headerMode :none}
           [screen {:name      :main
                    :component tabs-comp}]
           [screen {:name      :modal
                    :component modal-comp
                    :options   {:gestureEnabled false}}]]]])))

(defn init []
  (rn/register-comp "bestrestrn" root-stack))
```

Notice that we register root-stack without hot reload but screens components are reloadable. **Also it's important to create components outside renderer function**

re-frame navigation events `steroid.rn.navigation.events`

```clojure
(:require steroid.rn.navigation.events
          [steroid.rn.navigation.core :as rnn])

(reagent/create-class
 {:component-did-mount (rnn/create-mount-handler #(dispatch [:init-app]))
  :reagent-render
  (fn []
    [rnn/navigation-container {:ref rnn/nav-ref-handler}
      [navigator {:mode :modal :headerMode :none}
       [screen {:name      :main
                :component tabs-comp}]]])})
```

Note! `create-mount-handler` and `nav-ref-handler` should be used together

After you require `steroid.rn.navigation.events` once, re-frame events are registered and you can use them for navigation

`(re-frame/dispatch [:navigate-to :modal])`

`(re-frame/dispatch [:navigate-back])`


ENJOY!