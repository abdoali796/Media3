package com.abdoali.datasourece.di

import android.content.Context
import com.abdoali.datasourece.ApiQuran
import com.abdoali.datasourece.ContentResolverHelper
import com.abdoali.datasourece.DataSources
import com.abdoali.datasourece.api.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    @Singleton
    fun  contentResolverHelperProvide(
        @ApplicationContext context: Context,
        apiService: ApiService
    ): ContentResolverHelper=
        ContentResolverHelper(
            context
        )
@Provides
@Singleton
fun apiQuranProvide(
    apiService: ApiService
)= ApiQuran(apiService)

@Provides
@Singleton
fun dataSourcesProvide(
  apiService: ApiQuran,
  contentResolverHelper: ContentResolverHelper
)=DataSources(
    contentResolverHelper,
    apiService
)


    @Provides
    @Singleton
    fun apiReciters(): ApiService {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS).build()
        val retrofit = Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi)).client(okHttpClient)
            .baseUrl(BASE_URL).build()
        return retrofit.create(ApiService::class.java)
    }

}
private const val  BASE_URL= "https://www.mp3quran.net"