(ns lab2.core
  (:require [clojure.core.async :as a]))


(defn new-abs [n] (max n (- n)))
(def input-channel (a/chan 15))
(def output-channel (a/chan 15))
(a/onto-chan! input-channel [0 11 22 25 26 30 40 50 55 60 66 12 0 14 15])


(defn residual [in-chan out-chan threshold]
  (a/go
    (loop [previous (a/<! in-chan)]
      (when-some [value (a/<! in-chan)]
        (when (<= threshold (new-abs (- value previous)))
          (println "the number is suitable" value)
          (a/>! out-chan value))
        (recur value)))))

(residual input-channel output-channel 10)
