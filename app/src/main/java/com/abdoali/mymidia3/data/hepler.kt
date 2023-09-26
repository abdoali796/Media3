package com.abdoali.mymidia3.data

import java.util.concurrent.TimeUnit

fun formatDuration(duration: Long): String {
    return if (duration < 3600000) {
        val minutes: Long =
            TimeUnit.MINUTES.convert(duration , TimeUnit.MILLISECONDS)
        val seconds: Long = (TimeUnit.SECONDS.convert(
            duration , TimeUnit.MILLISECONDS
        ) - minutes * TimeUnit.SECONDS.convert(1 , TimeUnit.MINUTES))
        String.format("%02d:%02d" , minutes , seconds)
    } else {
        val hour: Long = TimeUnit.HOURS.convert(duration , TimeUnit.MILLISECONDS)
        val minutes: Long = TimeUnit.MINUTES.convert(
            duration , TimeUnit.MILLISECONDS
        ) - hour * TimeUnit.MINUTES.convert(60 , TimeUnit.MINUTES)
        val seconds: Long = (TimeUnit.SECONDS.convert(
            duration , TimeUnit.MILLISECONDS
        ) - minutes * TimeUnit.SECONDS.convert(
            1 , TimeUnit.MINUTES
        )) - hour * TimeUnit.SECONDS.convert(1 , TimeUnit.HOURS)
        String.format("%02d:%02d:%02d" , hour , minutes , seconds)
    }

}