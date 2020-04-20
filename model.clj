(ns hospital2.model)

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