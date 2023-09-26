package com.abdoali.playservice.service.notifcation

import android.app.PendingIntent
import android.graphics.Bitmap
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerNotificationManager

@UnstableApi
class NotificationAdapter(
//    private val context: Context ,
    private val pendingIntent: PendingIntent?
) : PlayerNotificationManager.MediaDescriptionAdapter {

    override fun getCurrentContentTitle(player: Player): CharSequence =
        player.mediaMetadata.albumTitle ?: ""

    override fun createCurrentContentIntent(player: Player): PendingIntent? =
        pendingIntent

    override fun getCurrentContentText(player: Player): CharSequence =
        player.mediaMetadata.displayTitle ?: ""

    //    override fun getCurrentLargeIcon(
//        player: Player ,
//        callback: PlayerNotificationManager.BitmapCallback
//    ): Bitmap? {
//        return null
//
    override fun getCurrentLargeIcon(
        player: Player ,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
//    Glide.with(context)
//        .asBitmap()
//        .load(player.mediaMetadata.artworkUri)
//        .diskCacheStrategy(DiskCacheStrategy.ALL)
//        .into(object : CustomTarget<Bitmap>() {
//            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                callback.onBitmap(resource)
//            }
//
//            override fun onLoadCleared(placeholder: Drawable?) = Unit
//        })
        return null

    }
}
