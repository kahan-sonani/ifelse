package com.tnj.if_else.architecture.baseLevelEntities;

import java.util.ArrayList;

public class WorkflowSettingsProxy {

    protected ArrayList<Action> actionList;
    protected ArrayList<Trigger> triggerList;
    protected ArrayList<Criteria> criteriaList;
    protected String state;
    protected String flag;

    public WorkflowSettingsProxy(){
        actionList = new ArrayList<>();
        triggerList = new ArrayList<>();
        criteriaList = new ArrayList<>();
        state = Workflow.State.DEACTIVATED;
        flag = Workflow.Flag.NOT_CONFIGURED;
    }

    public Action getAction(int index){
        return (index < actionList.size())?actionList.get(index) : null;
    }
    public Trigger getTrigger(int index){
        return (index < triggerList.size())? triggerList.get(index) : null;
    }
    public Criteria getCriteria(int index){
        return (index < criteriaList.size())?criteriaList.get(index) : null;
    }
    public WorkflowSettingsProxy addAction(Class<Action> actionClass) {
        try {
            actionList.add(actionClass.newInstance());
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return this;
    }

    public WorkflowSettingsProxy addTrigger(Class<Trigger> triggerClass) {
        try {
            triggerList.add(triggerClass.newInstance());
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return this;
    }

    public WorkflowSettingsProxy addCriteria(Class<Criteria> criteriaClass) {
        try {
            criteriaList.add(criteriaClass.newInstance());
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return this;
    }

    public WorkflowSettingsProxy removeActionAt(int index) {
        if(index < actionList.size())
            actionList.remove(index);
        return this;
    }

    public WorkflowSettingsProxy removeTriggerAt(int index) {
        if(index < triggerList.size())
            triggerList.remove(index);
        return this;
    }

    public WorkflowSettingsProxy removeCriteriaAt(int index) {
        if(index < criteriaList.size())
            criteriaList.remove(index);
        return this;
    }

    public WorkflowSettingsProxy remove(Action action) {
        if(actionList.contains(action))
            actionList.remove(action);
        return this;
    }

    public WorkflowSettingsProxy remove(Trigger trigger) {
        if(triggerList.contains(trigger))
            triggerList.remove(trigger);
        return this;
    }

    public WorkflowSettingsProxy remove(Criteria criteria) {
        if(criteriaList.contains(criteria))
            criteriaList.remove(criteria);
        return this;
    }

    public ArrayList<Criteria> getCriteriaList(){
        return criteriaList;
    };

    public WorkflowSettingsProxy setTriggerList(ArrayList<Trigger> triggerList){
        this.triggerList = triggerList;
        return this;
    };

    public ArrayList<Action> getActionList(){
        return actionList;
    }

    public WorkflowSettingsProxy setCriteriaList(ArrayList<Criteria> criteriaList){
        this.criteriaList = criteriaList;
        return this;
    }

    public WorkflowSettingsProxy setActionList(ArrayList<Action> actionList){
        this.actionList = actionList;
        return  this;
    }

    public ArrayList<Trigger> getTriggerList(){
        return triggerList;
    }

    public  String getFlag(){
        return flag;
    }

    public String getState(){
        return state;
    }

    public WorkflowSettingsProxy setState(String state){
        this.state = state;
        return this;
    }

    public int getActionCount(){
        return actionList.size();
    }
    public int getTriggerCount(){
        return triggerList.size();
    }
    public int getCriteriaCount(){
        return criteriaList.size();
    }
}
