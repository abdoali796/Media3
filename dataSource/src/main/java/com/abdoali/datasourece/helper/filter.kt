package com.abdoali.datasourece.helper

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.abdoali.datasourece.QuranItem
import com.abdoali.datasourece.api.Surah
import com.abdoali.datasourece.api.SurahSort

fun List<QuranItem>.itSort():List<QuranItem>{
    val list=this
    val sort= mutableListOf<QuranItem>()
    val sorts= SurahSort.sura
    for (i in  list){
        if (sorts.contains(i.surah)  ) sort+=i
    }
    return sort
}

