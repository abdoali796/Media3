package com.abdoali.datasourece.api

import android.util.Log
import org.json.JSONObject
import java.util.Locale
import javax.inject.Inject


class ApiQuran @Inject constructor(private val apiQuran: ApiService) {

//    suspend fun getNewMp3quran(): List<Quran> {
//        return try {
//
//            val jsonObject = JSONObject(apiQuran.getRecentRead())
//            parserReadsMp3(jsonObject).asQuranList()
//        } catch (e: Exception) {
//            Log.i("Exception" , e.message.toString())
//            emptyList<Quran>()
//        }
//
//
//    }

    suspend fun getAllMp3quran(): Mp3quran {
        return try {

            val language = Locale.getDefault().isO3Language
            val jsonObject = JSONObject(apiQuran.getListReciters(language))
            return parserReciterMp3(jsonObject)
        } catch (e: Exception) {
            Log.i("Exception", e.message.toString())
            Mp3quran(emptyList())
        }


    }

//    suspend fun getFovMp3quran(): List<Quran> {
//        val list = getAllMp3quran()
//        var index = 0
//        val listFov = mutableListOf<Quran>()
//        for (Read in list.indices) {
//            if (list[Read].artists == "مشاري العفاسي" ||
//                list[Read].artists == "ماهر المعيقلي" ||
//                list[Read].artists == "عبدالباسط عبدالصمد" ||
//                list[Read].artists == "سعد الغامدي" ||
//                list[Read].artists == "إسلام صبحي" ||
//                list[Read].artists == "محمود علي البنا"
//            ) {
//                listFov += Quran(
//
//                    artists = list[Read].artists ,
//                    surah = list[Read].surah ,
//                    uri = list[Read].uri
//                )
//                index ++
//            }
//        }
//        return listFov
//    }

}


