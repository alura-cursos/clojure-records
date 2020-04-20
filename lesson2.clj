(ns hospital2.lesson2
  (:use clojure.pprint))

(defrecord Patient [id name birthdate])

; PrivatePatient = Patient
; InsuredPatient = Patient + Health Insurance

; (defrecord InsuredPatient INHERITS Patient [health-insurance])

(defrecord PrivatePatient [id name birthdate])
(defrecord InsuredPatient [id name birthdate health-insurance])

;needs-authorization?
;private >= 50
;insured - check if exam is covered

;(defn needs-authorization?
;  [patient exam cost]
;  (if (= PrivatePatient (type patient))
;    (>= cost 50)
;
;    (if (= InsuredPatient (type patient))
;           (let [health-insurance (get patient :health-insurance)]
;             (not (some #(= % exam) health-insurance)))
;           true))
;  )

(defprotocol Authorizable
  (needs-authorization? [patient exam cost]))

(extend-type PrivatePatient
  Authorizable
  (needs-authorization? [patient exam cost]
    (>= cost 50)))

(extend-type InsuredPatient
  Authorizable
  (needs-authorization? [patient exam cost]
    (let [health-insurance (get patient :health-insurance)]
      (not (some #(= % exam) health-insurance)))))

(let [private-patient (->PrivatePatient 15, "William", "09/18/1981")
      insured-patient (->InsuredPatient 15, "William", "09/18/1981", [:x-ray, :ultrasound])]
  (pprint (needs-authorization? private-patient, :x-ray, 500))
  (pprint (needs-authorization? private-patient, :x-ray, 40))
  (pprint (needs-authorization? insured-patient, :x-ray, 999999))
  (pprint (needs-authorization? insured-patient, :blood-test, 999999))
  )

;(defrecord InsuredPatient
;  [id name birthdate health-insurance]
;  Authorizable
;  (needs-authorization? [patient exam cost]
;    (let [health-insurance (get patient :health-insurance)]
;      (not (some #(= % exam) health-insurance)))))

;9/18
;18/09

(defprotocol Dateable
  (to-ms [this]))

(extend-type java.lang.Number
  Dateable
  (to-ms [this] this))

(extend-type java.util.Date
  Dateable
  (to-ms [this] (.getTime this)))

(extend-type java.util.Calendar
  Dateable
  (to-ms [this] (to-ms (.getTime this))))


(pprint (to-ms 56))
(pprint (to-ms (java.util.Date.)))
(pprint (to-ms (java.util.GregorianCalendar.)))
(pprint (to-ms "William"))