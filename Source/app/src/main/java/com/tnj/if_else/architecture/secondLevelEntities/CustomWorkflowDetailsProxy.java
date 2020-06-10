package com.tnj.if_else.architecture.secondLevelEntities;

import com.tnj.if_else.architecture.baseLevelEntities.WorkflowDetailsProxy;
import com.tnj.if_else.utils.helperClasses.Color;

public class CustomWorkflowDetailsProxy extends WorkflowDetailsProxy {

    public CustomWorkflowDetailsProxy(){
        super();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public CustomWorkflowDetailsProxy setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public CustomWorkflowDetailsProxy setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public CustomWorkflowDetailsProxy setColor(Color color) {
        this.color.color  = color.color;
        this.color.name = color.name;
        return this;
    }
}
