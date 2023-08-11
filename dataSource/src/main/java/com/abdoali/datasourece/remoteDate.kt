package com.abdoali.datasourece

import android.util.Log
import com.abdoali.datasourece.api.ApiService
import com.abdoali.datasourece.api.Mp3quran
import com.abdoali.datasourece.api.parserMp3
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

fun online()= listOf(
    "https://cdn.islamic.network/quran/audio-surah/128/ar.alafasy/114.mp3",
    "https://cdn.islamic.network/quran/audio-surah/128/ar.alafasy/113.mp3",
    "https://cdn.islamic.network/quran/audio-surah/128/ar.alafasy/112.mp3",
    "https://cdn.islamic.network/quran/audio-surah/128/ar.alafasy/111.mp3",
    "https://cdn.islamic.network/quran/audio-surah/128/ar.alafasy/110.mp3",
    "https://cdn.islamic.network/quran/audio-surah/128/ar.alafasy/109.mp3",
    "https://cdn.islamic.network/quran/audio-surah/128/ar.alafasy/100.mp3",
)
class ApiQuran @Inject constructor(private val apiQuran: ApiService){

    suspend fun getMp3quran():Mp3quran{
        return try {
            val jsonObject =JSONObject(apiQuran.getListReciters())
            parserMp3(jsonObject)
        }catch (e:Exception){
            Log.i("Exception",e.message.toString())
            Mp3quran(emptyList())
        }


    }
}


