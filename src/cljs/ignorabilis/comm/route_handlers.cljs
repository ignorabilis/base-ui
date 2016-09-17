(ns ignorabilis.comm.route-handlers
  (:require [ignorabilis.web.comm.routes :as wroutes]
            [ignorabilis.views.home.core :as home]
            [ignorabilis.views.skills.core :as skills]
            [ignorabilis.views.not-found.core :as not-found]
            [ignorabilis.views.not-implemented.core :as not-implemented]))

(def route-handlers
  {::wroutes/home-page      home/home-page
   ::wroutes/skills-page    skills/skills-page
   ::wroutes/not-found-page not-found/not-found-page})

(defn handler-key->current-page [handler-key]
  (get route-handlers handler-key not-implemented/not-implemented-page))
