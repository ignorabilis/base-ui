(ns ignorabilis.web.html.pages
  (:require [ignorabilis.web.html.helpers :as html-helpers]
            [hiccup.page :as hpage]))

(defn landing-page []
  (html-helpers/html5
    [:html
     [:head
      [:meta {:charset "utf-8"}]
      (apply hpage/include-css ["/site.css"
                                "/foundation/css/foundation.css"])]
     [:div#ignorabilis-app
      "Loading..."]
     [:script {:src "/foundation/js/jquery.js"}]
     [:script {:src "/foundation/js/foundation.min.js"}]
     [:script {:src "main.js"}]]))