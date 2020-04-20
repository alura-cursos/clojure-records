(ns hospital2.logic
  (:require [hospital2.model :as h.model]))

(defn this-moment
  []
  (h.model/to-ms (java.util.Date.)))