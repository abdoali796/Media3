package com.abdoali.playservice.service

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ServiceControl @Inject constructor(@ApplicationContext val context: Context) {
    private val intent = Intent(context , ServicePlayer::class.java)

    fun startService() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
            Toast.makeText(context , "startForegroundService" , Toast.LENGTH_LONG).show()
            Log.i("onPlaybackStateAbdoali" , "StartForegroundService")

        } catch (e: Exception) {
            Toast.makeText(context , e.localizedMessage , Toast.LENGTH_LONG).show()
        }

    }


    fun stopService() {
        try {
            context.stopService(intent)
            Toast.makeText(context , "StopForegroundService" , Toast.LENGTH_LONG).show()
            Log.i("onPlaybackStateAbdoali" , "StopForegroundService")
        } catch (e: Exception) {
            Toast.makeText(context , e.localizedMessage , Toast.LENGTH_LONG).show()
        }

    }


}