package com.tnj.if_else.architecture.concreteEntities.built_in_workflow;

import com.tnj.if_else.architecture.baseLevelEntities.Action;
import com.tnj.if_else.architecture.baseLevelEntities.Criteria;
import com.tnj.if_else.architecture.baseLevelEntities.Trigger;
import com.tnj.if_else.architecture.concreteEntities.actionClasses.OpenApplicationAction;
import com.tnj.if_else.architecture.concreteEntities.triggerClasses.HeadphoneInsertRemovedTrigger;
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflowDetailsProxy;
import com.tnj.if_else.utils.helperClasses.ColorUtility;
import com.tnj.if_else.utils.interfaces.BuiltInWorkflowBuilder;

public class HeadPhoneMagicBuilder implements BuiltInWorkflowBuilder {

    public static final String ID = "Headphone Magic";
    public static final String NAME = "Headphone magic 🎧";
    public static final String DESCRIPTION = "This workflow opens any music application selected by the user when it is active whenever headphone plug"
            + " is inserted in to the 3.5mm headphone jack of the smartphone.";

    @Override
    public BuiltInWorkflowDetailsProxy getDetails() {
        return new BuiltInWorkflowDetailsProxy(ID,NAME,DESCRIPTION).setColor(ColorUtility.getColorByIndex(3))
                .setInitialSettings(new BuiltInWorkflowDetailsProxy.initialSettings() {
                    @Override
                    public Action getAction() {
                        return new OpenApplicationAction();
                    }

                    @Override
                    public Trigger getTrigger() {
                        return new HeadphoneInsertRemovedTrigger();
                    }

                    @Override
                    public Criteria getCriteria() {
                        return null;
                    }
                });
    }
}
