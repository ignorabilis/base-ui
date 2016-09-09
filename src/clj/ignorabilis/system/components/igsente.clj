(ns ignorabilis.system.components.igsente
  (:require [ignorabilis.web.ws-handler :as ws-handler]
            [mount.core :refer [defstate]]
            [taoensso.sente :as sente]
            [taoensso.sente.server-adapters.http-kit :refer [sente-web-server-adapter]]
    ;; or
    ;; [taoensso.sente.server-adapters.immutant :refer (sente-web-server-adapter)]
            [taoensso.timbre :as timbre]))

(defn start-sente []
  (timbre/debug "Starting Sente.")
  (let [{:keys [ch-recv send-fn ajax-post-fn ajax-get-or-ws-handshake-fn connected-uids]}
        (sente/make-channel-socket! sente-web-server-adapter {})]
    {:ring-ajax-post                ajax-post-fn
     :ring-ajax-get-or-ws-handshake ajax-get-or-ws-handshake-fn
     :ch-chsk                       ch-recv
     :chsk-send!                    send-fn
     :connected-uids                connected-uids
     :router                        (atom (sente/start-chsk-router! ch-recv ws-handler/event-msg-handler*))}))

(defn stop-sente [{:keys [router] :as sente-component}]
  (let [stop-f @router]
    (assoc sente-component :router (stop-f))))

(defstate
  sente-component
  :start (start-sente)
  :stop (stop-sente sente-component))