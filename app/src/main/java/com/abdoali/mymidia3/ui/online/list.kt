package com.abdoali.mymidia3.ui.online

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.abdoali.mymidia3.data.UIEvent
import com.abdoali.mymidia3.ui.ListMp

@Composable
fun List(
//    quran: List<QuranItem> ,
    uiEvent: (UIEvent) -> Unit ,
    modifier: Modifier = Modifier
) {

    val vm: VMTest = hiltViewModel()

    val quranItem by vm.list.collectAsState()

    val key = remember {
        vm.getKey()
    }
    LaunchedEffect(key1 = key) {
        Log.i("getKey" , "key$key")
    }




    ListMp(
        quranItem ,
        key?.get(0) ,
        uiEvent , modifier
    )
}


fun NavController.navToList(title: String) {
    navigate("$LIST/$title")
}

fun NavGraphBuilder.list(onUIEvent: (UIEvent) -> Unit) {
    composable(
        "$LIST/{$TITLE_LIST}" , arguments = listOf(
            navArgument(TITLE_LIST) { NavType.StringType } ,

            )) {
        List(  uiEvent = onUIEvent)
    }
}

fun getTitle(savedStateHandle: SavedStateHandle): String? {

    return savedStateHandle[TITLE_LIST]
}


private const val LIST = "LISTABd"
private const val TITLE_LIST = "TITLE_LIST"
