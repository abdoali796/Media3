package com.abdoali.datasourece.api

import org.json.JSONObject

fun parserMp3(jsonObject: JSONObject): Mp3quran {
    val mp3quranList = mutableListOf<Mp3quran>()
    val recitersListJsonArray = jsonObject.getJSONArray("reciters")
    val reciter = mutableListOf<Reciter>()
    for (reciterData in 0 until recitersListJsonArray.length()) {

        val reciterJSONObject = recitersListJsonArray.getJSONObject(reciterData)
        val name = reciterJSONObject.getString("name")
        val id = reciterJSONObject.getInt("id")
        val letter = reciterJSONObject.getString("letter")
        val moshaf = reciterJSONObject.getJSONArray("moshaf")
        val moshafList = mutableListOf<Moshaf>()
        for (mo in 0 until moshaf.length()) {
            val moshafJSONObject = moshaf.getJSONObject(mo)
            val id = moshafJSONObject.getInt("id")
            val name = moshafJSONObject.getString("name")
            val server = moshafJSONObject.getString("server")
            val surah_total = moshafJSONObject.getInt("surah_total")
            val moshaf_type = moshafJSONObject.getInt("moshaf_type")
            val surah_list = moshafJSONObject.getString("surah_list")
            moshafList += Moshaf(id , moshaf_type , name , server , surah_list , surah_total)
        }
        reciter += Reciter(id = id , letter = letter , moshaf = moshafList , name = name)
    }

    return Mp3quran(reciter)
}