package com.abdoali.mymidia3.ui.online

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.abdoali.datasourece.api.Reciter
import com.abdoali.mymidia3.uiCompount.Item

@Composable
fun screenTitle(
    list: List<String>? ,
    string: String ,
    navController: NavController ,
    modifier: Modifier = Modifier ,
    reciter: List<Reciter> = emptyList() ,
) {
    Column(

        modifier = modifier
            .fillMaxSize()
            .border(BorderStroke(2.dp , Color.Black))
    ) {
        LazyColumn() {
            item { Item(main = string , sacandery = null) }
            if (!list.isNullOrEmpty()) {
                items(items = list) {
                    Item(
                        main = it ,
                        sacandery = null ,
                        modifier.clickable { navController.navToList(it) })
                }
            } else {
                items(reciter){reciter->
                    reciter.moshaf.forEach {
                        Item(main = reciter.name , sacandery =it.name , modifier.clickable { navController.navToList(reciter.name+","+it.name) } )
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

fun NavGraphBuilder.sourList(list: List<String> , navController: NavController) {
    composable(SOUR_LIST) {
        screenTitle(list , "Sour" , navController)
    }
}

fun NavGraphBuilder.artistList(list: List<Reciter> , navController: NavController) {
    composable(ARTIST_LIST) {
        screenTitle(null , "Artist" , navController , reciter = list)
    }
}


const val SOUR_LIST = "SOUR_LISTSOUR_LIST"
const val ARTIST_LIST = "ARTIST_LIST"