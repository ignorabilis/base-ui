(ns ignorabilis.web.html.pages
  (:require [ignorabilis.web.html.helpers :as html-helpers]
            [hiccup.page :as hpage]))

(defn landing-page []
  (html-helpers/html5
    [:html
     [:head
      [:meta {:charset "utf-8"}]
      [:link
       {:crossorigin "anonymous",
        :integrity "sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u",
        :href "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css",
        :rel "stylesheet"}]
      [:link
       {:crossorigin "anonymous",
        :integrity "sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp",
        :href "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css",
        :rel "stylesheet"}]
      (apply hpage/include-css ["/site.css"])]
     [:div#ignorabilis-app
      "Loading..."]

     [:script
      {:crossorigin "anonymous",
       :integrity "sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8=",
       :src "https://code.jquery.com/jquery-3.1.1.min.js"}]
     [:script
      {:crossorigin "anonymous",
       :integrity "sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa",
       :src "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"}]
     [:script {:src "main.js"}]]))