package com.tnj.if_else.utils.helperClasses;

import com.tnj.if_else.architecture.concreteEntities.built_in_workflow.HeadPhoneMagicBuilder;
import com.tnj.if_else.architecture.concreteEntities.built_in_workflow.ProfilerBuilder;
import com.tnj.if_else.architecture.concreteEntities.built_in_workflow.ReminderBuilder;
import com.tnj.if_else.architecture.concreteEntities.built_in_workflow.SendMessageBuilder;
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflowDetailsProxy;

import java.util.ArrayList;

public class BIWorkflowProviders {

    private static BIWorkflowProviders providers;
    private ArrayList<BuiltInWorkflowDetailsProxy> detailsProxies;

    public static BIWorkflowProviders getInstance(){
        return providers == null ? providers = new BIWorkflowProviders() : providers;
    }
    private BIWorkflowProviders(){
        detailsProxies = new ArrayList<>();
        detailsProxies.add(new ProfilerBuilder().getDetails());
        detailsProxies.add(new ReminderBuilder().getDetails());
        detailsProxies.add(new HeadPhoneMagicBuilder().getDetails());
        detailsProxies.add(new SendMessageBuilder().getDetails());
    }

    public ArrayList<BuiltInWorkflowDetailsProxy> getAllWorkflow(){
        return detailsProxies;
    }

    public BuiltInWorkflowDetailsProxy find(String id){
        for(BuiltInWorkflowDetailsProxy proxy : detailsProxies)
            if(id.equals(proxy.getId()))
                return proxy;
        return null;
    }
}
