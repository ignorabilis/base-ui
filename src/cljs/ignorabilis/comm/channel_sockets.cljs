(ns ignorabilis.comm.channel-sockets
  (:require [taoensso.sente :as sente]))

(declare chsk ch-chsk chsk-send! chsk-state)

(defn init-channel-sockets []
  (let [{:keys [chsk ch-recv send-fn state]}
        (sente/make-channel-socket! "/chsk" {:type :auto})]
    (def chsk chsk)
    (def ch-chsk ch-recv)
    (def chsk-send! send-fn)
    (def chsk-state state)))
