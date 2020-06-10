package com.tnj.if_else.architecture.concreteEntities.triggerClasses;

import com.tnj.if_else.architecture.baseLevelEntities.Trigger;
import com.tnj.if_else.utils.constants.TriggerCategories;
import com.tnj.if_else.utils.constants.triggers;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.Date;

import static com.tnj.if_else.architecture.concreteEntities.triggerClasses.CalendarEventTrigger.Settings.CALENDAR_TIME;

public class CalendarEventTrigger extends Trigger {

    public static final String NAME = triggers.categoryDateTime.CalendarEventTrigger;
    public static final String CATEGORY = TriggerCategories.categoryDateTime;
    public static final MaterialDrawableBuilder.IconValue ICON = MaterialDrawableBuilder.IconValue.CALENDAR_CHECK;
    public static final String DESCRIPTION = "This trigger will be fired on the date and time specified by user.";

    public static class Settings {
        public static final String CALENDAR_TIME = "calendar time";
    }

    public CalendarEventTrigger() {
        super();
        triggerDetails.name = NAME;
        triggerDetails.description = DESCRIPTION;
        triggerDetails.className = getClass().getCanonicalName();
        triggerDetails.icon = ICON;
        triggerDetails.category = CATEGORY;
    }
    public static class CalendarEventBuilder {

        private CalendarEventTrigger event;

        public CalendarEventBuilder setEventDate(Date calendarTime) {
            event.configuration.put(CALENDAR_TIME, calendarTime);
            return this;
        }

        public CalendarEventTrigger build() {
            return event;
        }
    }
}
