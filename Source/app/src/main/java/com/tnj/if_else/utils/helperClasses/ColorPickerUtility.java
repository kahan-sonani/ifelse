package com.tnj.if_else.utils.UI;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.tnj.if_else.R;

import petrov.kristiyan.colorpicker.ColorPicker;

public class ColorPickerUtility {

    public static ColorPicker setDefaultColors(Context c, ColorPicker picker) {
        picker.setColors(
                ContextCompat.getColor(c, R.color.workflow_color1),
                ContextCompat.getColor(c, R.color.workflow_color2),
                ContextCompat.getColor(c, R.color.workflow_color3),
                ContextCompat.getColor(c, R.color.workflow_color4),
                ContextCompat.getColor(c, R.color.workflow_color5),
                ContextCompat.getColor(c, R.color.workflow_color6),
                ContextCompat.getColor(c, R.color.workflow_color7),
                ContextCompat.getColor(c, R.color.workflow_color8),
                ContextCompat.getColor(c, R.color.workflow_color9),
                ContextCompat.getColor(c, R.color.workflow_color10));
        return picker;
    }
}
