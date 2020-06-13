package com.tnj.if_else.architecture.concreteEntities.built_in_workflow;

import com.tnj.if_else.architecture.baseLevelEntities.Action;
import com.tnj.if_else.architecture.baseLevelEntities.Criteria;
import com.tnj.if_else.architecture.baseLevelEntities.Trigger;
import com.tnj.if_else.architecture.concreteEntities.actionClasses.SendNotificationAction;
import com.tnj.if_else.architecture.concreteEntities.triggerClasses.CalendarEventTrigger;
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflowDetailsProxy;
import com.tnj.if_else.utils.helperClasses.ColorUtility;
import com.tnj.if_else.utils.interfaces.BuiltInWorkflowBuilder;



public class ReminderBuilder implements BuiltInWorkflowBuilder {

    public static final String ID = "RemindMe";
    public static final String NAME = ID;
    public static final String DESCRIPTION = "Reminds you of any unfinished important task, " +
            "It generates a popup Android notification & sound to get you reminded.";

    @Override
    public BuiltInWorkflowDetailsProxy getDetails() {
        return new BuiltInWorkflowDetailsProxy(ID,NAME, DESCRIPTION).setColor(ColorUtility.getColorByIndex(9))
                .setInitialSettings(new BuiltInWorkflowDetailsProxy.initialSettings() {
                    @Override
                    public Action getAction() {
                        return new SendNotificationAction();
                    }

                    @Override
                    public Trigger getTrigger() {
                        return new CalendarEventTrigger();
                    }

                    @Override
                    public Criteria getCriteria() {
                        return null;
                    }
                });
    }
}
