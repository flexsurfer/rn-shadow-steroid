# rn-shadow-steroid

A Clojure library with a few functions-steroids for react native app, **can be used only with shadow-cljs**

![IMG](screencast.gif)

[![Clojars](https://img.shields.io/clojars/v/rn-shadow-steroid.svg)](https://clojars.org/rn-shadow-steroid)

## Usage

```clojure
{:dependencies [[rn-shadow-steroid "0.2.2"]]}
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

 :dependencies [[rn-shadow-steroid "0.2.2"]]

 :builds       {:dev
                {:target     :react-native
                 :init-fn    app.core/init
                 :output-dir "app"
                 :devtools   {:after-load steroid.rn.core/reload
                              :build-notify steroid.rn.core/build-notify}}}}
```

Now each time you change your code it will be hot reloaded keeping an app state. **NOTE react native fast refresh should be turned off**


###React Native components

#### Basic components
```clojure
[steroid.rn.core :as rn]
rn/app-registry rn/view rn/text rn/image rn/text-input rn/scroll-view
```

#### UI components
```clojure
[steroid.rn.components.ui :as ui]
rn/button rn/switch
```

#### Touchable components
```clojure
[steroid.rn.components.touchable :as touchable]
touchable/touchable-highlight touchable/touchable-native-feedback touchable/touchable-opacity touchable/touchable-without-feedback
```

#### List components
```clojure
[steroid.rn.components.list :as list]
list/flat-list list/section-list
```

#### Other components
```clojure
[steroid.rn.components.other :as other]
other/activity-indicator other/alert other/dimensions other/keyboard-avoiding-view other/modal other/refresh-control other/status-bar
```

#### Picker component
````bash
yarn add @react-native-community/picker
````

```clojure
[steroid.rn.components.picker :as picker]
picker/picker picker/item
```

#### DateTimePicker component
````bash
yarn add @react-native-community/datetimepicker
````

```clojure
[steroid.rn.components.datetimepicker :as datetimepicker]
datetimepicker/date-time-picker
```

#### Async storage component
````bash
yarn add @react-native-community/async-storage
````

```clojure
[steroid.rn.components.async-storage :as async-storage]
(async-storage/set-item "key" {:my-value "value"})
(async-storage/get-item "key" #(println "value" %))
(async-storage/remove-item "key")
(async-storage/get-all-keys #(println "keys" %))
(async-storage/clear)
```
     
#### EXAMPLE

```clojure
(:require [steroid.rn.core :as rn]
          [steroid.rn.components.list :as list])

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
     [list/flat-list {:data data-vector :render-fn item-render}]])
```
 
### React Navigation with HOT RELOAD

#### Navigation container component
````bash
yarn add @react-navigation/native
````

```clojure
[steroid.rn.navigation.core :as rnn]
rnn/navigation-container
rnn/create-navigation-container-reload
```

#### Stack components
````bash
yarn add @react-navigation/stack
````

```clojure
[steroid.rn.navigation.stack :as stack]
stack/stack
```

#### Bottom tabs components
````bash
yarn add @react-navigation/bottom-tabs
````

```clojure
[steroid.rn.navigation.bottom-tabs :as bottom-tabs]
bottom-tabs/bottom-tab
```

#### Safe area components
````bash
yarn add react-native-safe-area-context
````

```clojure
[steroid.rn.navigation.safe-area :as safe-area]
safe-area/safe-area-provider safe-area/safe-area-consumer safe-area/safe-area-view
```

#### EXAMPLE

````bash
`yarn add @react-navigation/native @react-navigation/stack @react-navigation/bottom-tab react-native-reanimated react-native-gesture-handler react-native-screens react-native-safe-area-context @react-native-community/masked-view`

`cd ios; pod install; cd ..`
````

```clojure
  (:require [steroid.rn.core :as rn]
            [re-frame.core :as re-frame]
            [steroid.rn.navigation.core :as rnn]
            [steroid.rn.navigation.stack :as stack]
            [steroid.rn.navigation.bottom-tabs :as bottom-tabs]
            [clojurernproject.views :as screens]
            [steroid.rn.navigation.safe-area :as safe-area]
            steroid.rn.navigation.events))

(defn main-screens []
  [bottom-tabs/bottom-tab
   [{:name      :home
     :component screens/home-screen}
    {:name      :basic
     :component screens/basic-screen}]])

(defn root-stack []
  [safe-area/safe-area-provider
   [(rnn/create-navigation-container-reload
     {:on-ready #(re-frame/dispatch [:init-app-db])}
     [stack/stack {:mode :modal :header-mode :none}
      [{:name      :main
        :component main-screens}
       {:name      :modal
        :component screens/modal-screen}]])]])

(defn init []
  (rn/register-comp "ClojureRNProject" root-stack))
```

Notice that we register root-stack without hot reload but create navigation-container-reload.

#### re-frame navigation events `steroid.rn.navigation.events`

After `steroid.rn.navigation.events` has been required once, re-frame events are registered and you can use them for navigation

`(re-frame/dispatch [:navigate-to :modal])`

`(re-frame/dispatch [:navigate-to :basic])`

`(re-frame/dispatch [:navigate-back])`

ENJOY!