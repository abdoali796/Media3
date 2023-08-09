package com.abdoali.mymidia3.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlayerModule {
//    @Provides
//     @Singleton
//    @Named("aa")
//    fun audioAttr(): AudioAttributes =
//        AudioAttributes.Builder().setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
//            .setUsage(C.USAGE_MEDIA).build()
//
//    @Provides
//    @Singleton
//    @Named("aa")
//    fun exoplayer(
//        @ApplicationContext context: Context , audioAttributes: AudioAttributes
//    ): ExoPlayer =
//        ExoPlayer.Builder(context)
//
//            .build()
//            .apply {
//                setAudioAttributes(audioAttributes , true)
//                setHandleAudioBecomingNoisy(true)
//            }
//

    @Provides
    @Singleton
    fun s(): String = "ِِِِِِِِِِAaaa777777777777aaaaaaaa"
}