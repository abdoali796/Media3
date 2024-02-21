package com.abdoali.datasourece.api

import com.abdoali.datasourece.cach.ReciterCach

data class Reciter(
    val id: Int ,
    val letter: String ,
    val moshaf: List<Moshaf> ,
    val name: String
){
    fun  listMoshaf(){

    }
}

fun Reciter.toReciterCach():ReciterCach{
    return ReciterCach(id, letter, moshaf, name)
}