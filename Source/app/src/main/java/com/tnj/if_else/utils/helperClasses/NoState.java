package com.tnj.if_else.utils.helperClasses;

import android.content.Context;

import com.tnj.if_else.utils.interfaces.State;

public class NoState implements State {

    private String noStateString = "No State";

    @Override
    public String getMessage(Context context) {
        return noStateString;
    }

    @Override
    public String getMessage(Context context, Object... formatArgs) {
        return noStateString;
    }
}
