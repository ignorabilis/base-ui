(ns ignorabilis.common.layout.header)

(defn header-comp []
  [:header
   [:div.title-bar
    {:data-responsive-toggle "main-menu"
     :data-hide-for "medium"}
    [:button.menu-icon
     {:type "button"
      :data-toggle :data-toggle}]
    [:div.title-bar-title
     "Menu"]]
   [:div#main-menu.top-bar
    [:div.top-bar-left
     [:ul.dropdown.menu
      {:data-dropdown-menu "data-dropdown-menu"}
      [:li.menu-text "Ignorabilis"]]]
    [:div.top-bar-right
     [:ul.menu.vertical.medium-horizontal
      {:data-responsive-menu "drilldown medium-dropdown"}
      [:li [:a {:href "#"} "Skills"]]
      [:li [:a {:href "#"} "Experience"]]]]]])