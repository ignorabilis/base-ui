(ns ignorabilis.common.layout.core
  (:require [ignorabilis.common.layout.header :as header]
            [ignorabilis.common.layout.body :as body]
            [ignorabilis.common.layout.footer :as footer]))

(defn page-layout [page]
  [:div.main
   [header/header-comp]
   [body/body-comp page]
   [footer/footer-comp]])