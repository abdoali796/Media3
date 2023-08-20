package com.abdoali.mymidia3.ui

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
import com.abdoali.mymidia3.uiCompount.Item

@Composable
fun screenTitle(list: List<String> ,
                navController: NavController ,
                modifier: Modifier = Modifier
) {
    Column(

        modifier = modifier.fillMaxSize().border(BorderStroke(2.dp, Color.Black))
    ) {
        LazyColumn(){
            items(items=list){
                Item(main = it , sacandery = null ,modifier.clickable { navController.navToList(it) })
            }
        }

    } }


