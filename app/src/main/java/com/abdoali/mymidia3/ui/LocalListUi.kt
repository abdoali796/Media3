package com.abdoali.mymidia3.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.abdoali.datasourece.QuranItem
import com.abdoali.datasourece.helper.isLocal
import com.abdoali.mymidia3.data.UIEvent

@Composable
fun LocaleList(
    quranItem: List<QuranItem> ,
    onUIEvent: (UIEvent) -> Unit
) {
    val quranItemList = remember {
        quranItem.isLocal()
    }
    if (quranItemList.isEmpty()) Text(text = "no think to show")
    AnimatedVisibility(quranItemList.isNotEmpty()) {


        ListMp(quranItem = quranItemList , "Locale" , uiEvent = onUIEvent)
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