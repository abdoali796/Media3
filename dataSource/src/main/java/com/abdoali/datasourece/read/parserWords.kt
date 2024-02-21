package com.abdoali.datasourece.read

import org.json.JSONObject

fun parserWords (jsonObject: JSONObject):Read {
    val id =jsonObject.getInt("id")
    val name =jsonObject.getString("name")
    val transliteration=jsonObject.getString("transliteration")
    val type =jsonObject.getString("transliteration")
    val total_verses =jsonObject.getInt("total_verses")
    val versesJsonArray = jsonObject.getJSONArray("verses")
    val verse = mutableListOf<Verse>()
    for (i in 0 until versesJsonArray.length()){
        val item =versesJsonArray.getJSONObject(i)
        val idV = item.getInt("id")
        val text = item.getString("text")
        val transliterationV = item.getString("transliteration")
        verse+=Verse(idV ,text ,transliterationV)

    }
    return  Read(id , name , total_verses ,transliteration ,type ,verse)
}