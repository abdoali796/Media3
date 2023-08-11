package com.abdoali.datasourece.api

import retrofit2.http.GET

interface ApiService {
    @GET("api/v3/reciters?language=ar")
    suspend fun getListReciters():String
}