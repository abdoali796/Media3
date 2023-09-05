package com.abdoali.playservice.service

import android.content.Intent
import androidx.media3.common.Player
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.abdoali.playservice.service.notifcation.NotificationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class ServicePlayer: MediaSessionService() {

    @Inject
    lateinit var mediaSession: MediaSession



    @Inject
    lateinit var notificationManager: NotificationManager


    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession = mediaSession

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    override fun onStartCommand(intent: Intent? , flags: Int , startId: Int): Int {
        notificationManager.startNotificationService(
            mediaSessionService = this,
            mediaSession = mediaSession
        )

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
mediaSession.run {
    if (player.playbackState != Player.STATE_IDLE) {
        player.seekTo(0)
        player.playWhenReady = false
        player.stop()
    }
}
    }
    

}