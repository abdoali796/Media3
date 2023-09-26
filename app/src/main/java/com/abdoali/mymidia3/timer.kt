package com.abdoali.mymidia3

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.SystemClock
import androidx.core.app.AlarmManagerCompat
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class Timer @Inject constructor(@ApplicationContext context: Context) {

    private var job: Job? = null
    private val alarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val REQUEST_CODE = 0
    private val TRIGGER_TIME = "TRIGGER_AT"

    private val minute: Long = 60_000L
    private val second: Long = 1_000L

    private val notifyIntent = Intent(context , AlarmReceiver::class.java)
    private var prefs =
        context.getSharedPreferences("com.abdoali.timer" , Context.MODE_PRIVATE)

    private val _timeSelection = MutableStateFlow<Int>(5)
    val timeSelection: MutableStateFlow<Int>
        get() = _timeSelection

    private lateinit var notifyPendingIntent: PendingIntent

    private var _alarmOn = MutableStateFlow<Boolean>(false)
    val isAlarmOn: StateFlow<Boolean>
        get() = _alarmOn

    private val _elapsedTime = MutableStateFlow<Long>(0L)
    val elapsedTime: StateFlow<Long>
        get() = _elapsedTime

    private lateinit var timer: CountDownTimer

    init {
        job = Job()
        _alarmOn.value = PendingIntent.getBroadcast(
            getApplication(context) ,
            REQUEST_CODE ,
            notifyIntent ,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        ) != null

        notifyPendingIntent = PendingIntent.getBroadcast(
            getApplication(context) ,
            REQUEST_CODE ,
            notifyIntent ,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (_alarmOn.value) {
            createTimer()
        }

    }

    private fun createTimer() {
        job.run {

            val triggerTime = loadTime()
            timer = object : CountDownTimer(triggerTime , second) {
                override fun onTick(millisUntilFinished: Long) {
                    _elapsedTime.tryEmit(triggerTime - SystemClock.elapsedRealtime())
                    if (_elapsedTime.value <= 0) {
                        resetTimer()
                    }
                }

                override fun onFinish() {
                    resetTimer()
                }
            }
            timer.start()
        }
    }

    private fun resetTimer() {
        timer.cancel()

        _elapsedTime.tryEmit(0)
        _alarmOn.value = false
    }

    fun setAlarm(isChecked: Boolean) {
        when (isChecked) {
            true -> timeSelection.value.let { startTimer(it) }
            false -> resetTimer()
        }
    }

    fun setTimeSelected(timerLengthSelection: Int) {
        _timeSelection.value = timerLengthSelection
    }

    private fun startTimer(timerLengthSelection: Int) {
        _alarmOn.value.let {
            if (! it) {
                _alarmOn.value = true
                val selectedInterval = when (timerLengthSelection) {
                    0 -> second * 10 //For testing only
                    else -> timerLengthSelection * minute
                }
                val triggerTime = SystemClock.elapsedRealtime() + selectedInterval



                AlarmManagerCompat.setExactAndAllowWhileIdle(
                    alarmManager ,
                    AlarmManager.ELAPSED_REALTIME_WAKEUP ,
                    triggerTime ,
                    notifyPendingIntent
                )

                job.run {
                    saveTime(triggerTime)
                }
            }
        }
        createTimer()
    }

    private fun saveTime(triggerTime: Long) =
        job.run {
            prefs.edit().putLong(TRIGGER_TIME , triggerTime).apply()
        }

    private fun loadTime(): Long =
        job.run {
            prefs.getLong(TRIGGER_TIME , 0)
        }

}

