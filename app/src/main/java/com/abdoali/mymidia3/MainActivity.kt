package com.abdoali.mymidia3

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.abdoali.mymidia3.ui.MainUi
import com.abdoali.mymidia3.ui.theme.Mymidia3Theme
import com.abdoali.playservice.service.ServicePlayer
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var isServiceRunning = false

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalPermissionsApi::class)
    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//    controllerFutur=MediaController.Builder(
//        this ,
//        SessionToken(this , ComponentName(this , MyService::class.java))
//    )
//
//        .buildAsync()
//    controller?.addMediaItems(contentResolverHelper.getMateData())
//
//    controller?.prepare()
//    controller?.play()
//    controller?.setDeviceVolume(100 )

//        val song = contentResolverHelper.getAudioData()
//        val player = ExoPlayer.Builder(this).build()

//        song.forEach { song1: com.abdoali.mymidia3.data.Song ->
//            val item = MediaItem.fromUri(song1.uri)
//            player.addMediaItem(item)
//        }
//        player.prepare()


        setContent {
            Mymidia3Theme {
                val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    rememberPermissionState(permission = Manifest.permission.READ_MEDIA_AUDIO)
                } else {
                    rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)
                }
                LaunchedEffect(key1 = true , block = {
                    if (! permission.status.isGranted) permission.launchPermissionRequest()
                })
//                val name = rememberSaveable {
//                    player.deviceVolume.toString()
//                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize() , color = MaterialTheme.colorScheme.background
                ) {
                    MainUi(
                    )
                }
            }
        }

        startService()
    }
@RequiresApi(Build.VERSION_CODES.O)
private fun   startService() {
    if (!isServiceRunning){
        val intent = Intent(this , ServicePlayer::class.java)
startForegroundService(intent)
        isServiceRunning =true
    }
}

    override fun onDestroy() {
        super.onDestroy()
        val intent = Intent(this , ServicePlayer::class.java)
        stopService(intent)
        isServiceRunning =false
    }
}

