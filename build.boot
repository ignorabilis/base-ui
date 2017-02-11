#!/usr/bin/env boot

(set-env! :resource-paths #{"src/clj" "src/cljc" "src/cljs" "resources"}
          :dependencies '[[adzerk/boot-cljs "1.7.228-1" :scope "test"]
                          [adzerk/boot-cljs-repl "0.3.2" :scope "test"]
                          [adzerk/boot-reload "0.4.12" :scope "test"]
                          [com.cemerick/piggieback "0.2.1" :scope "test"]
                          [weasel "0.7.0" :scope "test"]
                          [org.clojure/tools.nrepl "0.2.12" :scope "test"]

                          [org.clojure/clojure "1.8.0"]
                          [org.clojure/clojurescript "1.9.227"]

                          [environ "1.1.0"]
                          [boot-environ "1.1.0"]
                          [mount "0.1.10"]
                          [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                          [com.taoensso/sente "1.10.0"]
                          [com.taoensso/timbre "4.7.0"]
                          [http-kit "2.1.19"]
                          ;; [org.immutant/web       "2.0.0-beta2"]
                          [ring "1.4.0-RC1"]
                          [ring/ring-defaults "0.1.5"]
                          [bidi "2.0.9"]
                          [compojure "1.3.4"]
                          [hiccup "1.0.5"]
                          [reagent "0.6.0"]
                          [reagent-utils "0.2.0"]
                          [com.cognitect/transit-clj "0.8.288"]
                          [com.cognitect/transit-cljs "0.8.239"]])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
         '[adzerk.boot-reload :refer [reload]]
         '[environ.boot :refer [environ]])

(def version "0.1.0.0-SNAPSHOT")

(task-options!
  push {:ensure-branch nil}
  pom {:project     'ignorabilis
       :version     version
       :description "Personal website"
       :url         "https://bitbucket.org/irina-yaroslavova/ignorabilis"
       :license     {"Eclipse Public License"
                     "http://www.eclipse.org/legal/epl-v10.html"}})

(defn- generate-lein-project-file! [& {:keys [keep-project] :or {:keep-project true}}]
  (require 'clojure.java.io)
  (let [pfile ((resolve 'clojure.java.io/file) "project.clj")
        ; Only works when pom options are set using task-options!
        {:keys [project version]} (:task-options (meta #'boot.task.built-in/pom))
        prop #(when-let [x (get-env %2)] [%1 x])
        head (list* 'defproject (or project 'boot-project) (or version "0.0.0-SNAPSHOT")
                    (concat
                      (prop :url :url)
                      (prop :license :license)
                      (prop :description :description)
                      [:dependencies (get-env :dependencies)
                       :repositories (get-env :repositories)
                       :source-paths (vec (concat (get-env :source-paths)
                                                  (get-env :resource-paths)))]))
        proj (pp-str head)]
    (if-not keep-project (.deleteOnExit pfile))
    (spit pfile proj)))

(deftask lein-generate
         "Generate a leiningen `project.clj` file.
          This task generates a leiningen `project.clj` file based on the boot
          environment configuration, including project name and version (generated
          if not present), dependencies, and source paths. Additional keys may be added
          to the generated `project.clj` file by specifying a `:lein` key in the boot
          environment whose value is a map of keys-value pairs to add to `project.clj`."
         []
         (generate-lein-project-file! :keep-project true))

(defn get-boot-file [coll file-name]
  (if-let [benv (first (filter #(= (tmp-path %) file-name) coll))]
    (slurp (tmp-file benv))
    "not found"))

(deftask trace-task
         "Prints a file from the fileset."
         []
         (fn [next-task]
           (fn [fileset]
             (let [uf (user-files fileset)
                   if (input-files fileset)
                   of (output-files fileset)
                   uenv (get-boot-file if ".boot-env")]
               (prn uenv))
             (next-task fileset))))

(deftask ignorabilis-build
         "Builds an uberjar of the ignorabilis project that can be run with java -jar"
         []
         (comp
           (environ :env {:is-dev    "false"
                          :http-port "80"})
           (cljs :optimizations :advanced :compiler-options {:externs ["public/common/common.ext.js"]})
           (aot :all true)
           (pom :project 'ignorabilis
                :version version)
           (uber)
           (jar :main 'ignorabilis.core)
           (target)))

(deftask ignorabilis-dev []
         (comp
           (environ :env {:is-dev    "true"
                          :http-port "80"})
           (watch)
           (reload :ip "0.0.0.0" :port 40002)
           (cljs-repl :ip "0.0.0.0" :port 40003 :nrepl-opts {:bind "0.0.0.0" :port 40001})
           (cljs :source-map true)
           (repl :server true :bind "0.0.0.0" :port 40000)))

(defn -main [& args]
  (require 'ignorabilis.core)
  (apply (resolve 'ignorabilis.core/-main) args))
