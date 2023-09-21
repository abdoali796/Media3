package com.abdoali.mymidia3.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.abdoali.datasourece.QuranItem
import com.abdoali.datasourece.helper.isLocal
import com.abdoali.mymidia3.R
import com.abdoali.mymidia3.data.UIEvent

@Composable
fun LocaleList(
    quranItem: List<QuranItem> ,
    onUIEvent: (UIEvent) -> Unit
) {
//    val quranItemList = remember(key1 = quranItem) {
//        quranItem.isLocal()
//    }
    if (quranItem.isEmpty()) Text(text = stringResource(R.string.no_thing_to_show))
    AnimatedVisibility(quranItem.isNotEmpty()) {


        ListMp(quranItem = quranItem , stringResource(R.string.locale) , uiEvent = onUIEvent)
    }
}
//////////////////////////Navigation//////////////////////

fun NavGraphBuilder.locale(list: List<QuranItem> , onUIEvent: (UIEvent) -> Unit) {
    composable(LOCALE) {
        LocaleList(quranItem = list , onUIEvent = onUIEvent)
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