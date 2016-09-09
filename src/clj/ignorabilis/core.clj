(ns ignorabilis.core
  (:require [mount.core :as mount]))

(defn -main
  "Start a production system."
  [& args]
  (mount/start))