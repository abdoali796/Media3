package com.abdoali.mymidia3.uiCompount

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Item(
    main: String ,
    sacandery: String? ,
    modifier: Modifier = Modifier
) {

    ItemImp(title = main , artists = sacandery ,modifier=modifier)
}

@Composable
private fun ItemImp(

    title: String ,
    artists: String? ,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .padding(vertical = 12.dp , horizontal = 8.dp)
            .fillMaxWidth()
    ) {
        Text(text = title , style = MaterialTheme.typography.titleLarge)
        artists?.let { Text(text = it , style = MaterialTheme.typography.titleSmall) }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemPew() {
    ItemImp("abdo" , "all")
}