package com.abdoali.mymidia3.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdoali.datasourece.QuranItem
import com.abdoali.mymidia3.data.Repository
import com.abdoali.mymidia3.data.UIEvent
import com.abdoali.playservice.MediaServiceHandler
import com.abdoali.playservice.PlayerEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMSearch @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    fun onSearch(boolean: Boolean) {
        _isSearching.value = boolean
    }

    private val _list = repository.list
    val itemsFilter = _list

    val itemsFilterSearch = searchText.combine(_list) { text , item ->
        if (text.isBlank()) {
            if (item.size > 1001) item.shuffled().subList(1 , 100) else item


        } else {
            item.shuffled()
                .filter {
                    it.query(text)
                }
        }

    }.stateIn(
        viewModelScope , SharingStarted.WhileSubscribed(10000) , _list.value
    )

    private fun QuranItem.query(query: String): Boolean {
        val matching = listOf(
            artist , surah
        )
        return matching.any {
            it.contains(query , ignoreCase = true)
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text

    }

    fun playIndex(index: Int) {
        viewModelScope.launch {
            repository.onUIEvent(UIEvent.SeekToIndex(index))
        }
    }
}