(ns ignorabilis.common.layout.footer)

(defn footer-comp []
  [:footer
   {:style {:position "absolute"
            :bottom "0px"
            :height "50px"
            :background-color "black"
            :color "white"
            :width "100%"
            :z-index 100000}}
   [:div
    "Some text here."]])
