package com.abdoali.mymidia3.widget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.abdoali.mymidia3.data.Repository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WidgetBarReceiver ():GlanceAppWidgetReceiver() {
    @Inject
  lateinit var  handler: Repository
    override val glanceAppWidget: GlanceAppWidget
        get() = WidgetBar(handler)
}