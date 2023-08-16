package com.abdoali.playservice.di

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.session.MediaSession
import com.abdoali.datasourece.DataSources
import com.abdoali.playservice.MediaServiceHandler
import com.abdoali.playservice.service.notifcation.NotificationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PlayerModule {
    @Provides
    @Singleton
    fun audioAttr(): AudioAttributes =
        AudioAttributes.Builder().setContentType(C.AUDIO_CONTENT_TYPE_MUSIC).setUsage(C.USAGE_MEDIA)
            .build()

    @Provides
    @Singleton
    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    fun exoplayer(
        @ApplicationContext context: Context , audioAttributes: AudioAttributes
    ): ExoPlayer =
        ExoPlayer.Builder(context).setTrackSelector(DefaultTrackSelector(context)).build().apply {
            setAudioAttributes(audioAttributes , true)

            setHandleAudioBecomingNoisy(true)
        }


    @Provides
    @Singleton
    fun provideMediaSession(
        @ApplicationContext context: Context , player: ExoPlayer
    ): MediaSession = MediaSession.Builder(context , player).build()


    @Provides
    @Singleton
    fun provideServiceHandler(
        player: ExoPlayer ,
        data: DataSources
    ): MediaServiceHandler =
        MediaServiceHandler(
            player = player ,
            data

        )


    @Provides
    @Singleton
    fun provideNotificationManager(
        @ApplicationContext context: Context ,
        player: ExoPlayer
    ): NotificationManager =
        NotificationManager(
            context = context ,
            player = player
        )
}
