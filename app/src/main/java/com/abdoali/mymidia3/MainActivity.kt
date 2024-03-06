package com.abdoali.mymidia3

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.abdoali.mymidia3.data.MySharedPreferences
import com.abdoali.mymidia3.ui.mainUi
import com.abdoali.mymidia3.ui.search.search
import com.abdoali.mymidia3.ui.settings.SettingVM
import com.abdoali.mymidia3.ui.settings.setting
import com.abdoali.mymidia3.ui.splashscreen.SPLASH
import com.abdoali.mymidia3.ui.splashscreen.splash
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
//        val r = QuranWords(this).getWord("f")
//        Log.i("assetAbdo ", r.toString())
// Usage example
//        val url = "https://server10.mp3quran.net/minsh/001.mp3"
//        val fileName = "abdo.mp3"
//        downloadFile(this, url, fileName, onComplete = {
//            Log.i("DownloadAbdo!", fileName)
//        }, onProgress = {a,v -> Log.i("DownloadAbdo", "$a/$v")}
//        )

//        downloadFile(this, url, fileName) { downloadedBytes, totalBytes ->
//            // Update progress UI
//            Log.d("Download", "Downloaded $downloadedBytes/$totalBytes bytes")
//        } {
//            // Download complete
//            Log.d("Download", "Download complete!")
//        }
        setContent {
            val settingVM: SettingVM = hiltViewModel()
            val isLoading by settingVM.isLoading.collectAsState()
            val colorTheme by settingVM.theme.collectAsState()
            LaunchedEffect(key1 = colorTheme, block = {
                Log.i("changeTheme", "main $colorTheme")
            })
            Mymidia3Theme(
                colorTheme = colorTheme
            ) {
                val languag by settingVM.language.collectAsState()
                if (Locale.getDefault().isO3Language != languag) {

                    settingVM.updateData()
                }

                val mainNavController = rememberNavController()
                val subNavController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    LaunchedEffect(key1 = isLoading, block = {
                        Log.i("isLoading", "isLoadingForMain$isLoading")
                    })

//                    AnimatedVisibility(visible = !isLoading) {


                    NavHost(
                        navController = mainNavController, startDestination = SPLASH
                    ) {

                        mainUi(
                            mainNavController = mainNavController,
                            subNavController = subNavController
                        )
                        search(
                            subNavController = subNavController,
                            mainNavController = mainNavController
                        )
                        setting(settingVM)
                        splash(mainNavController, isLoading)

                    }

//                    }
                }
            }

        }


    }

}

