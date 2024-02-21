package com.abdoali.mymidia3.ui.local

import android.Manifest
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.abdoali.mymidia3.R
import com.abdoali.mymidia3.ui.ListMp
import com.abdoali.mymidia3.uiCompount.lottie.LottieCompose
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocaleList(

) {
    val vmLocal: VMLocalList = hiltViewModel()
    val quranItem by vmLocal.list.collectAsState()
    val permission =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            rememberPermissionState(permission = Manifest.permission.READ_MEDIA_AUDIO)
        } else {
            rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)
        }

    var refreshCounter = remember {
        2
    }
    Column {

        AnimatedVisibility(! permission.status.isGranted) {
            Column {

//                LottieCompose()
                Button(onClick = {

                    permission.launchPermissionRequest()

                }) {
                    Text(text = "allow permission")
                }
            }
        }
        AnimatedVisibility(
            permission.status.isGranted && quranItem.size < 2
                    && refreshCounter > 0
        ) {
            Button(onClick = {
                refreshCounter -= 1
                vmLocal.update()
            }) {
                Text(text = "refresh$refreshCounter")
            }
        }







        if (quranItem.isEmpty()) Text(text = stringResource(R.string.no_thing_to_show))
        AnimatedVisibility(quranItem.isNotEmpty()) {

            ListMp(
                quranItem = quranItem ,
                stringResource(R.string.locale) ,
                uiEvent = vmLocal::onUIEven ,
                id = null ,
                favorAddAction = { i: Int , s: String -> } ,
                favorDelAction = { i: Int , s: String -> }
            )
        }
    }
}
//////////////////////////Navigation//////////////////////

fun NavGraphBuilder.locale() {
    composable(LOCALE) {
        LocaleList(

        )
    }
}

fun NavController.navToLocale() {
    navigate(LOCALE) {
        popUpTo(LOCALE) {
            inclusive = false
            saveState = true
        }
        launchSingleTop = true
    }
}

const val LOCALE = "LOCALELOCALELOCALE"