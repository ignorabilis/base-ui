(ns ignorabilis.common.layout.footer)

(defn footer-comp []
  [:footer.footer
   [:div.row.full-width.small-up-1.medium-up-2.large-up-4
    [:div.column.large-4
     [:span.fi-laptop]
     [:p "Some nice explanations here."]]
    [:div.column.large-4
     [:span.fi-html5]
     [:p "Some nice explanations here."]]
    [:div.column.large-2
     [:h4 "Work With Me"]
     [:ul.footer-links
      [:li>a {:href "#"} "What I Do"]
      [:li>a {:href "#"} "Pricing"]
      [:li>a {:href "#"} "FAQ"]]]
    [:div.column.large-2
     [:h4 "Follow me"]
     [:ul.footer-links
      [:li>a {:href "#"} "Github"]
      [:li>a {:href "#"} "Bitbucket"]
      [:li>a {:href "#"} "LinkedIN"]]]]])