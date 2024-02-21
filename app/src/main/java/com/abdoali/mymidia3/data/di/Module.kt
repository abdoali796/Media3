package com.abdoali.mymidia3.data.di

import android.content.Context
import androidx.room.Room
import com.abdoali.datasourece.read.QuranWords
import com.abdoali.mymidia3.Timer
import com.abdoali.mymidia3.data.Repository
import com.abdoali.mymidia3.data.RepositoryImp
import com.abdoali.mymidia3.data.database.favorite.artist.ArtistDatabase
import com.abdoali.mymidia3.data.database.favorite.item.ItemUrlDatabase
import com.abdoali.mymidia3.data.database.favorite.surah.SurahDatabase
import com.abdoali.mymidia3.data.downloed.DownloadFile
import com.abdoali.playservice.MediaServiceHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    @Singleton
    fun timerPro(@ApplicationContext context: Context): Timer = Timer(context)

    @Provides
    @Singleton
    fun repositoryPro(
        mediaServiceHandler: MediaServiceHandler,
        timer: Timer,
        artistDatabase: ArtistDatabase,
        surahDatabase: SurahDatabase,
        itemUrlDatabase: ItemUrlDatabase,
        downloadFile: DownloadFile,
        quranWords: QuranWords
    ): Repository = RepositoryImp(
        mediaServiceHandler = mediaServiceHandler,
        timer = timer,
        artistDatabase = artistDatabase,
        surahDatabase = surahDatabase,
        itemUrlDatabase = itemUrlDatabase,
        downloadFile = downloadFile,
        quranWords=quranWords
    )

    @Provides
    @Singleton
    fun artistDatabasePro(@ApplicationContext context: Context): ArtistDatabase {
        return Room.databaseBuilder(
            context, ArtistDatabase::class.java, "ArtistDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun surahDatabasePro(@ApplicationContext context: Context): SurahDatabase {
        return Room.databaseBuilder(
            context, SurahDatabase::class.java, "SurahDatabase"
        ).build()

    }

    @Provides
    @Singleton
    fun itemDatabasePro(@ApplicationContext context: Context): ItemUrlDatabase {
        return Room.databaseBuilder(
            context, ItemUrlDatabase::class.java, "ItemUrlDDatabase"
        ).build()

    }

    @Provides
    @Singleton
    fun downloadPre(@ApplicationContext context: Context): DownloadFile = DownloadFile(context)

}