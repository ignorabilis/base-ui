(ns ignorabilis.system.components.igweb
  (:require [ignorabilis.common.env :refer [ienv]]
            [ignorabilis.web.http-handler :as http-handler]
            [mount.core :refer [defstate]]
            [org.httpkit.server :as server]
            [taoensso.timbre :as timbre]))

(defn start-web []
  (let [port (ienv :http-port)]
    (timbre/debug "Starting Web Server on port " port ".")
    (let [server (server/run-server http-handler/ighandler {:port port})]
      {:server server})))

(defn stop-web [{:keys [server]}]
  (server))

(defstate
  web-component
  :start (start-web)
  :stop (stop-web web-component))