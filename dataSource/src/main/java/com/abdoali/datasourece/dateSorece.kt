package com.abdoali.datasourece

import android.util.Log
import javax.inject.Inject

class DataSources @Inject constructor(
 private val  contentResolverHelper: ContentResolverHelper,
 private val  apiQuran: ApiQuran
){
    suspend fun getArtist():List<String> {
        Log.i("apiQuran","getArtist")

        return apiQuran.getAllMp3quran().reciters.map {
            it.name
        }
    }

    suspend fun gitContent():List<QuranItem>{
        Log.i("apiQuran","gitContent")
        val local=contentResolverHelper.getAudioData()
        val online=apiQuran.getAllMp3quran().asQuranList()
        val quranItemList= mutableListOf<QuranItem>()
        var index=0
        val localRang=local.size
        val itemNumber=local.size  + online.size
        for (i in 0 until itemNumber){
            if (i <localRang){
          quranItemList+= QuranItem(
             index =  index ,
             artist =  local[i].artists ,
             surah =  local[i].title ,
             uri =  local[i].uri ,
             isLocal =  true
          )
          index++
            }else{
                quranItemList+= QuranItem(
                   index =  index ,
                  artist =   online[i - localRang].artists ,
                   surah =  online[i - localRang].surah ,
                   uri =  online[i - localRang].uri ,
                   isLocal =  false
                )
            index++
            }
        }




return quranItemList

    }

}

