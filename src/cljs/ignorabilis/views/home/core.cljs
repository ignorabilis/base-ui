(ns ignorabilis.views.home.core
  (:require [ignorabilis.comm.routes :as routes]
            [ignorabilis.web.comm.routes :as wroutes]
            [reagent.core :as r :refer [atom]]))

(defn home-page []
  [:div
   "home page here"
   [:br]
   [:a.button
    {:on-click (fn [_]
                 (routes/set-current-page! ::wroutes/skills-page))}]])
