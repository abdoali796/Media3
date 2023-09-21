package com.abdoali.datasourece

import android.util.Log
import com.abdoali.datasourece.api.Reciter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataSources @Inject constructor(
    private val contentResolverHelper: ContentResolverHelper ,
    private val apiQuran: ApiQuran
) {
    suspend fun getArtist(): List<Reciter> {
        Log.i("apiQuran" , "getArtist")

        return apiQuran.getAllMp3quran().reciters
    }

    suspend fun gitLocal(): List<QuranItem> {
        val local = contentResolverHelper.getAudioData()
        val quranItemList = mutableListOf<QuranItem>()
        var index = 0
        for (i in local.indices) {
            quranItemList += QuranItem(
                index = index ,
                artist = local[i].artists ,
                surah = local[i].title ,
                uri = local[i].uri ,
                id = local[i].id ,
                isLocal = true
            )
            index ++
        }
        return quranItemList
    }

    suspend fun gitContent(): List<QuranItem> {
        Log.i("apiQuran" , "gitContent")
        val local = contentResolverHelper.getAudioData().size
        val online =  apiQuran.getAllMp3quran().asQuranList()
        val quranItemList = mutableListOf<QuranItem>()
        var index = local
        val itemNumber = local + online.size
        for (i in local until itemNumber) {

            quranItemList += QuranItem(
                index = index ,
                artist = online[i - local].artists ,
                surah = online[i - local].surah ,
                uri = online[i - local].uri ,
                moshaf = online[i - local].moshaf ,
                isLocal = false
            )
            index ++
//            if (index%100==0) delay(40L)
        }





        return quranItemList

    }

}

