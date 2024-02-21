package com.abdoali.datasourece.read

import org.json.JSONArray

fun parserTiming(ayaTimingArrayJSONObject: JSONArray): AyaTiming {
    val ayaTiming = AyaTiming()
    for (i in 0 until ayaTimingArrayJSONObject.length()) {
        val item = ayaTimingArrayJSONObject.getJSONObject(i)
        val ayah = item.getInt("ayah")
        val start_time = item.getLong("start_time")
        val end_time = item.getLong("end_time")
        ayaTiming.add(AyaTimingItem(ayah, start_time, end_time))
    }
    return ayaTiming
}