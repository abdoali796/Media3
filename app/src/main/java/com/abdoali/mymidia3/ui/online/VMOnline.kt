package com.abdoali.mymidia3.ui.online

import androidx.lifecycle.ViewModel
import com.abdoali.datasourece.QuranItem
import com.abdoali.datasourece.api.Reciter
import com.abdoali.mymidia3.data.Repository
import com.abdoali.mymidia3.data.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class VMOnline @Inject constructor(
    private val repository: Repository
):ViewModel() {

    val list:StateFlow<List<QuranItem>>
        get() = repository.list

    val artists:StateFlow<List<Reciter>>
        get() = repository.artistsList

    val soura:StateFlow<List<String>>
        get() = repository.sura


fun onUIEvent(uiEvent: UIEvent)=repository.onUIEvent(uiEvent)


}