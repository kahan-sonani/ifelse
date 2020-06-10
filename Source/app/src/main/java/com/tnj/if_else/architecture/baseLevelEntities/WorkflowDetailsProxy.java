package com.tnj.if_else.architecture.baseLevelEntities;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.tnj.if_else.utils.helperClasses.Color;
import com.tnj.if_else.utils.lookup.ColorUtility;

@IgnoreExtraProperties
public abstract class WorkflowDetailsProxy {

    protected String name;
    protected String description;
    protected Color color;

    public WorkflowDetailsProxy(){
        color = ColorUtility.getRandomColor();
    }
    public abstract String getName();
    public abstract String getDescription();
    public abstract Color getColor();


    public abstract WorkflowDetailsProxy setName(String name);
    public abstract WorkflowDetailsProxy setDescription(String description);
    public abstract WorkflowDetailsProxy setColor(Color color);
}
