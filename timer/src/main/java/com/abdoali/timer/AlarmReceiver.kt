package com.abdoali.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.abdoali.playservice.MediaServiceHandler
import com.abdoali.playservice.PlayerEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AlarmReceiver : BroadcastReceiver(){
    @Inject
    lateinit var handler: MediaServiceHandler
    override fun onReceive(context: Context? , intent: Intent?) {
        GlobalScope.launch {
            handler.onPlayerEvent(PlayerEvent.Stop)
        }

    }
}