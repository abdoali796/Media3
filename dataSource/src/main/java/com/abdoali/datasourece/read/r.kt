package com.abdoali.datasourece.read

import android.content.Context
import android.util.Log
import com.abdoali.datasourece.api.ApiService
import com.abdoali.datasourece.surahIndex
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class QuranWords @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiQuran: ApiService,
) {

   suspend fun  getWordsAndTiming(suwarString: String ,readId: Int):ReadAndTiming{
        val words=getWord(suwarString ,context)
        val timing=getTiming(suwarString, readId)
       return ReadAndTiming(words ,timing)
    }



   private suspend fun getTiming(suwarString: String, readId: Int): AyaTiming? {
        val surahIndex = surahIndex(suwarString)

        return try {
            parserTiming( JSONArray(apiQuran.getAyaTiming(surahIndex, readId)))
        } catch (e: Exception) {
            Log.i("timingAya",e.message.toString())
            e.message.toString()
            return null
        }
    }
}
fun getWord(suwarInt: String, context: Context): Read {
    val surahIndex = surahIndex(suwarInt)
    val x = context.assets.open("$surahIndex.json").bufferedReader().use { it.readText() }
    return parserWords(JSONObject(x))
}