package com.tnj.if_else.architecture.concreteEntities.actionClasses;

import com.tnj.if_else.architecture.baseLevelEntities.Action;
import com.tnj.if_else.utils.constants.ActionCategories;
import com.tnj.if_else.utils.constants.actions;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

public class SendSMSAction extends Action {

    public static final String NAME = actions.categoryNotification.SendNotificationAction;
    public static final String CATEGORY = ActionCategories.categoryNotification;
    public static final MaterialDrawableBuilder.IconValue ICON = MaterialDrawableBuilder.IconValue.MESSAGE_DRAW;
    public static final String DESCRIPTION = "This action sends a custom message to the specified Mobile number.";


    public SendSMSAction() {
        super();
        actionDetails.icon = ICON;
        actionDetails.className = getClass().getCanonicalName();
        actionDetails.description = DESCRIPTION;
        actionDetails.name = NAME;
        actionDetails.category = CATEGORY;
    }
}
