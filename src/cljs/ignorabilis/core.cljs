(ns ignorabilis.core
  (:require [ignorabilis.common.env :refer [ienv dev?]]
            [ignorabilis.comm.channel-sockets :as sockets]
            [ignorabilis.common.layout.core :as layout]
            [ignorabilis.services.core :as services]
            [ignorabilis.comm.route-handlers :as route-handlers]
            [cljs.core.async :refer [<! >! put! chan]]
            [taoensso.timbre :as timbre]
            [taoensso.sente :as sente :refer [cb-success?]]
            [goog.dom :as gdom]
            [reagent.core :as r :refer [atom]]
            [reagent.session :as session])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]]))

(defn init-logging []
  (when dev?
    (enable-console-print!)
    (timbre/set-level! :debug)
    (timbre/debug "ClojureScript appears to have loaded correctly.")))

(defn init-plugins []
  ;init bootstrap ui widgets here if needed
  )

(defn current-page []
  (let [{:keys [handler]} (session/get :current-page)
        current-page (route-handlers/handler-key->current-page handler)]
    [:div
     [current-page]]))

(defn mount-root []
  (r/render
    [layout/page-layout current-page]
    (gdom/getElement "ignorabilis-app")))

(def ws-router_ (atom nil))
(defn stop-ws-router! [] (when-let [stop-f @ws-router_] (stop-f)))
(defn start-ws-router! []
  (reset! ws-router_ (sente/start-chsk-router! sockets/ch-chsk services/event-msg-handler*)))
(defn restart-ws-router! []
  (stop-ws-router!)
  (start-ws-router!))

(defn start! []
  (init-logging)
  (init-plugins)
  (sockets/init-channel-sockets)
  (mount-root)
  (start-ws-router!))

(defn restart! []
  (restart-ws-router!))

(start!)
