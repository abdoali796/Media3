package com.abdoali.mymidia3.ui.player

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdoali.mymidia3.data.Repository
import com.abdoali.mymidia3.data.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMPlayer @Inject constructor(private val repository: Repository) : ViewModel() {

    val buffering = repository.buffering

    val title = repository.title
    private val currencyInt = repository.currentMediaItemIndex
    val progress: StateFlow<Float>
        get() = repository.progress

    val duration = repository.duration

    val uri = repository.uri
    val progressString = repository.progressString
    val isPlaying = repository.isPlaying
    val shuffle = repository.shuffle
    val repeat = repository.repeat
    var itFov = MutableStateFlow(false)

    //    private var _artist = MutableStateFlow("")
    val artist: StateFlow<String>
        get() = repository.artist

    init {

        viewModelScope.launch {
            repository.updateProgress()
        }
        itFav()
    }

    fun onUIEven(uiEvent: UIEvent) = repository.onUIEvent(uiEvent)

    fun addFav() {
        viewModelScope.launch {
            if (! itFov.value) {
                repository.addItemIndexFav(currencyInt.value)
            } else {
                repository.deleteItemFav(currencyInt.value)
            }
            itFav()
        }

    }

    private fun itFav() {
        viewModelScope.launch {
            currencyInt.collect { int ->
                delay(100)
                itFov.update { repository.itFavItem(int) }
                Log.i("  itFavItem" , "itFav vm ${itFov.value}")
            }
        }


    }

}