(ns metabase.collections.core
  "Main namespace for interacting with collections"
  (:require
   [metabase.collections.models.collection]
   [methodical.core :as methodical]
   [potemkin :as p]
   [toucan2.core :as t2]))

(comment
  metabase.collections.models.collection/keep-me)

;; CollectionAccess model for tracking access permissions
(doto :model/CollectionAccess
  (derive :metabase/model)
  (derive :hook/timestamped?))

(methodical/defmethod t2/table-name :model/CollectionAccess [_model] :collection_access)

(p/import-vars
 [metabase.collections.models.collection
  remote-synced-collection
  remote-synced-collection?
  check-for-remote-sync-update
  check-non-remote-synced-dependencies
  check-remote-synced-dependents
  library-collection
  create-library-collection!
  moving-into-remote-synced?
  moving-from-remote-synced?
  non-remote-synced-dependencies])
