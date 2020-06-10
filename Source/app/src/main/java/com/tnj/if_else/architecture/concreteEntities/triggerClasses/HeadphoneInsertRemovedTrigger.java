package com.tnj.if_else.architecture.concreteEntities.triggerClasses;

import com.tnj.if_else.architecture.baseLevelEntities.Trigger;
import com.tnj.if_else.utils.constants.TriggerCategories;
import com.tnj.if_else.utils.constants.triggers;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

public class HeadphoneInsertRemovedTrigger extends Trigger {

    public static final String NAME = triggers.categoryConnectivity.HeadphoneInsertRemovedTrigger;
    public static final String CATEGORY = TriggerCategories.categoryConnectivity;
    public static final MaterialDrawableBuilder.IconValue ICON = MaterialDrawableBuilder.IconValue.HEADPHONES;
    public static final String DESCRIPTION = "This trigger is fired when headphone plug is inserted into 3.5mm audio jack.";

    public HeadphoneInsertRemovedTrigger(){
        super();
        triggerDetails.category = CATEGORY;
        triggerDetails.icon = ICON;
        triggerDetails.description = DESCRIPTION;
        triggerDetails.name = NAME;
        triggerDetails.className = getClass().getCanonicalName();
    }
}
