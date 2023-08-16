package com.abdoali.mymidia3.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class VMTest @Inject constructor(
  private val savedStateHandle: SavedStateHandle
):ViewModel() {
    fun getKey(key:String):String?{
        return savedStateHandle[key]
    }
}