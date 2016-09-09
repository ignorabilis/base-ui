(ns ignorabilis.web.html.helpers
  (:require [hiccup.core :as hiccup]
            [hiccup.page :as hpage]))

(defn html5 [html]
  (str (:html5 hpage/doctype) (hiccup/html html)))