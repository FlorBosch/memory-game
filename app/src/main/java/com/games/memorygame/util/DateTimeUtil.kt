package com.games.memorygame.util


import android.content.Context

import com.games.memorygame.R

import java.util.concurrent.TimeUnit

object DateTimeUtil {

    fun fromMilliSecondsToString(context: Context, millis: Long): String {
        return context.getString(R.string.time_format,
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))
    }
}
