package com.abdoali.mymidia3.data

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
