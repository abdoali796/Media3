package com.abdoali.datasourece

import android.net.Uri
import androidx.core.net.toUri
import com.abdoali.datasourece.api.Mp3quran
import com.abdoali.datasourece.api.surahString

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
//            Log.i("asQuranListIndex",number+uri)
                songList += Quran(artists=artists , surah = surahString(surahList[s].toInt()) , moshaf = reciter.moshaf[i].name, uri)
                index ++
            }

        }
    }
    return songList
}

data class Quran(

    val artists: String ,
    val surah: String ,
    val moshaf:String,
    val uri: Uri
)