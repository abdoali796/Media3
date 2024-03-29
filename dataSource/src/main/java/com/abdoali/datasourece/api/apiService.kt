package com.abdoali.datasourece.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/v3/reciters?")
    suspend fun getListReciters(
        @Query("language")language:String="eng"
    ):String

@GET("api/v3/recent_reads?language=ar")
suspend fun getRecentRead():String

@GET("api/v3/ayat_timing")
suspend fun getAyaTiming(
    @Query("surah")surahInt: Int
    ,@Query("read")readInt: Int
):String

}