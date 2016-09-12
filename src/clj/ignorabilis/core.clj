(ns ignorabilis.core
  (:gen-class)
  (:require [mount.core :as mount]
            [ignorabilis.system.components.igsente]
            [ignorabilis.system.components.igweb]))

(defn -main
  "Start a production system."
  [& args]
  (mount/start))