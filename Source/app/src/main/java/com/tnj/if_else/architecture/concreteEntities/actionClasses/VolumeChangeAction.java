package com.tnj.if_else.architecture.concreteEntities.actionClasses;

import android.content.Context;
import android.media.AudioManager;

import com.tnj.if_else.architecture.baseLevelEntities.Action;
import com.tnj.if_else.utils.constants.ActionCategories;
import com.tnj.if_else.utils.constants.actions;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

public class VolumeChangeAction extends Action {

    public static final String NAME = actions.categoryMedia.VolumeChangeAction;
    public static final String CATEGORY = ActionCategories.categoryMedia;
    public static final MaterialDrawableBuilder.IconValue ICON = MaterialDrawableBuilder.IconValue.VOLUME_HIGH;
    public static final String DESCRIPTION = "Makes changes to Audio(ringer, call, notification, alarm, music) stream of your mobile device.";

    public VolumeChangeAction addVolumeProfile(String stream, Integer percentage) {
        addConfiguration(stream, percentage);
        return this;
    }

    public VolumeChangeAction() {
        super();
        actionDetails.name = NAME;
        actionDetails.description = DESCRIPTION;
        actionDetails.className = this.getClass().getCanonicalName();
        actionDetails.icon = ICON;
        actionDetails.category = CATEGORY;
    }

    @Override
    public void performAction(Context context) {

        AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        for (String key : configuration.keySet()) {
            int stream = getMappedStream(key);
            manager.setStreamVolume(stream,
                    getRelativeVolume(getConfiguration(key, Integer.class), manager, stream),
                    AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        }
    }

    private int getMappedStream(String stream) {
        switch (stream) {
            case Settings.NOTIFICATION_VALUE:
                return AudioManager.STREAM_NOTIFICATION;
            case Settings.VOICE_CALL_VALUE:
                return AudioManager.STREAM_SYSTEM;
            case Settings.RINGER_VALUE:
                return AudioManager.STREAM_RING;
            case Settings.MEDIA_VALUE:
                return AudioManager.STREAM_MUSIC;
            case Settings.ALARM_VALUE:
                return AudioManager.STREAM_ALARM;
        }
        return -1;
    }

    private int getRelativeVolume(int percentage, AudioManager manager, int stream) {

        int maxVolume = manager.getStreamMaxVolume(stream);
        return Math.round((float) (maxVolume * (percentage * 0.010)));
    }

    public static final class Settings {
        public static final String NOTIFICATION_VALUE = "Notification stream";
        public static final String ALARM_VALUE = "Alarm stream";
        public static final String RINGER_VALUE = "Ringer stream";
        public static final String VOICE_CALL_VALUE = "Voice call stream";
        public static final String MEDIA_VALUE = "Media stream";
    }
}
