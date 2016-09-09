(ns ignorabilis.web.comm.demo
  (:require [ignorabilis.system.components.igsente :as igsente]
            [taoensso.timbre :as timbre]
            [clojure.core.async :as async :refer [<! <!! >! >!! put! chan go go-loop]]))

;;;; Example: broadcast server>user

;; As an example of push notifications, we'll setup a server loop to broadcast
;; an event to _all_ possible user-ids every 10 seconds:
(defn start-broadcaster! []
  (go-loop [i 0]
           (<! (async/timeout 10000))
           (timbre/trace (format "Broadcasting server>user: %s" @(:connected-uids igsente/sente-component)))
           (doseq [uid (:any @(:connected-uids igsente/sente-component))]
             ((:chsk-send! igsente/sente-component) uid
               [:some/broadcast
                {:what-is-this "A broadcast pushed from server"
                 :how-often    "Every 10 seconds"
                 :to-whom      uid
                 :i            i}]))
           (recur (inc i))))

; Note that this'll be fast+reliable even over Ajax!:
(defn test-fast-server>user-pushes []
  (doseq [uid (:any @(:connected-uids igsente/sente-component))]
    (doseq [i (range 100)]
      ((:chsk-send! igsente/sente-component) uid [:fast-push/is-fast (str "hello " i "!!")]))))
