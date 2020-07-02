package com.tnj.if_else.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.tnj.if_else.IFELSEApplication;
import com.tnj.if_else.R;
import com.tnj.if_else.activities_and_fragments.activities.SettingsActivity;
import com.tnj.if_else.architecture.secondLevelEntities.BuiltInWorkflow;
import com.tnj.if_else.architecture.secondLevelEntities.CustomWorkflow;

import java.util.ArrayList;

public class WorkflowManager extends Service {

    private ArrayList<BuiltInWorkflow> biWorkflow;
    private ArrayList<CustomWorkflow> csWorkflow;

    public WorkflowManager() {
        Log.i("ifelse","constructor service");
        biWorkflow = new ArrayList<>();
        csWorkflow = new ArrayList<>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ifelse","onStartCommand() service");
        Intent notificationIntent = new Intent(this, SettingsActivity.class);
        TaskStackBuilder builder = TaskStackBuilder.create(this);
        builder.addNextIntentWithParentStack(notificationIntent);
        PendingIntent pendingIntent = builder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this, IFELSEApplication.NOTIFICATION_CHANNEL_ID)
                .setContentTitle("IFELSE service : Running")
                .setSmallIcon(R.drawable.ic_notification_app_icon)
                .setContentIntent(pendingIntent)
                .setTicker("IFELSE")
                .setContentText("Click on the notification to open settings and enable/disable the service")
                .build();

        startForeground(1,notification);
        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("ifelse","service onDestroyed");
    }
}
