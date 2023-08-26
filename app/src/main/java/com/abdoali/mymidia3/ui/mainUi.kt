package com.abdoali.mymidia3.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.abdoali.mymidia3.composeble.FancyAnimatedIndicator
import com.abdoali.mymidia3.data.DataEvent
import com.abdoali.mymidia3.ui.online.ONLINE
import com.abdoali.mymidia3.ui.online.navToOnline
import com.abdoali.mymidia3.ui.theme.Mymidia3Theme
import com.abdoali.mymidia3.uiCompount.MinControlImp
import com.abdoali.mymidia3.uiCompount.NavHostAudie
import com.abdoali.mymidia3.uiCompount.PlayUi
import com.abdoali.mymidia3.uiCompount.getIndexDestination

@OptIn(ExperimentalMaterial3Api::class)
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
    val artistsList = vm.artistsList.collectAsState().value
    val navController = rememberNavController()
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var selectedItem by remember { mutableIntStateOf(0) }
    val labelList = listOf(LOCALE , ONLINE)
    val iconList = listOf(
        Icons.Default.Save ,
        Icons.Default.NewReleases
    )
    val actionList =
        listOf(DataEvent.Local , DataEvent.NewApi , DataEvent.AllApi , DataEvent.FovApi)
    val scope = rememberCoroutineScope()
    val sheetScaffoldState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val navback by navController.currentBackStackEntryAsState()
    val destination = navback?.destination
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        FancyAnimatedIndicator(
            tabPositions = tabPositions ,
            selectedTabIndex = getIndexDestination(destination?.route)
        )
    }

    Scaffold(
        topBar = {
            TabRow(
                selectedTabIndex = 0 ,
                indicator = indicator
            ) {
                labelList.forEachIndexed { index , title ->
                    Tab(
                        text = { Text(title) } ,
                        selected = false ,
                        onClick = {
                            selectedItem = index

                        } ,
                    )
                }

            }

//            DockedSearchBar(
//                query = searchText ,
//                onQueryChange = vm::onSearchTextChange ,
//                active = isSearching ,
//
//                onSearch = { vm.onSearch(false) } ,
//                onActiveChange = vm::onSearch ,
//                trailingIcon = { Icon(Icons.Filled.Search , contentDescription = null) } ,
//                content = {
//                    LazyColumn {
//                        items(items = quranListSearch , key = { i -> i.index }) { quran ->
//                            Column(
//                                Modifier
//
//                                    .clickable {
//                                        vm.onUIEvent(UIEvent.SeekToIndex(quran.index))
//                                    }) {
//
//                                Text(text = quran.artist)
//                                Text(text = quran.surah)
//                                Text(text = quran.index.toString())
//                                Text(text = quran.uri.toString())
//                                Text(text = "=======  ============")
//                            }
//
//                        }
//                    }
//                }
//            )

        } ,

        floatingActionButtonPosition = FabPosition.Center ,
        floatingActionButton = {
            Column {


                MinControlImp(isPlayerEvent = isPlaying ,
                    name = title ,
                    onUIEvent = vm::onUIEvent ,
                    modifier = Modifier.clickable { openBottomSheet = true })


//                NavigationBar {
//
//                    labelList.forEachIndexed { index , item ->
//                        NavigationBarItem(
//                            alwaysShowLabel = true ,
//                            label = { Text(text = item) } ,
//                            selected = selectedItem == index ,
//                            onClick = {
//                                vm.onDataEvent(actionList[index])
//                                when (index){
//                                    1->navController.navToLocale()
//                                    2->navController.navToOnline()
//                                    else ->navController.navToLocale()
//                                }
//
//                                selectedItem = index
//                            } ,
//                            icon = {
//                                Icon(
//                                    imageVector = iconList[index] ,
//                                    contentDescription = labelList[index]
//                                )
//                            })
//
//                    }
//                }
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

        NavHostAudie(
            navController=navController,
            quranList = quranList,
            soura=soura,
            artistsList = artistsList,
            uiEvent = vm::onUIEvent,
            modifier = Modifier.padding(padding)

        )


        LaunchedEffect(key1 = selectedItem) {
            Log.i("selectedItem" , "$selectedItem  ${destination?.route}")
            if (selectedItem == 0) navController.navToLocale() else if (selectedItem==1) navController.navToOnline()
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

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Mymidia3Theme {
//        MainUiImp("Android" , {} , {} , {} , {})
    }
}