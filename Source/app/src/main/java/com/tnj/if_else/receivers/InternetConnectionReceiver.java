package com.tnj.if_else.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.lifecycle.MutableLiveData;

public class InternetConnectionReceiver extends BroadcastReceiver {

    public static MutableLiveData<Boolean> isNetworkAvailable;

    public InternetConnectionReceiver() {
        isNetworkAvailable = new MutableLiveData<>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (isOnline(context)) {
            isNetworkAvailable.postValue(true);
        } else {
            isNetworkAvailable.postValue(false);
        }
    }

    private boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null;
    }
}
