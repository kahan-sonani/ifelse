package com.tnj.if_else.utils.helperClasses;

import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.tnj.if_else.R;

public class Snacker {

    public static Snackbar show(View view, String message, int duration,int maxLines, boolean retryMode, onRetryCallback listenerForRetry){
        Snackbar s = Snackbar.make(view,message,duration)
            .setBackgroundTint(ContextCompat.getColor(view.getContext(),R.color.colorBlack));
        s.setBehavior(new BaseTransientBottomBar.Behavior());
        ((TextView) s.getView().findViewById(R.id.snackbar_text)).setMaxLines(maxLines);
        if(retryMode && listenerForRetry != null)
            s.setAction("RETRY", view1 -> listenerForRetry.onRetry())
                .setActionTextColor(ContextCompat.getColor(view.getContext(), R.color.colorYellow));
        s.show();
        return s;
    }

    public interface onRetryCallback{
        void onRetry();
    }
}
