package com.abdoali.datasourece

import android.icu.text.AlphabeticIndex
import android.net.Uri
import android.provider.MediaStore.Audio.Artists

data class QuranItem(
    val index: Int,
    val artist:String,
    val surah: String ,
    val uri: Uri,
    val isLocal: Boolean,
)