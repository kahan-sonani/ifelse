package com.tnj.if_else.architecture.concreteEntities.built_in_workflow;

import com.tnj.if_else.architecture.baseLevelEntities.Action;
import com.tnj.if_else.architecture.baseLevelEntities.Criteria;
import com.tnj.if_else.architecture.baseLevelEntities.Trigger;
import com.tnj.if_else.architecture.concreteEntities.actionClasses.VolumeChangeAction;
import com.tnj.if_else.architecture.concreteEntities.triggerClasses.DayTimeTrigger;
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflowDetailsProxy;
import com.tnj.if_else.utils.interfaces.BuiltInWorkflowBuilder;
import com.tnj.if_else.utils.lookup.ColorUtility;

public class ProfilerBuilder implements BuiltInWorkflowBuilder {

    public static final String ID = "Profiler";
    public static final String NAME = ID;
    public static final String DESCRIPTION = "Built-in service which helps you to automate audio settings of your phone according to time.";

    @Override
    public BuiltInWorkflowDetailsProxy getDetails() {
        return new BuiltInWorkflowDetailsProxy(ID,NAME, DESCRIPTION)
                .setColor(ColorUtility.getColorByIndex(7))
                .setInitialSettings(new BuiltInWorkflowDetailsProxy.initialSettings() {
                    @Override
                    public Action getAction() {
                        return new VolumeChangeAction();
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








