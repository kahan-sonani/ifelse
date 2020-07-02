package com.tnj.if_else.architecture.concreteEntities.built_in_workflow;

import com.tnj.if_else.architecture.baseLevelEntities.Action;
import com.tnj.if_else.architecture.baseLevelEntities.Criteria;
import com.tnj.if_else.architecture.baseLevelEntities.Trigger;
import com.tnj.if_else.architecture.concreteEntities.actionClasses.SendSMSAction;
import com.tnj.if_else.architecture.concreteEntities.triggerClasses.DayTimeTrigger;
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflowDetailsProxy;
import com.tnj.if_else.utils.helperClasses.ColorUtility;
import com.tnj.if_else.utils.interfaces.BuiltInWorkflowBuilder;

public class SendMessageBuilder implements BuiltInWorkflowBuilder {

    public static final String ID = "SendMessage";
    public static final String NAME = ID;
    public static final String DESCRIPTION = "This builtIn workflow will sent a custom message written by you to specified contact at any user given time.";

    @Override
    public BuiltInWorkflowDetailsProxy getDetails() {
        return new BuiltInWorkflowDetailsProxy(ID,NAME, DESCRIPTION)
                .setColor(ColorUtility.getColorByIndex(2))
                .setInitialSettings(new BuiltInWorkflowDetailsProxy.initialSettings() {
                    @Override
                    public Action getAction() {
                        return new SendSMSAction();
                    }

                    @Override
                    public Trigger getTrigger() {
                        return new DayTimeTrigger();
                    }

                    @Override
                    public Criteria getCriteria() {
                        return null;
                    }
                });
    }
}
