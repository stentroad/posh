(ns posh.rum
  (:require [posh.plugin-base :as base
             :include-macros]
            [datascript.core :as d]
            [rum.core :as rum]))

(defn make-reaction [query-ratom & {:keys [auto-run on-set on-dispose]}]
  (rum/derived-atom [query-ratom]
      ::reaction-key
    (fn [qr]
      qr)))

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
              :ratom         atom
              :make-reaction make-reaction}]
    (assoc dcfg :pull (partial base/safe-pull dcfg))))

(base/add-plugin dcfg)
