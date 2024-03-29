package com.abdoali.mymidia3.uiCompount

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Item(
    main: String ,
    text2: String? ,

    modifier: Modifier = Modifier ,
    text3: String? = null ,
) {

    ItemImp(title = main , artists = text2 , modifier = modifier , text3)
}

@Composable
private fun ItemImp(

    title: String ,
    artists: String? ,
    modifier: Modifier = Modifier ,
    moshaf: String? = null ,
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ) , modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp , horizontal = 12.dp)
    ) {

        Column(
            modifier

                .padding(8.dp)

                .fillMaxWidth()
        ) {
            Text(text = title , style = MaterialTheme.typography.titleLarge)
            artists?.let {
                Text(
                    text = it ,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            moshaf?.let { Text(text = it , style = MaterialTheme.typography.titleSmall) }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemPew() {

    ItemImp("abdo" , "all")
}