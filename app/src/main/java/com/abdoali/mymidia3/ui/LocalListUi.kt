package com.abdoali.mymidia3.ui

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.abdoali.datasourece.QuranItem
import com.abdoali.datasourece.helper.isLocal
import com.abdoali.mymidia3.data.UIEvent
import com.abdoali.mymidia3.ui.online.ListMp

@Composable
fun LocaleList(
    quranItem: List<QuranItem>,
    onUIEvent: (UIEvent)->Unit
) {
    val quranItemList = remember{
        quranItem.isLocal()
    }

    ListMp(quranItem = quranItemList , uiEvent =onUIEvent  )
}
//////////////////////////Navigation//////////////////////

fun NavGraphBuilder.locale(list:List<QuranItem>,onUIEvent: (UIEvent) -> Unit){
    composable(LOCALE){
        LocaleList(quranItem =list  , onUIEvent = onUIEvent)
    }
}

fun NavController.navToLocale(){
    navigate(LOCALE){
        popUpTo(LOCALE){
            inclusive =false
            saveState=true
        }
launchSingleTop=true
    }
}
 const val LOCALE="LOCALELOCALELOCALE"