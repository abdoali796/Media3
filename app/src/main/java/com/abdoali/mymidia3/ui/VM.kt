package com.abdoali.mymidia3.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdoali.datasourece.QuranItem
import com.abdoali.datasourece.api.Reciter
import com.abdoali.mymidia3.data.Repository
import com.abdoali.mymidia3.data.UIEvent
import com.abdoali.playservice.MediaServiceHandler
import com.abdoali.playservice.PlayerEvent
import com.abdoali.playservice.service.ServiceControl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VM @Inject constructor(

    private val repository: Repository ,
//    private val mediaServiceHandler: MediaServiceHandler ,

    private val serviceControl: ServiceControl
) : ViewModel() {

    var name = repository.elapsedTime



    val isTimerOn = repository.isTimerOn
    val isPlaying = repository.isPlaying

    //    private var _BUFFERING = MutableStateFlow(false)

    //    private var _progress = MutableStateFlow(0f)

    //    private var _title = MutableStateFlow("")
    val title = repository.title


    val artist: StateFlow<String>
        get() = repository.artist


    private var isServiceStart = false

    init {
        Log.i("view module" , "initinitinitinit")

        startSarvie()
        viewModelScope.launch {
            repository.updateUI()
        }


    }



    private fun startSarvie() {
        if (! isServiceStart) {
            serviceControl.startService()
            isServiceStart = true
        }
    }

    fun onUIEvent(uiEvent: UIEvent) = viewModelScope.launch {
        repository.onUIEvent(uiEvent)
    }



    override fun onCleared() {
        serviceControl.stopService()




        isServiceStart = false
        super.onCleared()
    }
}
