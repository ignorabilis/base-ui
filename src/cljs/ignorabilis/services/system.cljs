(ns ignorabilis.services.system
  (:require [taoensso.timbre :as timbre]))

(defmulti system-msg-handler :id)

(defmethod system-msg-handler :chsk/state
  [{:keys [?data]}]
  (if (= ?data {:first-open? true})
    (timbre/debug "Channel socket successfully established!")
    (timbre/debug "Channel socket state change: %s" ?data)))

(defmethod system-msg-handler :chsk/recv
  [{:keys [?data]}]
  (timbre/debug "Push event from server: %s" ?data))

(defmethod system-msg-handler :chsk/handshake
  [{:keys [?data]}]
  (let [[?uid ?csrf-token ?handshake-data] ?data]
    (timbre/debug "\n Handshake: %s" ?data)))
