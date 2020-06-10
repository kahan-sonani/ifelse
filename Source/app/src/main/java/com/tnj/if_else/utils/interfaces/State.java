package com.tnj.if_else.utils.interfaces;

import android.content.Context;

public interface State {

    Boolean isSuccessful();

    String getMessage(Context context);

    String getMessage(Context context , Object... formatArgs);
}
