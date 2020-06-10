package com.tnj.if_else.architecture.concreteEntities.actionClasses;

import com.tnj.if_else.architecture.baseLevelEntities.Action;
import com.tnj.if_else.utils.constants.ActionCategories;
import com.tnj.if_else.utils.constants.actions;
import com.tnj.if_else.utils.interfaces.IAction;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

public class OpenApplicationAction extends Action implements IAction {

    public static final String NAME = actions.categoryApplications.OpenApplicationAction;
    public static final String CATEGORY = ActionCategories.categoryApplications;
    public static final MaterialDrawableBuilder.IconValue ICON = MaterialDrawableBuilder.IconValue.APPLICATION;
    public static final String DESCRIPTION = "This action launched an application or a sequence of application when executed which are installed in the device.";

    public OpenApplicationAction(){
        super();
        actionDetails.category = CATEGORY;
        actionDetails.icon = ICON;
        actionDetails.description = DESCRIPTION;
        actionDetails.name = NAME;
        actionDetails.className = getClass().getCanonicalName();
    }
}
