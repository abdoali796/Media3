package com.abdoali.mymidia3.ui.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import com.abdoali.mymidia3.data.MySharedPreferences
import com.abdoali.mymidia3.data.asTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingVM @Inject constructor():ViewModel() {
    private val savedTheme = MySharedPreferences.theme?.asTheme() ?:Theme.Blue
 private var _theme = MutableStateFlow(savedTheme)
    val theme:StateFlow<Theme>
        get() = _theme

    fun changeTheme(newTheme: Theme){

        _theme.update { newTheme }
        Log.i("changeTheme","vm ${_theme.value}")
        MySharedPreferences.theme=newTheme.name

    }

}

enum class Theme{
    Brown ,Blue,Red
}