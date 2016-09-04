(ns ignorabilis.common.env
  #?(:clj
     (:require [environ.core :as environ]
               [clojure.edn :as clj-edn])
     :cljs
     (:require [cljs.reader :as cljs-reader]))
  #?(:cljs
     (:require-macros [ignorabilis.common.env :refer [cljs-env]])))

#?(:clj
   (defmacro cljs-env []
     "Environ macro for cljs usage.
     Use with caution - only compile time values are available in cljs."
     environ/env))

#?(:clj  (defn ienv [kw]
           (let [env-var (environ/env kw)
                 parsed-env-var (clj-edn/read-string env-var)]
             parsed-env-var))
   :cljs (defn ienv [kw]
           (let [env-var ((cljs-env) kw)
                 parsed-env-var (cljs-reader/read-string env-var)]
             parsed-env-var)))

(def dev? (ienv :is-dev))

