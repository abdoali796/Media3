package com.abdoali.mymidia3

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.abdoali.mymidia3.data.MySharedPreferences
import com.abdoali.mymidia3.ui.MAIN_UI
import com.abdoali.mymidia3.ui.mainUi
import com.abdoali.mymidia3.ui.search.search
import com.abdoali.mymidia3.ui.settings.SettingVM
import com.abdoali.mymidia3.ui.settings.setting
import com.abdoali.mymidia3.ui.theme.Mymidia3Theme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MySharedPreferences.initShared(this)
        setContent {
            val settingVM: SettingVM = hiltViewModel()

            val colorTheme by settingVM.theme.collectAsState()
            LaunchedEffect(key1 = colorTheme , block = {
                Log.i("changeTheme" , "main $colorTheme")
            })
            Mymidia3Theme(
                colorTheme = colorTheme
            ) {
                val languag by settingVM.language.collectAsState()
                if (Locale.getDefault().isO3Language != languag) {

                    settingVM.updateData()
                }

                val mainNavController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize() ,
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = mainNavController ,
                        startDestination = MAIN_UI
                    ) {

                        mainUi(mainNavController)
                        search()
                        setting(settingVM)
                    }

                }
            }

        }


    }

}

