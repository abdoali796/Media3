package com.abdoali.datasourece

import android.util.Log
import com.abdoali.datasourece.api.Mp3quran
import com.abdoali.datasourece.api.Reciter
import javax.inject.Inject

class DataSources @Inject constructor(
 private val  contentResolverHelper: ContentResolverHelper,
 private val  apiQuran: ApiQuran
){
    suspend fun getArtist():List<Reciter> {
        Log.i("apiQuran","getArtist")

        return apiQuran.getAllMp3quran().reciters
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
              id=local[i].id,
             isLocal =  true
          )
          index++
            }else{
                quranItemList+= QuranItem(
                   index =  index ,
                  artist =   online[i - localRang].artists ,
                   surah =  online[i - localRang].surah ,
                   uri =  online[i - localRang].uri ,
                    moshaf = online[i-localRang].moshaf,
                   isLocal =  false
                )
            index++
            }
        }




return quranItemList

    }

}

