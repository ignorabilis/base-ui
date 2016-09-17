(ns ignorabilis.comm.routes
  (:require [ignorabilis.web.comm.routes :as wroutes]
            [reagent.session :as session]
            [bidi.router :as brouter]))

(def router-handler
  (let [router (brouter/start-router! wroutes/ig-routes
                                      {:on-navigate      (fn [current-page]
                                                           (session/put! :current-page current-page))
                                       :default-location {:handler ::home-page}})]
    router))

(defn set-current-page! [current-page-handler]
  (brouter/set-location!
    router-handler
    {:handler current-page-handler}))
