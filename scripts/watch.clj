(require '[cljs.build.api :as b])

(b/watch "src"
  {:main 'wikiguessr-lib.core
   :output-to "out/wikiguessr_lib.js"
   :output-dir "out"})
