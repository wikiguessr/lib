(ns wikiguessr-lib.core
  (:require [ajax.core :refer [GET]]))

(defn get-topic [handler]
  (GET "https://en.wikipedia.org/w/api.php"
       :params {:action "query"
                :format "json"
                :list "random"
                :rnnamespace 0
                :rnlimit 1
                :origin "*"}
       :response-format :json
       :keywords? true
       :handler handler)
  nil)

(defn add-topic-fn [topics]
  (fn [response]
    (swap! topics conj (:title ((get-in response [:query :random]) 0)))))

(defn return-topics-fn [number-of-topics topics-callback]
  (fn [key atom old-state new-state]
    (when (>= (count new-state) number-of-topics)
      (topics-callback (clj->js new-state))
      (remove-watch atom key))))

(defn ^:export start-round [number-of-topics topics-callback wordcloud-callback]
  (let [topics (atom [])]
    (add-watch topics :return-topics (return-topics-fn number-of-topics topics-callback))
    (dotimes [_ number-of-topics] (get-topic (add-topic-fn topics)))))
