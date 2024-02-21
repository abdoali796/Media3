package com.abdoali.datasourece

import android.net.Uri
import androidx.core.net.toUri
import com.abdoali.datasourece.api.Mp3quran
import com.abdoali.datasourece.cach.QuranItemCach

data class QuranItem(
    val index: Int ,
    val artist: String ,
    val surah: String ,
    val uri: Uri ,
    val id: Long = 0 ,

    val isLocal: Boolean ,
    val moshaf:String?=null,
){

}
fun QuranItem.asQuranCach(): QuranItemCach {
    return QuranItemCach(
        index = this.index,
        artist = this.artist,
        surah = this.surah,
        uri = this.uri.toString(),
        id = this.id,
        isLocal = this.isLocal,
        moshaf = this.moshaf
    )
}

suspend fun Mp3quran.asQuranList(): List<Quran> {
    val reciters = this.reciters
    var index = 0
    val songList = mutableListOf<Quran>()
    for (reciter in reciters) {
        val artists = reciter.name
        for (i in reciter.moshaf.indices) {
            val server = reciter.moshaf[i].server
            val surahList = reciter.moshaf[i].surah_list.split(",")
//        Log.i("asQuranList",artists+server)
            for (s in surahList.indices) {
                val number =
                    if (surahList[s].length == 1) "00${surahList[s]}" else if (surahList[s].length == 2) "0${surahList[s]}" else surahList[s]
                val uri = "$server$number.mp3".toUri()
//            Log.i("asQuranListIndex", index.toString() +uri )
                songList += Quran(artists=artists , surah = surahString(surahList[s].toInt()) , moshaf = reciter.moshaf[i].name, uri, id = reciter.id.toLong())
                index ++
//                if (index%100==0) delay(40L)
            }

        }
    }
    return songList
}

data class Quran(

    val artists: String ,
    val surah: String ,
    val moshaf:String,
    val uri: Uri,
    val id: Long
)