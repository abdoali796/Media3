package com.abdoali.mymidia3.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abdoali.datasourece.helper.isLocal
import com.abdoali.mymidia3.data.DataEvent
import com.abdoali.mymidia3.data.UIEvent
import com.abdoali.mymidia3.ui.theme.Mymidia3Theme
import com.abdoali.mymidia3.uiCompount.Item
import com.abdoali.mymidia3.uiCompount.MinControlImp
import com.abdoali.mymidia3.uiCompount.PlayUi

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun MainUi() {
    val vm: VM = hiltViewModel()
 //   val context = LocalContext.current
    val timer by vm.name.collectAsState()
    val title by vm.title.collectAsState()
//    val artists by vm.artist.collectAsState()
//    val duration by vm.duration.collectAsState()
//    val progress by vm.progress.collectAsState()
    val isPlaying by vm.isPlaying.collectAsState()
//    val shuffle by vm.shuffle.collectAsState()
//    val progressString by vm.progressString.collectAsState()
//    val uri by vm.uri.collectAsState()
    val isSearching by vm.isSearching.collectAsState()
    val searchText by vm.searchText.collectAsState()
    val quranList by vm.itemsFilter.collectAsState()
    val quranListSearch by vm.itemsFilterSearch.collectAsState()
//    val local = quranList.isLocal()
    val soura = vm.sura
    val artistsList=vm.artistsList.collectAsState().value
    val navController = rememberNavController()
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var selectedItem by remember { mutableIntStateOf(0) }
    val labelList = listOf("Local" , "New Reads " , "All Reads" , "Best Reads")
    val iconList = listOf(
        Icons.Default.Save ,
        Icons.Default.NewReleases ,
        Icons.Default.AllInbox ,
        Icons.Default.Star
    )
    val actionList =
        listOf(DataEvent.Local , DataEvent.NewApi , DataEvent.AllApi , DataEvent.FovApi)
    val scope = rememberCoroutineScope()
    val sheetScaffoldState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    Scaffold(
        topBar = {
            DockedSearchBar(
                query = searchText ,
                onQueryChange = vm::onSearchTextChange ,
                active = isSearching ,

                onSearch = { vm.onSearch(false) } ,
                onActiveChange = vm::onSearch ,
                trailingIcon = { Icon(Icons.Filled.Search , contentDescription = null) } ,
                content = {
                    LazyColumn {
                        items(items = quranListSearch , key = { i -> i.index }) { quran ->
                            Column(
                                Modifier

                                    .clickable {
                                        vm.onUIEvent(UIEvent.SeekToIndex(quran.index))
                                    }) {

                                Text(text = quran.artist)
                                Text(text = quran.surah)
                                Text(text = quran.index.toString())
                                Text(text = quran.uri.toString())
                                Text(text = "=======  ============")
                            }

                        }
                    }
                }
            )

        } ,


        bottomBar = {
            Column {


                MinControlImp(isPlayerEvent = isPlaying ,
                    name = title ,
                    onUIEvent = vm::onUIEvent ,
                    modifier = Modifier.clickable { openBottomSheet = true })


                NavigationBar {

                    labelList.forEachIndexed { index , item ->
                        NavigationBarItem(
                            alwaysShowLabel = true ,
                            label = { Text(text = item) } ,
                            selected = selectedItem == index ,
                            onClick = {
                                vm.onDataEvent(actionList[index])
                                when (index){
                                    1->navController.navigate("1"){launchSingleTop =true}
                                    2->navController.navigate("2"){launchSingleTop =true}
                                    3->navController.navigate("3"){launchSingleTop =true}
                                    else ->navController.navigate("0"){launchSingleTop =true}
                                }

                                selectedItem = index
                            } ,
                            icon = {
                                Icon(
                                    imageVector = iconList[index] ,
                                    contentDescription = labelList[index]
                                )
                            })

                    }
                }
//                NavigationDrawerItem(label = { /*TODO*/ } , selected =  , onClick = { /*TODO*/ })
//                Row {
//                    Button(onClick = { vm.onDataEvent(DataEvent.NewApi) }) {
//                        Text(text = "NewApi")
//                    }
//                    Button(onClick = { vm.onDataEvent(DataEvent.Local) }) {
//                        Text(text =)
//                    }
//                    Button(onClick = { vm.onDataEvent(DataEvent.AllApi) }) {
//                        Text(text = "AllApi")
//                    }
//                    Button(onClick = { vm.onDataEvent(DataEvent.FovApi) }) {
//                        Text(text = "FovApi")
//                    }
//                }
            }
        }) { padding ->


        NavHost(
            navController = navController ,
            startDestination = "0" ,
            modifier = Modifier.padding(padding)
        ) {
            list(quranList,vm::onUIEvent)
            composable("0") {
                ListX(quran = quranList.filter { it.isLocal }, uiEvent = vm::onUIEvent)
            }

            composable("1") {
                screen2(artistsList,navController)
            }

            composable("2") {
                ListX(quran = quranList.filter { !it.isLocal }, uiEvent = vm::onUIEvent)

            }
            composable("3") {
                screen2(soura,navController)
            }
        }

        LazyColumn(
            modifier = Modifier
                .animateContentSize()
                .padding(paddingValues = padding)
        ) {
            item {

            }
            item { Text(text = vm.formatDuration(timer)) }
            item { Text(text = quranList.size.toString()) }
//            items(items = quranList , key = { i -> i.index }) { quran ->
//                Column(
//                    Modifier
//
//                        .clickable {
//                            vm.onUIEvent(UIEvent.SeekToIndex(quran.index))
//                        }) {
//
//                    Item( quran.surah ,  quran.artist)
//                }
//
//        }


    }
    if (openBottomSheet) {
        ModalBottomSheet(onDismissRequest = { openBottomSheet = false } ,
            sheetState = sheetScaffoldState ,
            content = {
                PlayUi(

                )
            })
    }

}}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Mymidia3Theme {
//        MainUiImp("Android" , {} , {} , {} , {})
    }
}