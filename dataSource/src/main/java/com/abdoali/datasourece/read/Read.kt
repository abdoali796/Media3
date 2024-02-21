package com.abdoali.datasourece.read

data class Read(
    val id: Int,
    val name: String,
    val total_verses: Int,
    val transliteration: String,
    val type: String,
    val verses: List<Verse> ,

)
data class ReadAndTiming(
    val read: Read ,
    val timing: AyaTiming?
)