package com.abdoali.datasourece

import android.util.Log
import com.abdoali.datasourece.api.ApiQuran
import com.abdoali.datasourece.api.Reciter
import javax.inject.Inject


interface DataSources {
    suspend fun getArtist(): List<Reciter>
    suspend fun gitLocal(): List<QuranItem>
    suspend fun gitContent(): List<QuranItem>
}


class DataSourcesImp @Inject constructor(
    private val contentResolverHelper: ContentResolverHelper,
    private val apiQuran: ApiQuran
): DataSources {

   override suspend fun getArtist(): List<Reciter> {
        Log.i("apiQuran", "getArtist")

        return apiQuran.getAllMp3quran().reciters
    }

   override suspend fun gitLocal(): List<QuranItem> {
        val local = contentResolverHelper.getAudioData()
        val quranItemList = mutableListOf<QuranItem>()
        var index = 0
        for (i in local.indices) {
            quranItemList += QuranItem(
                index = index,
                artist = local[i].artists,
                surah = local[i].title,
                uri = local[i].uri,
                id = local[i].id,
                isLocal = true
            )
            index++
        }
        return quranItemList
    }

 override   suspend fun gitContent(): List<QuranItem> {
        Log.i("apiQuran", "gitContent")
        val local = contentResolverHelper.getAudioData().size
        val online = apiQuran.getAllMp3quran().asQuranList()
        val quranItemList = mutableListOf<QuranItem>()
        var index = local
        val itemNumber = local + online.size
        for (i in local until itemNumber) {

            quranItemList += QuranItem(
                index = index,
                artist = online[i - local].artists,
                surah = online[i - local].surah,
                uri = online[i - local].uri,
                moshaf = online[i - local].moshaf,
                id = online[i-local].id,
                isLocal = false
            )
            index++
//            if (index%100==0) delay(40L)
        }





        return quranItemList

    }

}

//package com.abdoali.datasourece
//
//import android.util.Log
//import com.abdoali.datasourece.api.Reciter
//import com.abdoali.datasourece.api.toReciterCach
//import com.abdoali.datasourece.cach.QuranItemCachDatabase
//import com.abdoali.datasourece.cach.ReciterCachDatabase
//import com.abdoali.datasourece.cach.SpUtil
//import com.abdoali.datasourece.cach.asQuranItem
//import com.abdoali.datasourece.cach.toReciterList
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.flow
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.flow.map
//import javax.inject.Inject
//
//class DataSources @Inject constructor(
//    private val contentResolverHelper: ContentResolverHelper,
//    private val apiQuran: ApiQuran,
//    private val quranItemCachDatabase: QuranItemCachDatabase,
//    private val reciterCachDatabase: ReciterCachDatabase,
//    private val spUtil: SpUtil
//
//
//) {
//    private val _quranList = MutableStateFlow<List<QuranItem>>(emptyList())
//    val quranList: StateFlow<List<QuranItem>>
//        get() = _quranList
//
//
//    //    suspend fun
//    suspend fun getArtist(): Flow<List<Reciter>> {
//
//        Log.i("apiQuran", "getArtistaAAA")
//        if (cacheOrNot()) {
//            try {
//                val reciter = apiQuran.getAllMp3quran().reciters
//                reciterCachDatabase.reciterDao().delete()
//                Log.i("apiQuran", "getArtist $reciter")
//                reciter.forEach { reciterCachDatabase.reciterDao().insert(it.toReciterCach()) }
//                return flowOf(reciter)
//            } catch (
//                e: Exception
//            ) {
//                Log.i("apiQuran", e.message.toString())
//            }
//
////
//        }
//
//        return reciterCachDatabase.reciterDao().getReciterCach().map { it.toReciterList() }
//    }
//
//    suspend fun gitLocal(): List<QuranItem> {
//        val local = contentResolverHelper.getAudioData()
//        val quranItemList = mutableListOf<QuranItem>()
//        var index = 0
//        for (i in local.indices) {
//            quranItemList += QuranItem(
//                index = index,
//                artist = local[i].artists,
//                surah = local[i].title,
//                uri = local[i].uri,
//                id = local[i].id,
//                isLocal = true
//            )
//            index++
//        }
//        return emptyList()
//    }
//
//    suspend fun gitContent(): Flow<List<QuranItem>> {
//        if (cacheOrNot()) {
//            return flow<List<QuranItem>> {
//                Log.i("apiQuran", "gitContent")
////        val local = contentResolverHelper.getAudioData().size
//                var online= listOf<Quran>()
//                val local = 0
//                try {
//                    online = apiQuran.getAllMp3quran().asQuranList()
//
//
//                    val quranItemList = mutableListOf<QuranItem>()
//                    var index = local
//                    val itemNumber = local + online.size
//                    for (i in local until itemNumber) {
//
//                        quranItemList += QuranItem(
//                            index = index,
//                            artist = online[i - local].artists,
//                            surah = online[i - local].surah,
//                            uri = online[i - local].uri,
//                            moshaf = online[i - local].moshaf,
//                            isLocal = false
//                        )
//                        index++
//
//
////            if (index%100==0) delay(40L)
//                    }
//                    Log.i("apiQuran", "emitData")
//                    cacheData(quranItemList)
//                    spUtil.putDate(2)
//                    emit(quranItemList)
//                    Log.i("apiQuran", "save data")
//
//                    spUtil.putDate(2)
//                }catch (e:Exception){
//
//                    spUtil.putDate(0)
//
//                }
//            }
//        }
//        return quranItemCachDatabase.quranItemCachDao().getAllQuranItem().map { it.asQuranItem() }
//    }
//
//    private suspend fun cacheData(quranItemList: MutableList<QuranItem>) {
//        quranItemList.forEach { quranItem ->
//            quranItemCachDatabase.quranItemCachDao().insert(
//                quranItem.asQuranCach()
//            )
//
//        }
//
//
//    }
//
//    private fun cacheOrNot(): Boolean {
//        val data = spUtil.getDate()
//        Log.i("cacheOrNot", data.toString())
//        return data == 0
//    }
//}
//
