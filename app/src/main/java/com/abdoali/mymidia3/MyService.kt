package com.abdoali.mymidia3
//
//import android.app.PendingIntent
//import android.app.PendingIntent.FLAG_IMMUTABLE
//import android.app.PendingIntent.FLAG_UPDATE_CURRENT
//import android.app.PendingIntent.getActivity
//import android.app.TaskStackBuilder
//import android.content.Intent
//import android.os.Build
//import android.os.Bundle
//import androidx.media3.common.MediaItem
//import androidx.media3.datasource.DataSourceBitmapLoader
//import androidx.media3.exoplayer.ExoPlayer
//import androidx.media3.session.CacheBitmapLoader
//import androidx.media3.session.CommandButton
//import androidx.media3.session.LibraryResult
//import androidx.media3.session.MediaLibraryService
//import androidx.media3.session.MediaSession
//import androidx.media3.session.SessionCommand
//import androidx.media3.session.SessionResult
//import com.google.common.collect.ImmutableList
//import com.google.common.util.concurrent.Futures
//import com.google.common.util.concurrent.ListenableFuture
//import dagger.hilt.android.AndroidEntryPoint
//import javax.inject.Inject
//@AndroidEntryPoint
//class MyService : MediaLibraryService() {
//    @Inject
//    lateinit var exoPlayer: ExoPlayer
//    private val librarySessionCallback = CustomMediaLibrarySessionCallback()
//
//
//    private lateinit var mediaLibrarySession: MediaLibrarySession
//    private lateinit var customCommands: List<CommandButton>
//
//    private var customLayout = ImmutableList.of<CommandButton>()
//
//    companion object {
//        private const val SEARCH_QUERY_PREFIX_COMPAT = "androidx://media3-session/playFromSearch"
//        private const val SEARCH_QUERY_PREFIX = "androidx://media3-session/setMediaUri"
//        private const val CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_ON =
//            "com.abdoali.mymidia3.SHUFFLE_ON"
//        private const val CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_OFF =
//            "com.abdoali.mymidia3.SHUFFLE_OFF"
//        private const val NOTIFICATION_ID = 123
//        private const val CHANNEL_ID = "demo_session_notification_channel_id"
//        private val immutableFlag = if (Build.VERSION.SDK_INT >= 23) FLAG_IMMUTABLE else 0
//    }
//
//    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaLibrarySession? {
//        return mediaLibrarySession
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        customCommands =
//            listOf(
//                getShuffleCommandButton(
//                    SessionCommand(CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_ON , Bundle.EMPTY)
//                ) ,
//                getShuffleCommandButton(
//                    SessionCommand(CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_OFF , Bundle.EMPTY)
//                )
//            )
//        customLayout = ImmutableList.of(customCommands[0])
//        initiSession()
////        setListener(MediaSessionServiceListener())
//    }
////    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaLibrarySession {
////        return mediaLibrarySession
////    }
//    override fun onTaskRemoved(rootIntent: Intent?) {
//    if (! exoPlayer.playWhenReady || exoPlayer.mediaItemCount == 0) {
//        stopSelf()
//    }
//}
//     fun onPlaybackResumption(){
//
//    }
//    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
//    override fun  onDestroy() {
//        mediaLibrarySession.setSessionActivity(getBackStackedActivity())
//        mediaLibrarySession.release()
//        exoPlayer.release()
//        clearListener()
//        super.onDestroy()
//    }
//
//    private fun getBackStackedActivity(): PendingIntent {
//        return TaskStackBuilder.create(this).run {
//            addNextIntent(Intent(this@MyService, MainActivity::class.java))
//            addNextIntent(Intent(this@MyService, MainActivity::class.java))
//            getPendingIntent(0, immutableFlag or FLAG_UPDATE_CURRENT)
//        }
//    }
//
//    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
//    private fun initiSession() {
//
//        mediaLibrarySession =
//            MediaLibrarySession.Builder(this , exoPlayer , librarySessionCallback)
//                .setSessionActivity(getSingleTopActivity())
//                .setBitmapLoader(CacheBitmapLoader(DataSourceBitmapLoader(this)))
//                .build()
//        if (!customLayout.isEmpty()) {
//            // Send custom layout to legacy session.
//            mediaLibrarySession.setCustomLayout(customLayout)
//        }
//    }
//
//    private fun getSingleTopActivity(): PendingIntent {
//        return getActivity(
//            this ,
//            0 ,
//            Intent(this , MainActivity::class.java) ,
//            immutableFlag or FLAG_UPDATE_CURRENT
//        )
//    }
//
//    private fun getShuffleCommandButton(sessionCommand: SessionCommand): CommandButton {
//        val isOn = sessionCommand.customAction == CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_ON
//        return CommandButton.Builder()
//            .setDisplayName(
//                getString(
//                    if (isOn) R.string.exo_controls_shuffle_on_description
//                    else R.string.exo_controls_shuffle_off_description
//                )
//            )
//            .setSessionCommand(sessionCommand)
//            .setIconResId(if (isOn) R.drawable.baseline_shuffle_24 else R.drawable.baseline_shuffle_on_24)
//            .build()
//    }
//
//    private inner class CustomMediaLibrarySessionCallback : MediaLibrarySession.Callback {
//        override fun onConnect(
//            session: MediaSession ,
//            controller: MediaSession.ControllerInfo
//        ): MediaSession.ConnectionResult {
//            val connectionResult = super.onConnect(session , controller)
//            val availableSessionCommands = connectionResult.availableSessionCommands.buildUpon()
//            for (commandButton in customCommands) {
//                // Add custom command to available session commands.
//                commandButton.sessionCommand?.let { availableSessionCommands.add(it) }
//            }
//            return MediaSession.ConnectionResult.accept(
//                availableSessionCommands.build() ,
//                connectionResult.availablePlayerCommands
//            )
//
//        }
//
//        override fun onCustomCommand(
//            session: MediaSession ,
//            controller: MediaSession.ControllerInfo ,
//            customCommand: SessionCommand ,
//            args: Bundle
//        ): ListenableFuture<SessionResult> {
//            if (CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_ON == customCommand.customAction) {
//                // Enable shuffling.
//                exoPlayer.shuffleModeEnabled = true
//                // Change the custom layout to contain the `Disable shuffling` command.
//                customLayout = ImmutableList.of(customCommands[1])
//                // Send the updated custom layout to controllers.
//                session.setCustomLayout(customLayout)
//            } else if (CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_OFF == customCommand.customAction) {
//                // Disable shuffling.
//                exoPlayer.shuffleModeEnabled = false
//                // Change the custom layout to contain the `Enable shuffling` command.
//                customLayout = ImmutableList.of(customCommands[0])
//                // Send the updated custom layout to controllers.
//                session.setCustomLayout(customLayout)
//            }
//            return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
//        }
//
//        override fun onGetItem(
//            session: MediaLibrarySession ,
//            browser: MediaSession.ControllerInfo ,
//            mediaId: String
//        ): ListenableFuture<LibraryResult<MediaItem>> {
//            return super.onGetItem(session , browser , mediaId)
//        }
//
//    }
//
//
//}
//
//
