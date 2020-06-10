package com.tnj.if_else.architecture.baseLevelEntities;

import com.tnj.if_else.utils.helperClasses.EntitySet;

import java.util.HashMap;

public class Trigger {

    protected HashMap<String, Object> configuration;
    protected EntitySet triggerDetails;


    public EntitySet getTriggerDetails() {
        return triggerDetails;
    }

    public Trigger setTriggerDetails(EntitySet triggerDetails) {
        this.triggerDetails = triggerDetails;
        return this;
    }

    protected Trigger() {
        configuration = new HashMap<>();
        triggerDetails = new EntitySet();
    }

    public HashMap<String, Object> getConfiguration() {
        return configuration;
    }

    public Trigger setConfiguration(HashMap<String, Object> configuration) {
        this.configuration = configuration;
        return this;
    }

    public <T> Trigger addConfiguration(String key, T value) {
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
