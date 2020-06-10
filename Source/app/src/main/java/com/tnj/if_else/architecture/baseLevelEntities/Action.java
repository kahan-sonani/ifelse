package com.tnj.if_else.architecture.baseLevelEntities;

import android.content.Context;

import com.tnj.if_else.utils.helperClasses.EntitySet;
import com.tnj.if_else.utils.interfaces.IAction;

import java.util.HashMap;

public class Action implements IAction {

    protected HashMap<String, Object> configuration;
    protected EntitySet actionDetails;

    public EntitySet getActionDetails() {
        return actionDetails;
    }

    public Action setActionDetails(EntitySet actionDetails) {
        this.actionDetails = actionDetails;return this;
    }

    protected Action() {
        configuration = new HashMap<>();
        actionDetails = new EntitySet();
    }

    public HashMap<String, Object> getConfiguration() {
        return configuration;
    }

    public Action setConfiguration(HashMap<String, Object> configuration) {
        this.configuration = configuration;
        return this;
    }

    public <T> Action addConfiguration(String key, T object) {
        configuration.put(key, object);
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

    @Override
    public void performAction(Context context) {
        //Nothing doing here, its for the sake of inheritance..
    }
}
