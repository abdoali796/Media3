package com.abdoali.mymidia3.ui.online

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdoali.datasourece.QuranItem
import com.abdoali.datasourece.api.Reciter
import com.abdoali.mymidia3.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMList @Inject constructor(
    private val savedStateHandle: SavedStateHandle ,
    private val repository: Repository
) : ViewModel() {
    val sura = repository.sura
    val artistsList: StateFlow<List<Reciter>>
        get() = repository.artistsList

    val favItems: StateFlow<List<QuranItem>>
        get() = repository.favItem

    private val _list: StateFlow<List<QuranItem>>
        get() = repository.list
    private val _fiterList = MutableStateFlow<List<QuranItem>>(emptyList())
    val list: StateFlow<List<QuranItem>>
        get() = _fiterList
    private val _itFav = MutableStateFlow(false)
    val itFav: StateFlow<Boolean>
        get() = _itFav

    init {

        filter()
        itFov()
    }

    fun getKey(): List<String>? = getTitle(savedStateHandle)?.split(",")
    fun getID(): Int? = getID(savedStateHandle)

    private fun filter() {
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

    val favArtist: StateFlow<List<Reciter>>
        get() = repository.favArtist

    val favSurah: StateFlow<List<String>>
        get() = repository.favSurah

    private fun itFov() {
        viewModelScope.launch {
            delay(100)
            val key = getKey()?.get(0)
            val artist = artistsList.value.find { key == it.name }

            _itFav.update {
                favArtist.value.contains(artist) || favSurah.value.contains(
                    key
                )
            }
        }
    }

    fun addFav(reciter: Int , surah: String) = viewModelScope.launch {

        if (reciter != - 1) {
            repository.addArtistFav(reciter)
        } else {
            repository.addSurahFav(
                surah
            )
        }
        itFov()
    }

    fun deleteFav(reciter: Int , surah: String) = viewModelScope.launch {
        if (reciter != - 1) {
            repository.deleteArtistFav(reciter)
        } else {
            repository
                .deleteSurahFav(surah)
        }

        itFov()
    }


}
