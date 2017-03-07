(ns posh.reagent
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [posh.plugin-base :as base
              :include-macros]
            [datascript.core :as d]
            [reagent.core :as r]
            [reagent.ratom :as ra]))

(defn make-reaction [query-ratom & {:keys [auto-run on-set on-dispose]}]
  (ra/make-reaction (fn []
                      ;;(println "RENDERING: " storage-key)
                      @query-ratom)
                    :auto-run auto-run
                    :on-set on-set
                    :on-dispose on-dispose))

(def dcfg
  (let [dcfg {:db            d/db
              :pull*         d/pull
              :q             d/q
              :filter        d/filter
              :with          d/with
              :entid         d/entid
              :transact!     d/transact!
              :listen!       d/listen!
              :conn?         d/conn?
              :ratom         r/atom
              :make-reaction make-reaction}]
   (assoc dcfg :pull (partial base/safe-pull dcfg))))

(base/add-plugin dcfg)
