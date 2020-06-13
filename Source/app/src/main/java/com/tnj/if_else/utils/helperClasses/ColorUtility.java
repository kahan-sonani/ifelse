package com.tnj.if_else.utils.helperClasses;

import com.tnj.if_else.R;

import java.util.Random;

public class ColorUtility {

    public static final String DARK_GREEN = "DARK GREEN";
    public static final String LITE_RED = "LITE RED";
    public static final String LITE_GREEN = "LITE GREEN";
    public static final String BLACK = "BLACK";
    public static final String MODERATE_BLUE = "MODERATE BLUE";
    public static final String SPACE_BLUE = "SPACE BLUE";
    public static final String MAROON = "MAROON";
    public static final String OCEAN_BLUE = "OCEAN BLUE";
    public static final String DEEP_BLUE = "DEEP BLUE";
    public static final String ORANGE = "ORANGE";
    public static final String GRAY = "GRAY";


    public static Color getRandomColor() {
        return getColorByIndex(new Random().nextInt(10));
    }
    public static Color getColorByIndex(int index) {
        switch (index % 10) {
            case 0:
                return new Color(R.color.workflow_color1, MAROON);
            case 1:
                return new Color(R.color.workflow_color2, LITE_RED);
            case 2:
                return new Color(R.color.workflow_color3, LITE_GREEN);
            case 3:
                return new Color(R.color.workflow_color4, BLACK);
            case 4:
                return new Color(R.color.workflow_color5, MODERATE_BLUE);
            case 5:
                return new Color(R.color.workflow_color6, SPACE_BLUE);
            case 6:
                return new Color(R.color.workflow_color7, DARK_GREEN);
            case 7:
                return new Color(R.color.workflow_color8, ORANGE);
            case 8:
                return new Color(R.color.workflow_color9, DEEP_BLUE);
            case 9:
                return new Color(R.color.workflow_color10, OCEAN_BLUE);
        }
        return new Color(R.color.workflow_colorDefault, GRAY);
    }

    public static int getTheme(String color) {
        switch (color) {
            case MAROON:
                return R.style.workflow_color_1_theme;
            case LITE_RED:
                return R.style.workflow_color_2_theme;
            case LITE_GREEN:
                return R.style.workflow_color_3_theme;
            case BLACK:
                return R.style.workflow_color_4_theme;
            case MODERATE_BLUE:
                return R.style.workflow_color_5_theme;
            case SPACE_BLUE:
                return R.style.workflow_color_6_theme;
            case DARK_GREEN:
                return R.style.workflow_color_7_theme;
            case ORANGE:
                return R.style.workflow_color_8_theme;
            case DEEP_BLUE:
                return R.style.workflow_color_9_theme;
            case OCEAN_BLUE:
                return R.style.workflow_color_10_theme;
        }
        return R.style.workflow_color_11_theme;
    }
}
