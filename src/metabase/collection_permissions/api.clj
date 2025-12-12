(ns metabase.collection-permissions.api
  "API endpoints for collection permissions management."
  (:require
   [metabase.api.common :as api]
   [metabase.api.macros :as api.macros]
   [metabase.util.malli.schema :as ms]
   [toucan2.core :as t2]))

(api.macros/defendpoint :get "/graph"
  "Get the current collection permissions graph with revision info."
  [_route-params
   _query-params
   _body]
  (let [graph (t2/select :model/PermissionsGraph)]
    {:groups (or (:groups graph) {})
     :revision (or (:revision graph) 0)}))

(api.macros/defendpoint :put "/graph"
  "Update collection permissions graph."
  [_route-params
   {:keys [skip-graph]} :- [:map
                            [:skip-graph {:optional true} [:maybe :boolean]]]
   body :- [:map
            [:groups :map]
            [:revision ms/IntGreaterThanOrEqualToZero]]]
  (let [current-revision (or (:revision (t2/select-one :model/PermissionsGraph)) 0)]
    (when-not (= (:revision body) current-revision)
      (throw (ex-info "Revision mismatch" {:status-code 409})))
    (t2/update! :model/PermissionsGraph body)
    {:status "success"}))

(api.macros/defendpoint :get "/model-collection/:id"
  "Get permissions for a specific model collection."
  [{:keys [id]} :- [:map
                    [:id ms/PositiveInt]]]
  (let [collection (api/read-check (t2/select-one :model/Collection id))
        permissions (t2/select :model/Permissions :collection_id id)]
    {:collection collection
     :permissions permissions}))
