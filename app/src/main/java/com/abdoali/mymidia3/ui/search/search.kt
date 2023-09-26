package com.abdoali.mymidia3.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.abdoali.mymidia3.R
import com.abdoali.mymidia3.uiCompount.Item

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search() {
    val vm: VMSearch = hiltViewModel()
    val searchText by vm.searchText.collectAsState()
    val isSearching by vm.isSearching.collectAsState()
    val quranListSearch by vm.itemsFilterSearch.collectAsState()
    Column {


        DockedSearchBar(
            query = searchText ,
            onQueryChange = vm::onSearchTextChange ,
            active = isSearching ,
placeholder = { Text(text = stringResource(R.string.search_text))},
            onSearch = { vm.onSearch(false) } ,
            onActiveChange = vm::onSearch ,
            trailingIcon = { Icon(Icons.Filled.Search , contentDescription = null) } ,
            content = {
                if (quranListSearch.size > 3) {
                repeat(4){i->
                        Item(main = quranListSearch[i].surah , text2 = quranListSearch[i].artist , Modifier.clickable {
                            vm.playIndex(quranListSearch[i].index)

                    })}
                }else{
                    quranListSearch.forEach { itme ->
                        Item(main = itme.surah , text2 = itme.artist , Modifier.clickable {
                            vm.playIndex(itme.index)
                        }) }
                }
            }
        )
        LazyColumn {
            items(items = quranListSearch , key = { i -> i.index }) { quran ->
                Item(main = quran.surah , text2 = quran.artist , Modifier.clickable {
                    vm.playIndex(quran.index)
                } , text3 = quran.moshaf)
            }

        }
    }
}
