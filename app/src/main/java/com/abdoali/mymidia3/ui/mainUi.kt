package com.abdoali.mymidia3.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.abdoali.mymidia3.R
import com.abdoali.mymidia3.composeble.FancyAnimatedIndicator
import com.abdoali.mymidia3.data.UIEvent
import com.abdoali.mymidia3.data.formatDuration
import com.abdoali.mymidia3.ui.local.navToLocale
import com.abdoali.mymidia3.ui.online.navToOnline
import com.abdoali.mymidia3.ui.player.PlayUi
import com.abdoali.mymidia3.ui.search.navToSearch
import com.abdoali.mymidia3.ui.settings.navToSetting
import com.abdoali.mymidia3.uiCompount.MinControlImp
import com.abdoali.mymidia3.uiCompount.NavHostAudie
import com.abdoali.mymidia3.uiCompount.Timer
import com.abdoali.mymidia3.uiCompount.getIndexDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUi(
    mainNavController: NavController , subNavController: NavHostController
) {
    val vm: VM = hiltViewModel()
//    val context = LocalContext.

    val timer by vm.name.collectAsState()
    val title by vm.title.collectAsState()

    val isPlaying by vm.isPlaying.collectAsState()

//    val quranList = emptyList<QuranItem>()
//    val localList by vm.localList.collectAsState()
    val isTimerOn by vm.isTimerOn.collectAsState()
//    val soura by  vm.sura.collectAsState()
//    val artistsList by vm.artistsList.collectAsState()

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val selectedItem by rememberSaveable { mutableIntStateOf(1) }
    val labelList =
        listOf(stringResource(R.string.locale) , stringResource(R.string.online))

    val sheetScaffoldState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var expand by remember {
        mutableStateOf(false)
    }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val navback by subNavController.currentBackStackEntryAsState()
    val destination = navback?.destination
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        FancyAnimatedIndicator(
            tabPositions = tabPositions ,
            selectedTabIndex = getIndexDestination(destination?.route)
        )
    }
    var showDig by rememberSaveable { mutableStateOf(false) }

    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection) ,

        topBar = {
//
            TopAppBar(title = { Text(text = "abdo ali") } ,
                scrollBehavior = scrollBehavior ,
                actions = {
                    AnimatedVisibility(visible = isTimerOn) {

                        Text(text = formatDuration(timer))
                    }
                    Row {

                        IconButton(onClick = {
                            showDig = true
                        }) {
                            if (showDig) {
                                Timer(showTimer = { showDig = it } ,
                                    onUIEvent = vm::onUIEvent)
                            }
                            if (isTimerOn) {
                                Icon(Icons.Outlined.Timer ,
                                    contentDescription = null ,
                                    modifier = Modifier.clickable {
                                        vm.onUIEvent(UIEvent.Timer(0))
                                    })
                            } else {
                                if (showDig) {
                                    Timer(showTimer = {
                                        showDig = it
                                    } , onUIEvent = vm::onUIEvent)
                                }
                                Icon(Icons.Rounded.Timer , contentDescription = null)

                            }
                        }
                    }
                    IconButton(onClick = { mainNavController.navToSearch() }) {
                        Icon(Icons.Outlined.Search , contentDescription = null)
                    }

                    IconButton(onClick = { expand = true }) {
                        Icon(Icons.Default.MoreVert , null)
                    }
                    DropdownMenu(expanded = expand ,
                        onDismissRequest = { expand = false } ,
                        modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
                        DropdownMenuItem(text = { Text(stringResource(R.string.setting)) } ,
                            onClick = { mainNavController.navToSetting() } ,
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Settings , contentDescription = null
                                )
                            } ,
                            modifier = Modifier.wrapContentSize(Alignment.TopEnd))
                    }
                })

        } ,

        floatingActionButtonPosition = FabPosition.End , bottomBar = {
            Column {

                MinControlImp(isPlayerEvent = isPlaying ,
                    name = title ,
                    onUIEvent = vm::onUIEvent ,
                    modifier = Modifier.clickable { openBottomSheet = true })

            }
        }) { padding ->
        Column(Modifier.padding(padding)) {
            TabRow(
                selectedTabIndex = 0 , indicator = indicator
            ) {
                labelList.forEachIndexed { index , title ->
                    Tab(
                        text = { Text(title) } ,
                        selected = false ,
                        onClick = {
//                            selectedItem = index
                            if (index == 0) subNavController.navToLocale() else if (selectedItem == 1) subNavController.navToOnline()

                        } ,
                    )
                }

            }

//            AnimatedVisibility(visible = artistsList.isEmpty()) {
//                Text(text = stringResource(R.string.wait_a_minute))
//            }
//            val subNavController = rememberNavController()
            NavHostAudie(
                navController = subNavController ,

                uiEvent = vm::onUIEvent ,

                )



            LazyColumn(
                modifier = Modifier.animateContentSize()

            ) {

                item {

                }
                item {
                    Spacer(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp).dp))

                }

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

}

////////////////////////////////navigation////////////////////
const val MAIN_UI = "MAIN_UI_MAIN_UI"
fun NavGraphBuilder.mainUi(
    mainNavController: NavController , subNavController: NavHostController
) {
    composable(MAIN_UI) {
        MainUi(mainNavController , subNavController = subNavController)
    }
}
