package com.abdoali.datasourece.di

import android.content.Context
import android.content.SharedPreferences
import com.abdoali.datasourece.ContentResolverHelper
import com.abdoali.datasourece.DataSources
import com.abdoali.datasourece.DataSourcesImp
import com.abdoali.datasourece.api.ApiQuran
import com.abdoali.datasourece.api.ApiService
import com.abdoali.datasourece.cach.QuranItemCachDatabase
import com.abdoali.datasourece.cach.ReciterCachDatabase
import com.abdoali.datasourece.cach.SpUtil
import com.abdoali.datasourece.read.QuranWords
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
    fun contentResolverHelperProvide(
        @ApplicationContext context: Context,
//        apiService: ApiService
    ): ContentResolverHelper = ContentResolverHelper(
        context
    )

    @Provides
    @Singleton
    fun apiQuranProvide(
        apiService: ApiService
    ) = ApiQuran(apiService)

    @Provides
    @Singleton
    fun quranWords(@ApplicationContext context: Context, apiService: ApiService) =
        QuranWords(context , apiService)

    @Provides
    @Singleton
    fun repositoriesProvide(
        apiService: ApiQuran,
        contentResolverHelper: ContentResolverHelper,
//        quranItemCachDatabase: QuranItemCachDatabase,
//        reciterCachDatabase: ReciterCachDatabase,
//        spUtil: SpUtil
    ): DataSources {
        return DataSourcesImp(
            contentResolverHelper, apiService,
//        quranItemCachDatabase = quranItemCachDatabase,
//        reciterCachDatabase = reciterCachDatabase,
//        spUtil = spUtil
        )
    }


    @Provides
    @Singleton
    fun sharedPre(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("cachData", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun spUtilPre(sharedPreferences: SharedPreferences): SpUtil {
        return SpUtil(sharedPreferences)
    }

    @Provides
    @Singleton
    fun quranItemCachDatabasePro(@ApplicationContext context: Context): QuranItemCachDatabase {
        return androidx.room.Room.databaseBuilder(
            context, QuranItemCachDatabase::class.java, "cachDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun reciterCachDatabase(@ApplicationContext context: Context): ReciterCachDatabase {
        return androidx.room.Room.databaseBuilder(
            context, ReciterCachDatabase::class.java, "ReciterCachv"
        ).build()
    }

    @Provides
    @Singleton
    fun apiReciters(): ApiService {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val okHttpClient: OkHttpClient =
            OkHttpClient.Builder().connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS).build()
        val retrofit = Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi)).client(okHttpClient)
            .baseUrl(BASE_URL).build()
        return retrofit.create(ApiService::class.java)
    }

}

private const val BASE_URL = "https://www.mp3quran.net"