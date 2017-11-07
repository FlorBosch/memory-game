package com.themobilecompany.memorygame.util;


import android.content.Context;

import com.themobilecompany.memorygame.R;

import java.util.concurrent.TimeUnit;

public class DateTimeUtil {

    public static String fromMilliSecondsToString(Context context, long millis) {
        return context.getString(R.string.time_format,
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }
}
