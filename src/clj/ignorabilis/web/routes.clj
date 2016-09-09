(ns ignorabilis.web.routes
  (:require [ignorabilis.web.html.pages :as html-pages]
            [ignorabilis.web.auth.core :as auth]
            [ignorabilis.system.components.igsente :as igsente]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]))

(defroutes
  igroutes
  (GET "/" req (html-pages/landing-page))

  (GET "/chsk" req ((:ring-ajax-get-or-ws-handshake igsente/sente-component) req))
  (POST "/chsk" req ((:ring-ajax-post igsente/sente-component) req))
  (POST "/login" req (auth/login! req))

  (route/resources "/")
  (route/not-found "<h1>Page not found</h1>"))