(ns ignorabilis.web.auth.core
  (:require [taoensso.timbre :as timbre]))

(defn login!
  "Here's where you'll add your server-side login/auth procedure (Friend, etc.).
  In our simplified example we'll just always successfully authenticate the user
  with whatever user-id they provided in the auth request."
  [ring-request]
  (let [{:keys [session params]} ring-request
        {:keys [user-id]} params]
    (timbre/debug "Login request: %s" params)
    {:status 200 :session (assoc session :uid user-id)}))