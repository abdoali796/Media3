package com.abdoali.datasourece.helper

import com.abdoali.datasourece.QuranItem
import com.abdoali.datasourece.SurahSort

fun List<QuranItem>.itSort():List<QuranItem>{
    val list=this
    val sort= mutableListOf<QuranItem>()
    val sorts= SurahSort.sura
    for (i in  list){
        if (sorts.contains(i.surah)  ) sort+=i
    }
    return sort
}

