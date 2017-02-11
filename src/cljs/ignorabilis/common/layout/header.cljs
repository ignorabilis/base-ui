(ns ignorabilis.common.layout.header
  (:require [ignorabilis.comm.routes :as routes]))

(defn header-comp []
  [:nav.navbar.navbar-default
   [:div.container-fluid
    [:div.navbar-header
     [:button.navbar-toggle.collapsed
      {:aria-expanded "false",
       :data-target "#bs-example-navbar-collapse-1",
       :data-toggle "collapse",
       :type "button"}
      [:span.sr-only "Toggle navigation"]
      [:span.icon-bar]
      [:span.icon-bar]
      [:span.icon-bar]]
     [:a.navbar-brand {:href "#"} "Brand"]]
    [:div#bs-example-navbar-collapse-1.collapse.navbar-collapse
     [:ul.nav.navbar-nav
      [:li.active [:a {:on-click (fn [_]
                                   (routes/set-current-page! :client-routes/skills-page))} "Skills" [:span.sr-only "(current)"]]]
      [:li [:a {:href "#"} "Link 22"]]
      [:li.dropdown
       [:a.dropdown-toggle
        {:aria-expanded "false",
         :aria-haspopup "true",
         :role "button",
         :data-toggle "dropdown",
         :href "#"}
        "Dropdown "
        [:span.caret]]
       [:ul.dropdown-menu
        [:li [:a {:href "#"} "Action"]]
        [:li [:a {:href "#"} "Another action"]]
        [:li [:a {:href "#"} "Something else here"]]
        [:li.divider {:role "separator"}]
        [:li [:a {:href "#"} "Separated link"]]
        [:li.divider {:role "separator"}]
        [:li [:a {:href "#"} "One more separated link"]]]]]
     [:form.navbar-form.navbar-left
      [:div.form-group
       [:input.form-control {:placeholder "Search", :type "text"}]]
      [:button.btn.btn-default {:type "submit"} "Submit"]]
     [:ul.nav.navbar-nav.navbar-right
      [:li [:a {:href "#"} "Link"]]
      [:li.dropdown
       [:a.dropdown-toggle
        {:aria-expanded "false",
         :aria-haspopup "true",
         :role "button",
         :data-toggle "dropdown",
         :href "#"}
        "Dropdown "
        [:span.caret]]
       [:ul.dropdown-menu
        [:li [:a {:href "#"} "Action"]]
        [:li [:a {:href "#"} "Another action"]]
        [:li [:a {:href "#"} "Something else here"]]
        [:li.divider {:role "separator"}]
        [:li [:a {:href "#"} "Separated link"]]]]]]]])
