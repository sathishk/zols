/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zols.datastore.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import org.zols.datastore.DataStore;
import org.zols.datastore.query.Filter;
import org.zols.datastore.query.Page;
import org.zols.datastore.query.Query;
import org.zols.datatore.exception.DataStoreException;


public class DataService {
    
    private final DataStore dataStore;

    public DataService(DataStore dataStore) {
        this.dataStore = dataStore;
    }
    
    public String getIdField(String schemaId) throws DataStoreException {
        String idPropertyName = null ;
        List<String> idProps = dataStore.getSchemaManager().getJsonSchema(schemaId).getIdPropertyNames(); 
        if(!idProps.isEmpty()) {
            idPropertyName = idProps.get(0);
        }
        return idPropertyName;
    }

    public Map<String, Object> create(String schemaName, Map<String, Object> jsonData,Locale loc) throws DataStoreException {
        return dataStore.create(schemaName, jsonData,loc);
    }

    public Optional<Map<String, Object>> read(String schemaName, String id,Locale loc) throws DataStoreException {
        return dataStore.read(schemaName, id,loc);
    }
    
    public Map<String, Object> update(String schemaId, String id,Map<String, Object> jsonData,Locale loc) throws DataStoreException {
        return dataStore.update(schemaId,id, jsonData,loc);
    }

    public boolean delete(String schemaName, String name) throws DataStoreException {
        return dataStore.delete(schemaName, name);
    }

    public List<Map<String, Object>> list(String schemaName,Locale loc) throws DataStoreException {
        return dataStore.list(schemaName,loc);
    }
    
    public Page<Map<String, Object>> list(String schemaName, Query query,
            Integer pageNumber, Integer pageSize,Locale loc) throws DataStoreException {

        return dataStore.list(schemaName, query, pageNumber, pageSize);
    }

    public Page<Map<String, Object>> list(String schemaName, String queryString,
            Integer pageNumber, Integer pageSize,Locale loc) throws DataStoreException {
        Query query = null;
        if (queryString != null) {
            query = new Query();
            query.addFilter(new Filter(Filter.Operator.FULL_TEXT_SEARCH, queryString + "*"));
        }
        return dataStore.list(schemaName, query, loc,pageNumber, pageSize);
    
    }


}