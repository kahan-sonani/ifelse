package com.tnj.if_else.architecture.concreteEntities.triggerClasses;

import com.tnj.if_else.architecture.baseLevelEntities.Trigger;
import com.tnj.if_else.utils.constants.TriggerCategories;
import com.tnj.if_else.utils.constants.triggers;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.antonious.materialdaypicker.MaterialDayPicker;

import static com.tnj.if_else.architecture.concreteEntities.triggerClasses.DayTimeTrigger.Settings.END_TIME;
import static com.tnj.if_else.architecture.concreteEntities.triggerClasses.DayTimeTrigger.Settings.START_TIME;

public class DayTimeTrigger extends Trigger {

    public static final String NAME = triggers.categoryDateTime.DayTimeTrigger;
    public static final String CATEGORY = TriggerCategories.categoryDateTime;
    public static final MaterialDrawableBuilder.IconValue ICON = MaterialDrawableBuilder.IconValue.CALENDAR_CLOCK;
    public static final String DESCRIPTION = "This trigger can be triggered on any selected time of the day and selected day of the week.";

    public static class Settings {
        public static final String DAYS = "days";
        public static final String START_TIME = "startTime";
        public static final String END_TIME = "endTime";
    }
    public DayTimeTrigger() {
        super();
        triggerDetails.icon = ICON;
        triggerDetails.className = getClass().getCanonicalName();
        triggerDetails.description = DESCRIPTION;
        triggerDetails.name = NAME;
        triggerDetails.category = CATEGORY;
    }
    public static int convertWeekdayToCalendarDay(MaterialDayPicker.Weekday day) {
        switch (day) {
            case FRIDAY:
                return Calendar.FRIDAY;
            case MONDAY:
                return Calendar.MONDAY;
            case SUNDAY:
                return Calendar.SUNDAY;
            case TUESDAY:
                return Calendar.TUESDAY;
            case SATURDAY:
                return Calendar.SATURDAY;
            case THURSDAY:
                return Calendar.THURSDAY;
            case WEDNESDAY:
                return Calendar.WEDNESDAY;
            default:
                return -1;
        }
    }

    public static MaterialDayPicker.Weekday convertCalendarDayToWeekDay(int day) {
        switch (day) {
            case Calendar.FRIDAY:
                return MaterialDayPicker.Weekday.FRIDAY;
            case Calendar.MONDAY:
                return MaterialDayPicker.Weekday.MONDAY;
            case Calendar.SUNDAY:
                return MaterialDayPicker.Weekday.SUNDAY;
            case Calendar.TUESDAY:
                return MaterialDayPicker.Weekday.TUESDAY;
            case Calendar.SATURDAY:
                return MaterialDayPicker.Weekday.SATURDAY;
            case Calendar.THURSDAY:
                return MaterialDayPicker.Weekday.THURSDAY;
            case Calendar.WEDNESDAY:
                return MaterialDayPicker.Weekday.WEDNESDAY;
            default:
                return null;
        }
    }

    public static ArrayList<Integer> convertWeekDaysIntoCalendarDays(ArrayList<MaterialDayPicker.Weekday> days) {
        ArrayList<Integer> d = new ArrayList<>();
        for (MaterialDayPicker.Weekday day : days)
            d.add(convertWeekdayToCalendarDay(day));
        return d;
    }
    public static ArrayList<MaterialDayPicker.Weekday> convertCalendarDaysIntoWeekDays(ArrayList<Number> days) {
        ArrayList<MaterialDayPicker.Weekday> d = new ArrayList<>();
        for (Number day : days)
            d.add(convertCalendarDayToWeekDay(day.intValue()));
        return d;
    }

    public static class DayTimeBuilder {

        private DayTimeTrigger dayTime;

        public DayTimeBuilder() {
            dayTime = new DayTimeTrigger();
        }

        public DayTimeBuilder addDays(List<MaterialDayPicker.Weekday> days) {
            ArrayList<Integer> daysOfWeek = new ArrayList<>();
            for (MaterialDayPicker.Weekday day : days) {
                int d = convertWeekdayToCalendarDay(day);
                if (!daysOfWeek.contains(d))
                    daysOfWeek.add(d);
            }
            dayTime.configuration.put(Settings.DAYS, daysOfWeek);
            return this;
        }

        public DayTimeBuilder setTimeRange(String startTime, String endTime) {
            if (startTime != null && endTime != null) {
                dayTime.configuration.put(START_TIME, startTime);
                dayTime.configuration.put(END_TIME, endTime);
            }
            return this;
        }

        public DayTimeTrigger build() {
            return dayTime;
        }
    }
}

