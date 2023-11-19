package com.abdoali.mymidia3.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abdoali.mymidia3.R
import com.abdoali.mymidia3.ui.online.navToList
import com.abdoali.mymidia3.uiCompount.Item

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    subNavController: NavController
) {
    val vm: VMSearch = hiltViewModel()
    val searchText by vm.searchText.collectAsState()
    val isSearching by vm.isSearching.collectAsState()
    val quranListSearch by vm.itemsFilterSearch.collectAsState()
    Column {

        SearchBar(query = searchText ,
            onQueryChange = vm::onSearchTextChange ,
            active = isSearching ,
            placeholder = { Text(text = stringResource(R.string.search_text)) } ,
            onSearch = { vm.onSearch(false) } ,
            onActiveChange = vm::onSearch ,
            trailingIcon = { Icon(Icons.Filled.Search , contentDescription = null) } ,
            content = {
                if (quranListSearch.size > 3) {
                    repeat(4) { i ->
                        ItemSearch(title = quranListSearch[i].surah ,
                            artists = quranListSearch[i].artist ,
                            titleAction = {
                                subNavController.navToList(
                                    title = quranListSearch[i].surah , id = - 1
                                )
                            } ,
                            artistsAction = {})
                    }
                } else {
                    quranListSearch.forEach { itme ->
                        Item(main = itme.surah ,
                            text2 = itme.artist ,
                            Modifier.clickable {
                                vm.playIndex(itme.index)
                            })
                    }
                }
            })
        LazyColumn {
            items(items = quranListSearch , key = { i -> i.index }) { quran ->
                Item(main = quran.surah , text2 = quran.artist , Modifier.clickable {
                    vm.playIndex(quran.index)
                } , text3 = quran.moshaf)
            }

        }
    }
}

@Composable
private fun ItemSearch(

    title: String ,
    titleAction: () -> Unit ,
    artists: String? ,
    artistsAction: () -> Unit ,
    modifier: Modifier = Modifier ,
    moshaf: String? = null ,
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ) ,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp , horizontal = 12.dp)
    ) {

        Column(
            modifier

                .padding(8.dp)

                .fillMaxWidth()
        ) {
            Text(
                text = title ,
                style = MaterialTheme.typography.titleLarge ,
                modifier = modifier.clickable(onClick = titleAction)
            )
            artists?.let {
                Text(
                    text = it ,
                    style = MaterialTheme.typography.titleSmall ,
                    modifier = modifier.clickable(onClick = artistsAction)
                )
            }
            moshaf?.let { Text(text = it , style = MaterialTheme.typography.titleSmall) }

        }
    }
}

@Preview
@Composable
private fun itm() {
//    ItemSearch(
//
//    )
}