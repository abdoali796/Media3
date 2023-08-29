package com.abdoali.mymidia3.ui.online

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class VMTest @Inject constructor(
  private val savedStateHandle: SavedStateHandle
):ViewModel() {
    fun getKey(key:String):List<String>?{
        val key:String? =  savedStateHandle[key]
        return key?.split(",")
    }
}