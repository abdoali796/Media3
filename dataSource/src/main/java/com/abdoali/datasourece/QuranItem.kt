package com.abdoali.datasourece

import android.net.Uri

data class QuranItem(
    val index: Int ,
    val artist: String ,
    val surah: String ,
    val uri: Uri ,
    val id: Long = 0 ,

    val isLocal: Boolean ,
    val moshaf:String?=null,
)