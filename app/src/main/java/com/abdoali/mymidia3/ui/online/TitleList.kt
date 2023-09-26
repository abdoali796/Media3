package com.abdoali.mymidia3.ui.online

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.abdoali.mymidia3.uiCompount.Item

@Composable
fun screenTitle(
//    list: List<String>? ,
    keys: String ,
    navController: NavController ,
    modifier: Modifier = Modifier ,
//    reciter: List<Reciter> = emptyList() ,
) {
    val vm: VMList = hiltViewModel()
    val sour = remember {
        vm.sura
    }

    Column(

        modifier = modifier
            .fillMaxSize()
            .border(BorderStroke(2.dp , Color.Black))
    ) {

        LazyColumn {

            if (keys == SOUR_LIST) {
                items(items = sour) {
                    Item(
                        main = it ,
                        text2 = null ,
                        modifier.clickable { navController.navToList(it) })
                }
            } else {
                items(vm.artistsList.value) { reciter ->
                    reciter.moshaf.forEach {
                        Item(
                            main = reciter.name ,
                            text2 = it.name ,

                            modifier.clickable { navController.navToList(reciter.name + "," + it.name) })
                    }
                }
            }
        }

    }
}

fun NavController.navToSourList() {
    navigate(SOUR_LIST)
}

fun NavController.navToArtistList() {
    navigate(ARTIST_LIST)
}

fun NavGraphBuilder.sourList(navController: NavController) {
    composable(SOUR_LIST) {
        screenTitle(SOUR_LIST , navController)
    }
}

fun NavGraphBuilder.artistList(navController: NavController) {
    composable(ARTIST_LIST) {
        screenTitle(ARTIST_LIST , navController)
    }
}

const val SOUR_LIST = "SOUR_LISTSOUR_LIST"
const val ARTIST_LIST = "ARTIST_LIST"