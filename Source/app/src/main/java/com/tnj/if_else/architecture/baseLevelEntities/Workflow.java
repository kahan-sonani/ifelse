package com.tnj.if_else.architecture.baseLevelEntities;

public abstract class Workflow {

    protected String id;
    protected WorkflowSettingsProxy settings;
    protected WorkflowDetailsProxy details;

    public Workflow() {
        settings = new WorkflowSettingsProxy();
    }

    public Workflow(String id){
        this();
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public WorkflowDetailsProxy getDetails() {
        return details;
    }

    public WorkflowSettingsProxy getSettings() {
        return settings;
    }

    public static class State {
        public static final String DEACTIVATED = "Disabled";
        public static final String ACTIVATED = "Running";
    }

    public static class Flag {
        public static final String NOT_CONFIGURED = "Not configured";
        public static final String CONFIGURED = "Configured";
    }
}
