package com.abdoali.mymidia3

import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.CallSuper
import com.abdoali.playservice.MediaServiceHandler
import com.abdoali.playservice.PlayerEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : AppWidgetProvider() {
    @Inject
    lateinit var handler: MediaServiceHandler
    override fun onReceive(context: Context? , intent: Intent?) {

    super.onReceive(context, intent)
        Log.i("handler.onPlayerEvent(PlayerEvent.Stop)", "kkkk")
        runBlocking {
            handler.onPlayerEvent(PlayerEvent.Kill)

        }
    }



}
