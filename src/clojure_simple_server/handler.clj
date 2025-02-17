(ns clojure-simple-server.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.basic-authentication :refer [wrap-basic-authentication]]))

(def users [{:username "aboba" :password "amogus"}
            {:username "user1" :password "password"}])

(defn authenticated? [username password] 
  (let [user (first (filter #(= (:username %) username) users))]
    (and (not (nil? user)) (= (:password user) password))))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (route/not-found "Not Found"))

(def app
  (as-> app-routes $ 
    (wrap-basic-authentication $ authenticated?)
    (wrap-defaults $ site-defaults)))

(some #{"asd"} (map :username users))