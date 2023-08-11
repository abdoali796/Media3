package com.abdoali.datasourece.api

data class Reciter(
    val id: Int ,
    val letter: String ,
    val moshaf: List<Moshaf> ,
    val name: String
)