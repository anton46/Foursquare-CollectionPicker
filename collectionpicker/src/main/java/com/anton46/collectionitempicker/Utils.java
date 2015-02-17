package com.anton46.collectionitempicker;

import android.content.Context;

public class Utils {

    public static int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

}
