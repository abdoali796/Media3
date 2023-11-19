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

    val vm: VMList = hiltViewModel()

    val quranItem by vm.list.collectAsState()
    val itFav by vm.itFav.collectAsState()
    val favItems by vm.favItems.collectAsState()
    val key = remember {
        vm.getKey()
    }
    val id = remember {
        vm.getID()
    }
    LaunchedEffect(key1 = key) {
        Log.i("getKey" , "key$key")

    }

    if (key?.get(0) == "Fav") {
        ListMp(
            title = "المفضبلة" ,
            quranItem = favItems ,
            uiEvent = uiEvent ,
            id = null ,
            modifier = modifier ,
            favorAddAction = vm::addFav ,
            favorDelAction = vm::deleteFav ,
            itFav = itFav

        )
    } else {
        ListMp(
            title = key?.get(0) ,
            quranItem = quranItem ,
            uiEvent = uiEvent ,
            id = id ,
            modifier = modifier ,
            favorAddAction = vm::addFav ,
            favorDelAction = vm::deleteFav ,
            itFav = itFav

        )
    }


}

fun NavController.navToList(title: String , id: Int) {
    navigate("$LIST/$title/$id")
}

fun NavGraphBuilder.list(onUIEvent: (UIEvent) -> Unit) {
    composable(
        "$LIST/{$TITLE_LIST}/{$ID}" , arguments = listOf(
            navArgument(TITLE_LIST) { NavType.StringType } ,
            navArgument(ID) { NavType.IntType }
        )) {
        List(uiEvent = onUIEvent)
    }
}

fun getTitle(savedStateHandle: SavedStateHandle): String? {

    return savedStateHandle[TITLE_LIST]
}

fun getID(savedStateHandle: SavedStateHandle): Int? {
    val data: String? = savedStateHandle[ID]

    return data?.toIntOrNull()
}

private const val LIST = "LISTABd"
private const val TITLE_LIST = "TITLE_LIST"
private const val ID = "ID_hhhD"
