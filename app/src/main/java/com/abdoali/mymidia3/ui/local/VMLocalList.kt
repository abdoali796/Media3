package com.abdoali.mymidia3.ui.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdoali.datasourece.QuranItem
import com.abdoali.mymidia3.data.Repository
import com.abdoali.mymidia3.data.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMLocalList @Inject constructor(private val repository: Repository) : ViewModel() {
    val list: StateFlow<List<QuranItem>>
        get() = repository.localList

    fun onUIEven(uiEvent: UIEvent) = repository.onUIEvent(uiEvent)
    fun update() {
        viewModelScope.launch {
            repository.prepareData()
            repository.getFAVItem()
        }
    }
}