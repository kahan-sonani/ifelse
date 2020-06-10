package com.tnj.if_else.architecture.secondLevelEntities;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.tnj.if_else.architecture.baseLevelEntities.Action;
import com.tnj.if_else.architecture.baseLevelEntities.Criteria;
import com.tnj.if_else.architecture.baseLevelEntities.Trigger;
import com.tnj.if_else.architecture.baseLevelEntities.WorkflowDetailsProxy;
import com.tnj.if_else.utils.helperClasses.Color;

@IgnoreExtraProperties
public class BuiltInWorkflowDetailsProxy extends WorkflowDetailsProxy {

    private String id;

    @Exclude
    private initialSettings initialSettings;

    public BuiltInWorkflowDetailsProxy(){ super(); }

    public BuiltInWorkflowDetailsProxy(String id, String name, String description) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Exclude
    public BuiltInWorkflowDetailsProxy setInitialSettings(BuiltInWorkflowDetailsProxy.initialSettings initialSettings) {
        this.initialSettings = initialSettings;
        return this;
    }

    @Exclude
    public BuiltInWorkflowDetailsProxy.initialSettings getInitialSettings() {
        return initialSettings;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public BuiltInWorkflowDetailsProxy setName(String name) {
        return this;
    }

    @Override
    public BuiltInWorkflowDetailsProxy setDescription(String description) {
        return this;
    }

    @Override
    public BuiltInWorkflowDetailsProxy setColor(Color color) {
        this.color = color;
        return this;
    }
    public interface initialSettings {
        Action getAction();
        Trigger getTrigger();
        Criteria getCriteria();
    }
}
