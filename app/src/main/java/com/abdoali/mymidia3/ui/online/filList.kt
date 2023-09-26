package com.abdoali.mymidia3.ui.online

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abdoali.mymidia3.R

//import com.abdoali.mymidia3.ui.online.ListMp

@Composable
fun ListTitle(
    title: String ,
    titleList: List<String> ,
    actionNav: (String) -> Unit ,
    actionShowAll: () -> Unit ,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
            .border(
                1.dp ,
                color = MaterialTheme.colorScheme.onPrimaryContainer ,
                shape = MaterialTheme.shapes.large
            )
            .padding(3.dp)
    ) {
        Text(text = title , style = MaterialTheme.typography.headlineLarge)
        for (i in 1..6) {
            Text(
                text = titleList[i] ,
                modifier.clickable { actionNav(titleList[i]) } ,
                style = MaterialTheme.typography.titleLarge)
        }
        Button(onClick = actionShowAll) {
            Text(text = stringResource(R.string.show_all , title))
        }

    }

}

@Preview(showBackground = true)
@Composable
private fun ListPre() {
    ListTitle(
        "test" ,
        listOf(
            "test" ,
            "test" ,
            "test" ,
            "test" ,
            "test" ,
            "test" ,
            "test" ,
            "test" ,
            "test" ,
            "test" ,
            "test"
        ) ,
        {} , {}
    )
}