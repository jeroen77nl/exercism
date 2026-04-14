(ns log-levels
  (:require [clojure.string :as str]))

(defn message
   "Takes a string representing a log line
   and returns its message with whitespace trimmed."
   [s]
   (str/trim (peek (str/split s #":"))))

(message "[ERROR]: Invalid operation")

(defn log-level
  "Takes a string representing a log line
   and returns its level in lower-case."
  [s]
  (str/lower-case
   (str/replace
    (str/replace
     (first (str/split s #":"))
     "[" "")
    "]" "")))

(log-level "[ERROR]: Invalid operation")

(defn reformat
  "Takes a string representing a log line and formats it
   with the message first and the log level in parentheses."
  [s]
  (str (message s)
       " ("
       (log-level s)
       ")"))

(reformat "[INFO]: Operation completed")
