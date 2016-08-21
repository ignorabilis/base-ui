(ns ignorabilis.core
  (:require [ignorabilis.comm.channel-sockets :as sockets]
            [ignorabilis.views.home.core :as home]
            [ignorabilis.common.layout.core :as layout]
            [ignorabilis.services.core :as services]
            [cljs.core.async :refer [<! >! put! chan]]
            [taoensso.timbre :as timbre]
            [taoensso.sente :as sente :refer [cb-success?]]
            [goog.dom :as gdom]
            [reagent.core :as r :refer [atom]])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]]))

(timbre/debug "ClojureScript appears to have loaded correctly.")

(defn init-logging []
  #_(when is-dev
    (sente/set-logging-level! :trace)))

(defn mount-root []
  (r/render
    [layout/page-layout home/home-page]
    (gdom/getElement "ignorabilis-app")))

(def router_ (atom nil))
(defn stop-router! [] (when-let [stop-f @router_] (stop-f)))
(defn start-router! []
  (reset! router_ (sente/start-chsk-router! sockets/ch-chsk services/event-msg-handler*)))
(defn restart-router! []
  (stop-router!)
  (start-router!))

(defn start! []
  (init-logging)
  #_(hook-browser-navigation!)
  (sockets/init-channel-sockets)
  (mount-root)
  (start-router!))

(defn restart! []
  (restart-router!))

(start!)
