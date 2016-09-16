package com.hunter.lib;

import android.content.Context;
import android.util.DisplayMetrics;

public class DotUtils {

    public static int dp2px(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * 默认红点水平间距
     */
    public static int getHorizontalPadding(Context context) {
        return dp2px(context, isDensityHigh(context) ? 5 : 3);
    }

    /**
     * 默认的红点宽高
     */
    public static int getCircleDp(Context context) {
        return dp2px(context, isDensityHigh(context) ? 15 : 10);
    }

    /**
     * 大于10的红点宽
     */
    public static int getRectWidthDp(Context context) {
        return dp2px(context, isDensityHigh(context) ? 20 : 10);
    }

    /**
     * 无红点的dp大小
     */
    public static int getNoneDp(Context context) {
        return dp2px(context, isDensityHigh(context) ? 7 : 6);
    }

    public static boolean isDensityHigh(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi > DisplayMetrics.DENSITY_HIGH;
    }
}