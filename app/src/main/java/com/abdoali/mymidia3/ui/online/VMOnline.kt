package com.abdoali.mymidia3.ui.online

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdoali.datasourece.QuranItem
import com.abdoali.datasourece.api.Reciter
import com.abdoali.mymidia3.data.Repository
import com.abdoali.mymidia3.data.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMOnline @Inject constructor(
    private val repository: Repository
) : ViewModel() {

//    val list: StateFlow<List<QuranItem>>
//        get() = repository.list

    val artists: StateFlow<List<Reciter>>
        get() = repository.artistsList

    val surah: StateFlow<List<String>>
        get() = repository.sura

    val favArtist: StateFlow<List<Reciter>>
        get() = repository.favArtist

    val favSurah: StateFlow<List<String>>
        get() = repository.favSurah

    val favItem: StateFlow<List<QuranItem>>
        get() = repository.favItem

    init {
        updateFavItem()

    }

    fun onUIEvent(uiEvent: UIEvent) = repository.onUIEvent(uiEvent)
    private fun updateFavItem() {
        Log.i("updateFavItem" , "called")
        viewModelScope.launch {
            if (artists.value.isEmpty()) {
                delay(1000L)
                Log.i("updateFavItem" , "false")
                updateFavItem()

            } else {
                repository.getFAVItem()
                Log.i("updateFavItem" , "ture")
            }
        }
    }


}