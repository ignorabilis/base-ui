(ns ignorabilis.views.skills.core
  (:require [reagent.core :as r :refer [atom]]
            [clojure.string :as string]))

(defonce skills-state (atom {:search "" :skills [{:label "Foundation"}
                                                 {:label "Bootstrap"}
                                                 {:label "C#"}]}))

(defn skill-item [{:keys [label]}]
  [:li label])

(defn skills-list [skills search]
  (let [skill-result (filter #(string/includes? (:label %) search) skills)]
    [:ul
     (for [{:keys [label] :as skill} skill-result]
       ^{:key label} [skill-item skill])]))

(defn skills-search [search]
  [:input
   {:type "text"
    :placeholder "Search for a skill..."
    :value search
    :on-change (fn [e]
                 (let [new-value (.. e -target -value)]
                   (prn new-value)
                   (swap! skills-state assoc-in [:search] new-value)))}])

(defn skills-page []
  (let [{:keys [skills search]} @skills-state]
    [:div
     [skills-search search]
     [skills-list skills search]]))