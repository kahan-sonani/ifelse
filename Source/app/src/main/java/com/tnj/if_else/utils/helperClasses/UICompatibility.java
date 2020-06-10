package com.tnj.if_else.utils.UI;

import android.app.Activity;
import android.os.Build;

import androidx.core.content.ContextCompat;

public class UICompatibility {

    public static void changeStatusBarColorAndVisibility(Activity context, int color, int visibility) {
        context.getWindow().setStatusBarColor(ContextCompat.getColor(context, color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            context.getWindow().getDecorView().setSystemUiVisibility(visibility);
    }
}
