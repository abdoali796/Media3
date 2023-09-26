package com.abdoali.mymidia3.ui.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdoali.mymidia3.data.Repository
import com.abdoali.mymidia3.data.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMPlayer @Inject constructor(private val repository: Repository) : ViewModel() {

    val buffering = repository.buffering

    val title = repository.title

    val progress: StateFlow<Float>
        get() = repository.progress

    val duration = repository.duration

    val uri = repository.uri
    val progressString = repository.progressString
    val isPlaying = repository.isPlaying
    val shuffle = repository.shuffle

    val repeat = repository.repeat

    //    private var _artist = MutableStateFlow("")
    val artist: StateFlow<String>
        get() = repository.artist

    init {
        viewModelScope.launch {
            repository.updateProgress()
        }

    }

    fun onUIEven(uiEvent: UIEvent) = repository.onUIEvent(uiEvent)
}