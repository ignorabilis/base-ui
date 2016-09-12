(ns ignorabilis.common.env
  #?(:clj
     (:require [environ.core :as environ]
               [clojure.edn :as clj-edn])
     :cljs
     (:require [cljs.reader :as cljs-reader]))
  #?(:cljs
     (:require-macros [ignorabilis.common.env :refer [compile-time-env]])))

#?(:clj
   (defmacro compile-time-env []
     "Environ macro, containing only compile-time values.
     Use with caution - only compile time values are available."
     environ/env))

#?(:clj  (defn ienv [kw]
           (let [env-var (or (environ/env kw) ((compile-time-env) kw))
                 parsed-env-var (clj-edn/read-string env-var)]
             parsed-env-var))
   :cljs (defn ienv [kw]
           (let [env-var ((compile-time-env) kw)
                 parsed-env-var (cljs-reader/read-string env-var)]
             parsed-env-var)))

(def dev? (ienv :is-dev))

