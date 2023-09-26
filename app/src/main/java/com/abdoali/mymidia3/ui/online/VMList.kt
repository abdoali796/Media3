package com.abdoali.mymidia3.ui.online

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdoali.datasourece.QuranItem
import com.abdoali.datasourece.api.Reciter
import com.abdoali.datasourece.api.surah
import com.abdoali.mymidia3.data.Repository
import com.abdoali.playservice.MediaServiceHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMList @Inject constructor(
    private val savedStateHandle: SavedStateHandle ,
    private val repository: Repository
) : ViewModel() {
    val sura = surah()
    val artistsList: StateFlow<List<Reciter>>
        get() = repository.artistsList

    private val _list:StateFlow<List<QuranItem>>
        get() = repository.list
    private val _fiterList = MutableStateFlow<List<QuranItem>>(emptyList())
    val list: StateFlow<List<QuranItem>>
        get() = _fiterList
    init {
        filter()
    }

    fun getKey(): List<String>? = getTitle(savedStateHandle)?.split(",")


    fun filter() {
        viewModelScope.launch {
            val key = getKey()

            val o = if (! key.isNullOrEmpty()) {

                if (key.size == 1) _list.value.filter {
                    key[0] == it.surah || key[0] == it.artist
                } else {
                    _list.value.filter { key[0] == it.artist && key[1] == it.moshaf }
                }

            } else {
                _list.value
            }
            _fiterList.emit(o)
        }


    }

}
