package com.tnj.if_else.firebaseRepository.schema;

public class WorkflowSchema {

    public static final String ID = "details.id";

    public static final String WORKFLOW_DETAILS = "details";
    public static final String WORKFLOW_SETTINGS = "settings";

    public static final String ACTION_CONFIGURATION = "settings.actionList.configuration";
    public static final String TRIGGER_CONFIGURATION = "settings.triggerList.configuration";
    public static final String CRITERIA_CONFIGURATION = "settings.criteriaList.configuration";

    public static final String NAME = "details.name";
    public static final String DESCRIPTION = "details.description";
    public static final String STATE = "settings.state";
    public static final String COLOR = "details.color";

    public static final String BUILTIN_ACTION_CLASS = "initialSettings.action";
    public static final String BUILTIN_TRIGGER_CLASS = "initialSettings.trigger";
    public static final String BUILTIN_CRITERIA_CLASS = "initialSettings.criteria";

    public static final String ACTION_COUNT = "settings.actionCount";
    public static final String TRIGGER_COUNT = "settings.triggerCount";
    public static final String CRITERIA_COUNT = "settings.criteriaCount";

    public static final String ALL_ACTION = "settings.actionList";
    public static final String ALL_TRIGGER = "settings.triggerList";
    public static final String ALL_CRITERIA = "settings.criteriaList";

    public static final String ACTION_DETAILS = "settings.actionDetails";
    public static final String TRIGGER_DETAILS = "settings.triggerDetails";
    public static final String CRITERIA_DETAILS = "settings.criteriaDetails";
}
