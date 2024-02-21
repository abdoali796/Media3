package com.abdoali.datasourece.api

data class Mp3quran(
    val reciters: List<Reciter>
){
//    fun recitersString():List<String>
//    {
//        val list= mutableListOf<String>()
//        for (reciter in this.reciters){
//            for (moshaf in reciter.moshaf){
//                list += "${reciter.name}  ${moshaf.name} ${moshaf.surah_total}"
//            }
//        }
//        return list
//    }
}