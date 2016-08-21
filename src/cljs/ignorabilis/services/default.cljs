(ns ignorabilis.services.default
  (:require [taoensso.timbre :as timbre]))

(defmulti default-msg-handler :id)

(defmethod default-msg-handler :default
  [{:keys [event]}]
  (timbre/debug "Unhandled event: %s" event))
