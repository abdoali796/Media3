package com.abdoali.mymidia3.uiCompount

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.abdoali.datasourece.QuranItem
import com.abdoali.datasourece.api.Reciter
import com.abdoali.mymidia3.data.UIEvent
import com.abdoali.mymidia3.ui.LOCALE
import com.abdoali.mymidia3.ui.locale
import com.abdoali.mymidia3.ui.online.ONLINE
import com.abdoali.mymidia3.ui.online.artistList
import com.abdoali.mymidia3.ui.online.list
import com.abdoali.mymidia3.ui.online.online
import com.abdoali.mymidia3.ui.online.sourList

@Composable
fun NavHostAudie(
    navController: NavHostController ,
    quranList: List<QuranItem> ,
    artistsList: List<Reciter> ,
    soura: List<String> ,
    uiEvent: (UIEvent) -> Unit ,
    modifier: Modifier = Modifier ,
    local: List<QuranItem> ,
) {
    NavHost(
        navController = navController ,
        startDestination = LOCALE ,
        modifier = modifier
    ) {
        list( uiEvent)
        locale(local , uiEvent)
        online(
            list = quranList ,
            surah = soura ,
            artists = artistsList ,
            navController = navController ,
            onUIEvent = uiEvent
        )
        sourList(soura , navController)
        artistList(artistsList , navController)


    }
}

fun getIndexDestination(string: String?):Int{
    if (string==null) return 0
    if (string== LOCALE) return 0
    return 1
}