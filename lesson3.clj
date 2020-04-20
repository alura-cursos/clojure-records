(ns hospital2.lesson3
  (:use clojure.pprint)
  (:require [hospital2.logic :as h.logic]))

(defn downloads-patient
  [id]
  (println "Downloading patient" id)
  (Thread/sleep 1000)
  { :id id, :downloading-at (h.logic/this-moment)}
  )

(defn downloads-if-it-doesnt-exist
  [cache id downloader]
  (if (contains? cache id)
    cache
    (let [patient (downloader id)]
      (assoc cache id patient)))
  )

(defprotocol Downloadable
  (download! [this id]))

(defrecord Cache
  [cache downloader]
  Downloadable

  (download! [this id]
    (swap! cache downloads-if-it-doesnt-exist id downloader)
    (get @cache id))
  )

;(pprint (downloads-patient 15))
;(pprint (downloads-patient 30))

;(pprint (downloads-if-it-doesnt-exist {}, 15, downloads-patient))
;(pprint (downloads-if-it-doesnt-exist { 15 {:id 15} }, 15, downloads-patient))

(def patients (->Cache (atom {}), downloads-patient))
(pprint patients)
(download! patients 15)
(download! patients 30)
(download! patients 15)
(pprint patients)