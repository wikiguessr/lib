(ns wikiguessr-lib.core
  (:require [ajax.core :refer [GET]]))


(defn ^:export get-wiki []
  (GET "https://en.wikipedia.org/w/api.php?action=query&format=json&list=random&rnnamespace=0&rnlimit=1&origin=*"))
