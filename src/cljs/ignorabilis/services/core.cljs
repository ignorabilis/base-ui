(ns ignorabilis.services.core
  (:require [ignorabilis.services.system :as ssystem]
            [ignorabilis.services.default :as default]
            [taoensso.timbre :as timbre]))

(defn event-msg-handler* [{:keys [event] :as ev-msg}]
  (timbre/debug "Logging event: %s" event)
  (ssystem/system-msg-handler ev-msg)
  (default/default-msg-handler ev-msg))
