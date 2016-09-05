(ns ignorabilis.views.skills.core
  (:require [reagent.core :as r]
            [clojure.string :as string]))

(defonce skills-state (r/atom {:query "" :skills [{:label "Foundation"}
                                                  {:label "Bootstrap"}
                                                  {:label "C#"}
                                                  {:label "C1"}
                                                  {:label "C2"}
                                                  {:label "C3"}
                                                  {:label "C4"}
                                                  {:label "C5"}
                                                  {:label "C6"}
                                                  {:label "C7"}
                                                  {:label "C8"}
                                                  {:label "C9"}
                                                  {:label "C10"}
                                                  {:label "C11"}
                                                  {:label "C12"}
                                                  {:label "C13"}
                                                  {:label "C14"}
                                                  {:label "C15"}
                                                  {:label "C16"}
                                                  {:label "C17"}
                                                  {:label "C18"}
                                                  {:label "C19"}
                                                  {:label "C20"}
                                                  {:label "C21"}
                                                  {:label "C22"}
                                                  {:label "C23"}
                                                  {:label "C24"}
                                                  {:label "C25"}]}))

(defn case-insensitive-search [query-str coll item-key-path]
  (if (string/blank? query-str)
    coll
    (let [q (string/lower-case query-str)]
      (filter
        #(string/includes?
          (string/lower-case
            (get-in % item-key-path)) q)
        coll))))

(defn skill-item [{:keys [label]}]
  [:li label])

(defn skills-list [query-str skills]
  (let [skills-result (case-insensitive-search query-str skills [:label])]
    [:ul
     (for [{:keys [label] :as skill} skills-result]
       ^{:key label} [skill-item skill])]))

(defn skills-search [query-str]
  [:input
   {:type        "text"
    :placeholder "Search for a skill..."
    :value       query-str
    :on-change   (fn [e]
                   (let [new-value (.. e -target -value)]
                     (swap! skills-state assoc-in [:query] new-value)))}])

(defn skills-page []
  (let [{:keys [skills query]} @skills-state]
    [:div
     [skills-search query]
     [skills-list query skills]]))