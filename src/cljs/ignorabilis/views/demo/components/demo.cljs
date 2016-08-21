(ns ignorabilis.views.demo.components.demo
  (:require [ignorabilis.comm.channel-sockets :as sockets]
            [clojure.string :as str]
            [taoensso.timbre :as timbre]
            [taoensso.sente :as sente :refer [cb-success?]]
            [reagent.core :as r :refer [atom]]))

(defn btn1-click [_]
  (timbre/debug "Button 1 was clicked (won't receive any reply from server)")
  (sockets/chsk-send! [:example/button1 {:had-a-callback? "nope"}]))

(defn btn2-click [_]
  (timbre/debug "Button 2 was clicked (will receive reply from server)")
  (sockets/chsk-send! [:example/button2 {:had-a-callback? "indeed"}] 5000
              (fn [cb-reply] (timbre/debug "Callback reply: %s" cb-reply))))

(defn btn-login-click [_]
  (let [user-id (.-value (.getElementById js/document "input-login"))]
    (if (str/blank? user-id)
      (js/alert "Please enter a user-id first")
      (do
        (timbre/debug "Logging in with user-id %s" user-id)

        ;;; Use any login procedure you'd like. Here we'll trigger an Ajax
        ;;; POST request that resets our server-side session. Then we ask
        ;;; our channel socket to reconnect, thereby picking up the new
        ;;; session.

        (sente/ajax-call "/login"
                         {:method :post
                          :params {:user-id    (str user-id)
                                   :csrf-token (:csrf-token @sockets/chsk-state)}}
                         (fn [ajax-resp]
                           (timbre/debug "Ajax login response: %s" ajax-resp)
                           (let [login-successful? true ; Your logic here
                                 ]
                             (if-not login-successful?
                               (timbre/debug "Login failed")
                               (do
                                 (timbre/debug "Login successfull")
                                 (sente/chsk-reconnect! sockets/chsk))))))))))

(defn demo-page []
  [:div
   [:h1 "Sente reference example"]
   [:p "This is just a demo page for testing purposes."]
   [:hr]
   [:p [:strong "Step 1: "] "Open browser's JavaScript console."]
   [:p [:strong "Step 2: "] "Try: "
    [:button#btn1.button
     {:type "button" :on-click btn1-click}
     "chsk-send! (w/o reply)"]
    [:button#btn2.button.success
     {:type "button" :on-click btn2-click}
     "chsk-send! (with reply)"]]
   [:p [:strong "Step 3: "] "See browser's console + nREPL's std-out."]
   [:hr]
   [:h2 "Login with a user-id"]
   [:p "The server can use this id to send events to *you* specifically."]
   [:p [:input#input-login {:type :text :placeholder "User-id"}]
    [:button#btn-login.button
     {:type "button" :on-click btn-login-click}
     "Secure login!"]]])
