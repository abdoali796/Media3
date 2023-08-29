package com.abdoali.mymidia3.ui.online

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.abdoali.datasourece.QuranItem
import com.abdoali.mymidia3.data.UIEvent
import com.abdoali.mymidia3.ui.ListMp
import com.abdoali.mymidia3.uiCompount.Item

@Composable
fun List(
    quran: List<QuranItem> ,
    uiEvent: (UIEvent) -> Unit ,
    modifier: Modifier = Modifier
) {

    val vm: VMTest = hiltViewModel()

    var quranItem = remember {
        emptyList<QuranItem>()
    }

    val key = vm.getKey(TITLE_LIST)
//    LaunchedEffect(key1 = key) {

    Log.i("getKey" , "key$key")

    val quranItemList = remember(key1 = key) {

        if (! key.isNullOrEmpty()) {

             if (key.size==1) quran.filter {
                 key[0] == it.surah || key[0]==it.artist
             }else{
                 quran.filter { key[0] == it.artist && key[1] == it.moshaf }
             }

        } else {
            quran
        }
    }

    ListMp(
        quranItemList ,
        key?.get(0) ,
        uiEvent , modifier
    )
}


fun NavController.navToList(title: String) {
    navigate("$LIST/$title")
}

fun NavGraphBuilder.list(list: List<QuranItem> , onUIEvent: (UIEvent) -> Unit) {
    composable(
        "$LIST/{$TITLE_LIST}" , arguments = listOf(
            navArgument(TITLE_LIST) { NavType.StringType } ,

            )) {
        List(quran = list , uiEvent = onUIEvent)
    }
}

fun getTitle(savedStateHandle: SavedStateHandle): String? {


    return savedStateHandle[TITLE_LIST]
}


private const val LIST = "LISTABd"
private const val TITLE_LIST = "TITLE_LIST"
