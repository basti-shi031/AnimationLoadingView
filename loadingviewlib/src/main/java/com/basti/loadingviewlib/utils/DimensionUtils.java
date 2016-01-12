package com.basti.loadingviewlib.utils;

import android.content.Context;

/**
 * Created by SHIBW-PC on 2016/1/12.
 */
public class DimensionUtils {

    public static int dp2px(int dpValue,Context context) {
        return (int) context.getResources().getDisplayMetrics().density * dpValue;
    }

}
