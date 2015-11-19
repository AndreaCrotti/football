(defproject football "0.1.0-SNAPSHOT"
  :description "Put some science in team selections"
  :main football.main
  :url "https://github.com/AndreaCrotti/football"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/clj" "src/cljc"]

  :test-paths ["test/clj" "test/cljc"]

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170" :scope "provided"]
                 [prismatic/plumbing "0.5.2"]
                 [devcards "0.2.1"]

                 [org.clojure/core.match "0.3.0-alpha4"]
                 [org.clojure/core.async "0.2.374"]
                 [org.clojure/core.memoize "0.5.8"]
                 [org.clojure/tools.cli "0.3.3"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/core.typed "0.3.19"]
                 [org.clojure/math.combinatorics "0.1.1"]

                 [ring "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [slester/ring-browser-caching "0.1.1"]
                 [bk/ring-gzip "0.1.1"]
                 [compojure "1.4.0"]
                 [enlive "1.1.6"]
                 [org.omcljs/om "0.9.0"]
                 [environ "1.0.1"]
                 [org.clojure/test.check "0.9.0"]

                 [org.xerial/sqlite-jdbc "3.8.11.2"]
                 [org.postgresql/postgresql "9.2-1003-jdbc4"]
                 [org.clojure/java.jdbc "0.4.2"]
                 [yesql "0.5.1"]

                 [jarohen/phoenix "0.1.2"]
                 [com.stuartsierra/component "0.3.0"]

                 [prismatic/schema "1.0.3"]]
  
  :plugins [[lein-cljsbuild "1.0.5"]
            [lein-environ "1.0.0"]]

  :min-lein-version "2.5.0"

  :uberjar-name "football.jar"

  :cljsbuild {:builds {:app {:source-paths ["src/cljs" "src/cljc"]
                             :compiler {:output-to     "resources/public/js/app.js"
                                        :output-dir    "resources/public/js/out"
                                        :source-map    "resources/public/js/out.js.map"
                                        :preamble      ["react/react.min.js"]
                                        :optimizations :none
                                        :pretty-print true}}}}

  :profiles {:dev {:source-paths ["env/dev/clj"]
                   :test-paths ["test/clj"]

                   :dependencies [[figwheel "0.5.0-1"]
                                  [figwheel-sidecar "0.5.0-1"]
                                  [com.cemerick/piggieback "0.2.1"]
                                  [org.clojure/tools.nrepl "0.2.12"]
                                  [weasel "0.7.0"]]

                   :repl-options {:init-ns football.server
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

                   :plugins [[lein-figwheel "0.3.9"]]

                   :figwheel {:http-server-root "public"
                              :server-port 3449
                              :css-dirs ["resources/public/css"]
                              :ring-handler football.server/http-handler}

                   :env {:is-dev true
                         :browser-caching {"text/javascript" 0
                                           "text/html" 0}}

                   :cljsbuild {:test-commands { "test" ["phantomjs" "env/test/js/unit-test.js" "env/test/unit-test.html"] }
                               :builds {:app {:source-paths ["env/dev/cljs"]}
                                        :test {:source-paths ["src/cljs" "test/cljs" "src/cljc" "test/cljc"]
                                               :compiler {:output-to     "resources/public/js/app_test.js"
                                                          :output-dir    "resources/public/js/test"
                                                          :source-map    "resources/public/js/test.js.map"
                                                          :preamble      ["react/react.min.js"]
                                                          :optimizations :whitespace
                                                          :pretty-print  false}
                                               :notify-command  ["phantomjs" "bin/speclj" "resources/public/js/app_test.js"]
                                               }}}}

             :uberjar {:source-paths ["env/prod/clj"]
                       :hooks [leiningen.cljsbuild]
                       :env {:production true
                             :browser-caching {"text/javascript" 604800
                                               "text/html" 0}}
                       :omit-source true
                       :aot :all
                       :main football.server
                       :cljsbuild {:builds {:app
                                            {:source-paths ["env/prod/cljs"]
                                             :compiler
                                             {:optimizations :advanced
                                              :pretty-print false}}}}}})
