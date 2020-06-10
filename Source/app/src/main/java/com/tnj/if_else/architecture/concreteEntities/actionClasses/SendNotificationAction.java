package com.tnj.if_else.architecture.concreteEntities.actionClasses;

import android.content.Context;

import com.tnj.if_else.architecture.baseLevelEntities.Action;
import com.tnj.if_else.utils.constants.ActionCategories;
import com.tnj.if_else.utils.constants.actions;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder.IconValue;

public class SendNotificationAction extends Action {

    public static final String NAME = actions.categoryNotification.SendNotificationAction;
    public static final String CATEGORY = ActionCategories.categoryNotification;
    public static final IconValue ICON = IconValue.MESSAGE_TEXT;
    public static final String DESCRIPTION = "This action sends you notification on status bar of your mobile device, Title, Description is configurable.";

    public static final class Settings {
        public static final String NOTIFICATION_TITLE = "Notification title";
        public static final String NOTIFICATION_DESCRIPTION = "Notification Description";
    }

    public SendNotificationAction() {
        super();
        actionDetails.icon = ICON;
        actionDetails.className = getClass().getCanonicalName();
        actionDetails.description = DESCRIPTION;
        actionDetails.name = NAME;
        actionDetails.category = CATEGORY;
    }

    @Override
    public void performAction(Context context) {

    }

    public static class NotificationBuilder {

        SendNotificationAction notificationAction;

        public NotificationBuilder setTitle(String title) {
            notificationAction.configuration.put(Settings.NOTIFICATION_TITLE, title);
            return this;
        }

        public NotificationBuilder setDescription(String desc) {
            notificationAction.configuration.put(Settings.NOTIFICATION_DESCRIPTION, desc);
            return this;
        }

        public SendNotificationAction build() {
            return notificationAction;
        }
    }
}
