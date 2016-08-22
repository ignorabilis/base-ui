(ns ignorabilis.views.home.core
  (:require [reagent.core :as r :refer [atom]]
            [ignorabilis.views.skills.core :as skills]))

(defn home-page []
  [:div
   "home page here"
   [skills/skills-page]])
