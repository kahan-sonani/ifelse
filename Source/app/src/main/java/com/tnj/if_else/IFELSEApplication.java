package com.tnj.if_else;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class IFELSEApplication extends Application {

    public static final String IFELSE_ENABLED = "ifelse_foreground_service";
    public static final String NOTIFICATION_CHANNEL_ID = "IFELSEServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "IFELSE Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            ));
        }
    }
}
