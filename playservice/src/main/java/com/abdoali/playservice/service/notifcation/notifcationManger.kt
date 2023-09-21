package com.abdoali.playservice.service.notifcation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_CANCEL_CURRENT
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.PendingIntent.FLAG_NO_CREATE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.ui.PlayerNotificationManager
import com.abdoali.playservice.R
import com.abdoali.playservice.service.ServicePlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


private const val NOTIFICATION_ID = 280
private const val NOTIFICATION_CHANNEL_NAME = "notification channel 1"
private const val NOTIFICATION_CHANNEL_ID = "notification channel id 1"
class NotificationManager @Inject constructor(
    @ApplicationContext private val context: Context ,
    private val player: ExoPlayer
) {

    private var notificationManager: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    init {
        createNotificationChannel()
    }

    @UnstableApi
    fun startNotificationService(
        mediaSessionService: MediaSessionService ,
        mediaSession: MediaSession
    ) {
        buildNotification(mediaSession)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundNotification(mediaSessionService)
        }
    }

    @UnstableApi
    private fun buildNotification(mediaSession: MediaSession) {
        PlayerNotificationManager.Builder(context, NOTIFICATION_ID, NOTIFICATION_CHANNEL_ID)
            .setMediaDescriptionAdapter(
                NotificationAdapter(
                    context = context,
                    pendingIntent =  TaskStackBuilder.create(context).run {
                        addNextIntent(Intent(context, Class.forName("com.abdoali.mymidia3.MainActivity")))
                        getPendingIntent(0, FLAG_NO_CREATE or FLAG_MUTABLE)
                    }
                )
            )
            .setSmallIconResourceId(R.drawable.baseline_play_arrow_24)

            .build()
            .also {
                it.setMediaSessionToken(mediaSession.sessionCompatToken)
                it.setUseFastForwardActionInCompactView(false)
                it.setUseRewindActionInCompactView(true)
                it.setUseFastForwardAction(false)
                it.setUseNextActionInCompactView(true)
                it.setUsePreviousAction(true)
                    it.setUseStopAction(true)
                        it.setUseChronometer(true)
                it.setUseRewindAction(false)
                it.setPriority(NotificationCompat.PRIORITY_LOW)
                it.setPlayer(player)

                it.setUsePreviousActionInCompactView(true)
            }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun startForegroundNotification(mediaSessionService: MediaSessionService) {
        val notification = Notification.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setActions(
            )
            .build()

        mediaSessionService.startForeground(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          val   channel=
            NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        } else {
            null
        }

    }
}