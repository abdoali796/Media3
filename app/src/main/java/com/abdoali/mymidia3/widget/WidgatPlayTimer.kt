package com.abdoali.mymidia3.widget

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.glance.GlanceId
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Row
import com.abdoali.mymidia3.data.Repository

class WidgetPLayTimer(private val repository: Repository) :GlanceAppWidget(){

    override suspend fun provideGlance(context: Context, id: GlanceId) {


     provideContent {

     val timer by repository.duration.collectAsState()
     val isPlaying by repository.isPlaying.collectAsState()
         GlanceTheme {
             Scaffold {

                 Row (){
                 }
             }
         }
     }
    }
}