(ns clojure-getting-started.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]])
  (:import (com.twilio.sdk TwilioRestClient)
           (org.apache.http.message BasicNameValuePair)))

(defn splash []
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (pr-str ["Hello there" :from 'Heroku])})

(defn create-client []
  (TwilioRestClient. (env :account-sid) (env :auth-token)))

(defn create-param [name value]
  (BasicNameValuePair. name value))

(defn create-params [p1 p2 p3]
  (java.util.ArrayList. [p1 p2 p3]))

(defn create-message [client params]
  (. (. (. client getAccount) getMessageFactory) create params))

(defn send-msg []
  (let [client (create-client)
        p1 (create-param "Body" "Sup Dawg?!")
        p2 (create-param "To" (env :message-to))
        p3 (create-param "From" (env :message-from))]
    (create-message client (create-params p1 p2 p3)))
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Message sent!"})

(defroutes app
  (GET "/" []
       (splash))
  (GET "/client" [] (send-msg))
  (ANY "*" []
       (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))
