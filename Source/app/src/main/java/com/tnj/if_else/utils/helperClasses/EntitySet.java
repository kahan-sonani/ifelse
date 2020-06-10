package com.tnj.if_else.utils.helperClasses;

import android.os.Parcel;
import android.os.Parcelable;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconUtils;

public class EntitySet implements Parcelable {

    public static final String NAME = "NAME";
    public static final String CATEGORY = "CATEGORY";
    public static final String ICON = "ICON";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final Creator<EntitySet> CREATOR = new Creator<EntitySet>() {
        @Override
        public EntitySet createFromParcel(Parcel in) {
            return new EntitySet(in);
        }

        @Override
        public EntitySet[] newArray(int size) {
            return new EntitySet[size];
        }
    };
    public String name;
    public String description;
    public MaterialDrawableBuilder.IconValue icon;
    public String className;
    public String category;

    public EntitySet() {
        name = "None";
        description = "None";
        icon = MaterialDrawableBuilder.IconValue.MINUS;
        className = getClass().getCanonicalName();
        category = "None";
    }

    public EntitySet(String name, MaterialDrawableBuilder.IconValue icon, String className, String category, String description) {
        this.className = className;
        this.icon = icon;
        this.name = name;
        this.category = category;
        this.description = description;
    }

    public EntitySet(EntitySet set) {
        name = set.name;
        icon = set.icon;
        className = set.className;
        category = set.category;
        description = set.description;
    }

    protected EntitySet(Parcel in) {
        name = in.readString();
        className = in.readString();
        icon = MaterialDrawableBuilder.IconValue.valueOf(in.readString());
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(className);
        parcel.writeString(MaterialIconUtils.getIconString(icon.ordinal()));
        parcel.writeString(description);
    }
}
