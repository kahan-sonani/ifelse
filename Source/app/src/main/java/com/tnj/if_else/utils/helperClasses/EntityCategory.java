package com.tnj.if_else.utils.helperClasses;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.List;

public class EntityCategory extends ExpandableGroup<EntitySet> {

    private MaterialDrawableBuilder.IconValue categoryIcon;

    public EntityCategory(String title, MaterialDrawableBuilder.IconValue icon, List<EntitySet> items) {
        super(title, items);
        categoryIcon = icon;
    }

    public MaterialDrawableBuilder.IconValue getCategoryIcon() {
        return categoryIcon;
    }
}
