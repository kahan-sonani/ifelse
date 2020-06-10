package com.tnj.if_else.architecture.baseLevelEntities;

import com.tnj.if_else.utils.helperClasses.EntitySet;

import java.util.HashMap;

public class Criteria {

    protected HashMap<String, Object> configuration;
    protected EntitySet criteriaDetails;

    public EntitySet getCriteriaDetails() {
        return criteriaDetails;
    }

    public Criteria setCriteriaDetails(EntitySet criteriaDetails) {
        this.criteriaDetails = criteriaDetails;
        return  this;
    }

    protected Criteria() {
        configuration = new HashMap<>();
        criteriaDetails = new EntitySet();
    }

    public HashMap<String, Object> getConfiguration() {
        return configuration;
    }

    public Criteria setConfiguration(HashMap<String, Object> configuration) {
        this.configuration = configuration;
        return this;
    }
    public <T> Criteria addConfiguration(String key, T value) {
        configuration.put(key, value);
        return this;
    }

    public <T> T getConfiguration(String key, Class<T> type) {
        if (configuration.containsKey(key))
            return type.cast(configuration.get(key));
        else
            throw new IllegalArgumentException(key + ": doesn't exists!");
    }

    public void removeConfiguration() {
        configuration.clear();
    }
}
