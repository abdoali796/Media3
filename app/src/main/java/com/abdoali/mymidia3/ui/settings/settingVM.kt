package com.abdoali.mymidia3.ui.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdoali.mymidia3.data.MySharedPreferences
import com.abdoali.mymidia3.data.Repository
import com.abdoali.mymidia3.data.asTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingVM @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val savedTheme = MySharedPreferences.theme?.asTheme() ?: Theme.Blue
    private var _theme = MutableStateFlow(savedTheme)
    val theme: StateFlow<Theme>
        get() = _theme

    var language = MutableStateFlow<String>(Locale.getDefault().isO3Language)
        private set

    val isLoading=repository.isLoading

    init {

        updateData()
        Log.i("language" , "settingVM${language.value}")


        viewModelScope.launch {
            repository.updateUI()
        }

    }

    fun changeTheme(newTheme: Theme) {

        _theme.update { newTheme }
        Log.i("changeTheme" , "vm ${_theme.value}")
        MySharedPreferences.theme = newTheme.name

    }

    fun updateData() {
        Log.i("updateData","updateData")
        language.value = Locale.getDefault().isO3Language
        viewModelScope.launch {
            repository.prepareData()
            repository.getFavArtist()
        }
        viewModelScope.launch {
            repository.getFavSurah()
        }

    }

}

enum class Theme {
    Brown , Blue , Red
}