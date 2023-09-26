package com.abdoali.mymidia3.data.di

import android.content.Context
import com.abdoali.mymidia3.Timer
import com.abdoali.mymidia3.data.Repository
import com.abdoali.mymidia3.data.RepositoryImp
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
        mediaServiceHandler: MediaServiceHandler ,
        timer: Timer
    ): Repository =
        RepositoryImp(mediaServiceHandler = mediaServiceHandler , timer = timer)


}