package com.abdoali.datasourece

import android.util.Log
import com.abdoali.datasourece.api.ApiService
import com.abdoali.datasourece.api.Mp3quran
import com.abdoali.datasourece.api.parserReciterMp3
import org.json.JSONObject
import javax.inject.Inject

fun online() = listOf(
    "https://cdn.islamic.network/quran/audio-surah/128/ar.alafasy/114.mp3" ,
    "https://cdn.islamic.network/quran/audio-surah/128/ar.alafasy/113.mp3" ,
    "https://cdn.islamic.network/quran/audio-surah/128/ar.alafasy/112.mp3" ,
    "https://cdn.islamic.network/quran/audio-surah/128/ar.alafasy/111.mp3" ,
    "https://cdn.islamic.network/quran/audio-surah/128/ar.alafasy/110.mp3" ,
    "https://cdn.islamic.network/quran/audio-surah/128/ar.alafasy/109.mp3" ,
    "https://cdn.islamic.network/quran/audio-surah/128/ar.alafasy/100.mp3" ,
)

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

            val jsonObject = JSONObject(apiQuran.getListReciters())
            parserReciterMp3(jsonObject)
        } catch (e: Exception) {
            Log.i("Exception" , e.message.toString())
      Mp3quran(emptyList())
        }


    }

//    suspend fun getFovMp3quran(): List<Quran> {
//        val list = getAllMp3quran()
//        var index = 0
//        val listFov = mutableListOf<Quran>()
//        for (read in list.indices) {
//            if (list[read].artists == "مشاري العفاسي" ||
//                list[read].artists == "ماهر المعيقلي" ||
//                list[read].artists == "عبدالباسط عبدالصمد" ||
//                list[read].artists == "سعد الغامدي" ||
//                list[read].artists == "إسلام صبحي" ||
//                list[read].artists == "محمود علي البنا"
//            ) {
//                listFov += Quran(
//
//                    artists = list[read].artists ,
//                    surah = list[read].surah ,
//                    uri = list[read].uri
//                )
//                index ++
//            }
//        }
//        return listFov
//    }

}


