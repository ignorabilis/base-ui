(ns ignorabilis.core
  (:require [ignorabilis.comm.channel-sockets :as sockets]
            [ignorabilis.home.core :as home]
            [ignorabilis.common.layout.core :as layout]
            [cljs.core.async :refer [<! >! put! chan]]
            [taoensso.timbre :as timbre]
            [taoensso.sente :as sente :refer [cb-success?]]
            [taoensso.sente.packers.transit :as sente-transit] ;; Optional, for Transit encoding:
            [goog.dom :as gdom]
            [reagent.core :as r :refer [atom]])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]]))

(timbre/debug "ClojureScript appears to have loaded correctly.")

;;;; Logging config

;; (sente/set-logging-level! :trace) ; Uncomment for more logging
;;;; Packer (client<->server serializtion format) config
;; (def packer (sente-transit/get-transit-packer :edn))
;; (def packer :edn) ; Default packer (no need for Transit dep)



;;;; Routing handlers

(defmulti event-msg-handler :id)                            ; Dispatch on event-id
;; Wrap for logging, catching, etc.:
(defn event-msg-handler* [{:as ev-msg :keys [id ?data event]}]
  (timbre/debug "Event: %s" event)
  (event-msg-handler ev-msg))

(defmethod event-msg-handler :default                       ; Fallback
  [{:as ev-msg :keys [event]}]
  (timbre/debug "Unhandled event: %s" event))

(defmethod event-msg-handler :chsk/state
  [{:as ev-msg :keys [?data]}]
  (if (= ?data {:first-open? true})
    (timbre/debug "Channel socket successfully established!")
    (timbre/debug "Channel socket state change: %s" ?data)))

(defmethod event-msg-handler :chsk/recv
  [{:as ev-msg :keys [?data]}]
  (timbre/debug "Push event from server: %s" ?data))

(defmethod event-msg-handler :chsk/handshake
  [{:as ev-msg :keys [?data]}]
  (let [[?uid ?csrf-token ?handshake-data] ?data]
    (timbre/debug "Handshake: %s" ?data)))

;; Add your (defmethod handle-event-msg! <event-id> [ev-msg] <body>)s here...


(defn mount-root []
  (r/render
    [layout/page-layout home/home-page]
    (gdom/getElement "ignorabilis-app")))

(defn init! []
  #_(hook-browser-navigation!)
  (sockets/init-channel-sockets)
  (mount-root))

(init!)

(def router_ (atom nil))
(defn stop-router! [] (when-let [stop-f @router_] (stop-f)))
(defn start-router! []
  (stop-router!)
  (reset! router_ (sente/start-chsk-router! sockets/ch-chsk event-msg-handler*)))

(defn start! []
  (start-router!))

(start!)