package com.tnj.if_else.utils.helperClasses;

import com.tnj.if_else.architecture.concreteEntities.actionClasses.VolumeChangeAction;
import com.tnj.if_else.architecture.concreteEntities.triggerClasses.DayTimeTrigger;
import com.tnj.if_else.utils.constants.ActionCategories;
import com.tnj.if_else.utils.constants.CriteriaCategories;
import com.tnj.if_else.utils.constants.TriggerCategories;
import com.tnj.if_else.utils.constants.actions;
import com.tnj.if_else.utils.constants.criterias;
import com.tnj.if_else.utils.constants.triggers;
import com.tnj.if_else.utils.lookup.IconProvider;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ClassInventory {

    private static String getActionPackage() {
        return VolumeChangeAction.class.getPackage().getName() + ".";
    }

    private static String getCriteriaPackage() {
        return "";
    }

    private static String getTriggerPackage() {
        return DayTimeTrigger.class.getPackage().getName() + ".";
    }

    public static ArrayList<EntitySet> getActions() {
        ArrayList<EntitySet> sets = new ArrayList<>();
        for (Class c : actions.class.getClasses()) {
            for (Field f : c.getFields()) {
                sets.add(createActionEntitySet(f));
            }
        }
        return sets;
    }

    public static ArrayList<EntitySet> getTriggers() {
        ArrayList<EntitySet> sets = new ArrayList<>();
        for (Class c : triggers.class.getClasses()) {
            for (Field f : c.getFields()) {
                sets.add(createTriggerEntitySet(f));
            }
        }
        return sets;
    }

    public static ArrayList<EntitySet> getCriterias() {
        ArrayList<EntitySet> sets = new ArrayList<>();
        for (Class c : criterias.class.getClasses()) {
            for (Field f : c.getFields()) {
                sets.add(createCriteriaEntitySet(f));
            }
        }
        return sets;
    }

    private static EntitySet createActionEntitySet(Field field) {
        EntitySet set = new EntitySet();
        setNameAndClass(set, field);
        setActionIcon(set, field);
        return set;
    }

    private static EntitySet createTriggerEntitySet(Field field) {
        EntitySet set = new EntitySet();
        setNameAndClass(set, field);
        setTriggerIcon(set, field);
        return set;
    }

    private static EntitySet createCriteriaEntitySet(Field field) {
        EntitySet set = new EntitySet();
        setNameAndClass(set, field);
        setCriteriaIcon(set, field);
        return set;
    }

    private static void setNameAndClass(EntitySet set, Field field) {
        try {
            set.name = (String) field.get(field.getName());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private synchronized static void setActionIcon(EntitySet set, Field field) {
        try {
            String fullName;
            Class classEntity = Class.forName(fullName = getActionPackage() + field.getName());
            set.icon = (MaterialDrawableBuilder.IconValue) classEntity.getField(EntitySet.ICON).get(EntitySet.ICON);
            set.category = (String) classEntity.getField(EntitySet.CATEGORY).get(EntitySet.CATEGORY);
            set.description = (String) classEntity.getField(EntitySet.DESCRIPTION).get(EntitySet.DESCRIPTION);
            set.className = fullName;
        } catch (IllegalAccessException | NoSuchFieldException | ClassNotFoundException e) {
            set.icon = MaterialDrawableBuilder.IconValue.MINUS;
        }
    }

    private synchronized static void setTriggerIcon(EntitySet set, Field field) {
        try {
            String fullName;
            Class classType = Class.forName(fullName = getTriggerPackage() + field.getName());
            set.icon = (MaterialDrawableBuilder.IconValue) classType.getField(EntitySet.ICON).get(EntitySet.ICON);
            set.category = (String) classType.getField(EntitySet.CATEGORY).get(EntitySet.CATEGORY);
            set.description = (String) classType.getField(EntitySet.DESCRIPTION).get(EntitySet.DESCRIPTION);
            set.className = fullName;
        } catch (IllegalAccessException | NoSuchFieldException | ClassNotFoundException e) {
            set.icon = MaterialDrawableBuilder.IconValue.MINUS;
        }
    }

    private synchronized static void setCriteriaIcon(EntitySet set, Field field) {
        try {
            String fullName;
            Class classType = Class.forName(fullName = getCriteriaPackage() + field.getName());
            set.icon = (MaterialDrawableBuilder.IconValue) classType.getField(EntitySet.ICON).get(EntitySet.ICON);
            set.category = (String) classType.getField(EntitySet.CATEGORY).get(EntitySet.CATEGORY);
            set.description = (String) classType.getField(EntitySet.DESCRIPTION).get(EntitySet.DESCRIPTION);
            set.className = fullName;
        } catch (IllegalAccessException | NoSuchFieldException | ClassNotFoundException e) {
            set.icon = MaterialDrawableBuilder.IconValue.MINUS;
        }
    }

    public static ArrayList<EntityCategory> getActionsByCategory() {
        ArrayList<EntityCategory> categories = new ArrayList<>();
        for (Class c : actions.class.getClasses()) {
            ArrayList<EntitySet> sets = new ArrayList<>();
            for (Field f : c.getFields()) {
                sets.add(createActionEntitySet(f));
            }
            try {
                String title = (String) ActionCategories.class.getField(c.getSimpleName()).get(c.getSimpleName());
                categories.add(new EntityCategory(title, getActionIcon(title), sets));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return categories;
    }

    public static ArrayList<EntityCategory> getTriggersByCategory() {
        ArrayList<EntityCategory> categories = new ArrayList<>();
        for (Class c : triggers.class.getClasses()) {
            ArrayList<EntitySet> sets = new ArrayList<>();
            for (Field f : c.getFields()) {
                sets.add(createTriggerEntitySet(f));
            }
            try {
                String title = (String) TriggerCategories.class.getField(c.getSimpleName()).get(c.getSimpleName());
                categories.add(new EntityCategory(title, getTriggerIcon(title), sets));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return categories;
    }

    public static ArrayList<EntityCategory> getCriteriasByCategory() {
        ArrayList<EntityCategory> categories = new ArrayList<>();
        for (Class c : criterias.class.getClasses()) {
            ArrayList<EntitySet> sets = new ArrayList<>();
            for (Field f : c.getFields()) {
                sets.add(createCriteriaEntitySet(f));
            }
            try {
                String title = (String) CriteriaCategories.class.getField(c.getSimpleName()).get(c.getSimpleName());
                categories.add(new EntityCategory(title, getCriteriaIcon(title), sets));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return categories;
    }

    private static MaterialDrawableBuilder.IconValue getTriggerIcon(String categoryName) {
        return IconProvider.getTriggerCategoryIcon(categoryName);
    }

    private static MaterialDrawableBuilder.IconValue getActionIcon(String categoryName) {
        return IconProvider.getActionCategoryIcon(categoryName);
    }

    private static MaterialDrawableBuilder.IconValue getCriteriaIcon(String categoryName) {
        return IconProvider.getCriteriaCategoryIcon(categoryName);
    }
}
