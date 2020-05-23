(ns steroid.rn.components.datetimepicker
  (:require [reagent.core :as reagent]
            ["@react-native-community/datetimepicker" :default DateTimePicker]))

(def date-time-picker (reagent/adapt-react-class DateTimePicker))