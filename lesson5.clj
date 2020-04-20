(ns hospital2.lesson5
  (:use clojure.pprint)
  (:require [hospital2.logic :as h.logic]))


(defn authorizer-type
  [request]
  (let [patient (:patient request)
        situation (:situation patient)]

    (cond (= :urgent situation) :always-authorize

          (contains? patient :health-insurance) :check-insurance

          :else :check-exam-cost))
  )

(defmulti needs-authorization-for-request? authorizer-type)

(defmethod needs-authorization-for-request?
  :always-authorize
  [request]
  false)

(defmethod needs-authorization-for-request?
  :check-exam-cost
  [request]
  (>= (:cost request) 50))

(defmethod needs-authorization-for-request?
  :check-insurance
  [request]
  (not (some #(= % (:exam request)) (:health-insurance (:patient request)))))


(let [private-patient {:id 15, :name "William", :birthdate "09/18/1981", :situation :urgent}
      insured-patient {:id 15, :name "William", :birthdate "09/18/1981", :situation :urgent, :health-insurance [:x-ray, :ultrasound]}]
  (pprint (needs-authorization-for-request? { :patient private-patient, :cost 1000, :exam :blood-test}))
  (pprint (needs-authorization-for-request? { :patient insured-patient, :cost 1000, :exam :blood-test}))
  )


(let [private-patient {:id 15, :name "William", :birthdate "09/18/1981", :situation :regular}
      insured-patient {:id 15, :name "William", :birthdate "09/18/1981", :situation :regular, :health-insurance [:x-ray, :ultrasound]}]
  (pprint (needs-authorization-for-request? { :patient private-patient, :cost 1000, :exam :blood-test}))
  (pprint (needs-authorization-for-request? { :patient insured-patient, :cost 1000, :exam :blood-test}))
  )