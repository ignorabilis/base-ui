(ns ignorabilis.comm.route-handlers
  (:require [ignorabilis.views.home.core :as home]
            [ignorabilis.views.skills.core :as skills]
            [ignorabilis.views.not-found.core :as not-found]
            [ignorabilis.views.not-implemented.core :as not-implemented]
            [taoensso.timbre :as timbre]))

(def route-handlers
  {:client-routes/home-page      home/home-page
   :client-routes/skills-page    skills/skills-page
   :client-routes/not-found-page not-found/not-found-page})

(defn handler-key->current-page [handler-key]
  (let [current-page (get route-handlers handler-key)]
    (if current-page
      current-page
      (do (timbre/error "Page not implemented or broken for key: " handler-key)
          not-implemented/not-implemented-page))))
