package com.tnj.if_else.utils.lookup;

import com.tnj.if_else.utils.constants.ActionCategories;
import com.tnj.if_else.utils.constants.CriteriaCategories;
import com.tnj.if_else.utils.constants.TriggerCategories;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder.IconValue;

public class IconProvider {

    public static IconValue getActionCategoryIcon(String category) {
        switch (category) {
            case ActionCategories.categoryApplications:
                return IconValue.APPLICATION;
            case ActionCategories.categoryIntegration:
                return IconValue.LINK_VARIANT;
            case ActionCategories.categoryMedia:
                return IconValue.VOLUME_HIGH;
            case ActionCategories.categoryNotification:
                return IconValue.MESSAGE_PROCESSING;
            default:
                return IconValue.MINUS;
        }
    }

    public static IconValue getTriggerCategoryIcon(String category) {
        switch (category) {
            case TriggerCategories.categoryConnectivity:
                return IconValue.ROUTER_WIRELESS;
            case TriggerCategories.categoryDateTime:
                return IconValue.CALENDAR;
            default:
                return IconValue.MINUS;
        }
    }

    public static IconValue getCriteriaCategoryIcon(String category) {
        switch (category) {
            case CriteriaCategories.categoryBattery:
                return IconValue.BATTERY;
            default:
                return IconValue.MINUS;
        }
    }

}
