package com.abdoali.datasourece

import android.net.Uri

data class Song(

    val uri: Uri ,
    val displayName: String ,
    val id: Long ,
    val artists: String ,
    val duration: Int ,
    val title: String ,
    val date: String,


){

}
