package com.abdoali.mymidia3.composeble

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FancyIndicator(color: Color , modifier: Modifier = Modifier) {
    // Draws a rounded rectangular with border around the Tab, with a 5.dp padding from the edges
    // Color is passed in as a parameter [color]
    Box(
        modifier
            .padding(5.dp)
            .fillMaxSize()
            .border(BorderStroke(2.dp , color) , RoundedCornerShape(5.dp))
    )
}

@Composable
fun FancyAnimatedIndicator(tabPositions: List<TabPosition> , selectedTabIndex: Int) {
    val colors = MaterialTheme.colorScheme.onSurface
    val transition = updateTransition(selectedTabIndex)
    val indicatorStart by transition.animateDp(
        transitionSpec = {
            // Handle directionality here, if we are moving to the right, we
            // want the right side of the indicator to move faster, if we are
            // moving to the left, we want the left side to move faster.
            if (initialState < targetState) {
                spring(dampingRatio = 1f , stiffness = 50f)
            } else {
                spring(dampingRatio = 1f , stiffness = 1000f)
            }
        } , label = ""
    ) {
        tabPositions[it].left
    }

    val indicatorEnd by transition.animateDp(
        transitionSpec = {
            // Handle directionality here, if we are moving to the right, we
            // want the right side of the indicator to move faster, if we are
            // moving to the left, we want the left side to move faster.
            if (initialState < targetState) {
                spring(dampingRatio = 1f , stiffness = 1000f)
            } else {
                spring(dampingRatio = 1f , stiffness = 50f)
            }
        } , label = ""
    ) {
        tabPositions[it].right
    }



    FancyIndicator(
        // Pass the current color to the indicator
        colors ,
        modifier = Modifier
            // Fill up the entire TabRow, and place the indicator at the start
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            // Apply an offset from the start to correctly position the indicator around the tab
            .offset(x = indicatorStart)
            // Make the width of the indicator follow the animated width as we move between tabs
            .width(indicatorEnd - indicatorStart)
    )
}

//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.WindowInsets
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.selection.toggleable
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Favorite
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material3.BottomSheetDefaults
//import androidx.compose.material3.BottomSheetScaffold
//import androidx.compose.material3.Button
//import androidx.compose.material3.Checkbox
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.ListItem
//import androidx.compose.material3.ModalBottomSheet
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.material3.rememberBottomSheetScaffoldState
//import androidx.compose.material3.rememberModalBottomSheetState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.nestedscroll.nestedScroll
//import androidx.compose.ui.semantics.Role
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import kotlinx.coroutines.launch
//
//@Preview
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ModalBottomSheetSample() {
//    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
//    var skipPartiallyExpanded by remember { mutableStateOf(false) }
//    var edgeToEdgeEnabled by remember { mutableStateOf(false) }
//    val scope = rememberCoroutineScope()
//    val bottomSheetState = rememberModalBottomSheetState(
//        skipPartiallyExpanded = skipPartiallyExpanded
//    )
//
//    // App content
//    Column(
//        modifier = Modifier.fillMaxSize() ,
//        horizontalAlignment = Alignment.CenterHorizontally ,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Row(
//            Modifier.toggleable(
//                value = skipPartiallyExpanded ,
//                role = Role.Checkbox ,
//                onValueChange = { checked -> skipPartiallyExpanded = checked }
//            )
//        ) {
//            Checkbox(checked = skipPartiallyExpanded , onCheckedChange = null)
//            Spacer(Modifier.width(16.dp))
//            Text("Skip partially expanded State")
//        }
//        Row(
//            Modifier.toggleable(
//                value = edgeToEdgeEnabled ,
//                role = Role.Checkbox ,
//                onValueChange = { checked -> edgeToEdgeEnabled = checked }
//            )
//        ) {
//            Checkbox(checked = edgeToEdgeEnabled , onCheckedChange = null)
//            Spacer(Modifier.width(16.dp))
//            Text("Toggle edge to edge enabled.")
//        }
//        Button(onClick = { openBottomSheet = ! openBottomSheet }) {
//            Text(text = "Show Bottom Sheet")
//        }
//    }
//
//    // Sheet content
//    if (openBottomSheet) {
//        val windowInsets = if (edgeToEdgeEnabled)
//            WindowInsets(0) else BottomSheetDefaults.windowInsets
//
//        ModalBottomSheet(
//            onDismissRequest = { openBottomSheet = false } ,
//            sheetState = bottomSheetState ,
//            windowInsets = windowInsets
//        ) {
//            Row(Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.Center) {
//                Button(
//                    // Note: If you provide logic outside of onDismissRequest to remove the sheet,
//                    // you must additionally handle intended state cleanup, if any.
//                    onClick = {
//                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
//                            if (! bottomSheetState.isVisible) {
//                                openBottomSheet = false
//                            }
//                        }
//                    }
//                ) {
//                    Text("Hide Bottom Sheet")
//                }
//            }
//            var text by remember { mutableStateOf("") }
//            OutlinedTextField(value = text , onValueChange = { text = it })
//            LazyColumn {
//                items(50) {
//                    ListItem(
//                        headlineContent = { Text("Item $it") } ,
//                        leadingContent = {
//                            Icon(
//                                Icons.Default.Favorite ,
//                                contentDescription = "Localized description"
//                            )
//                        }
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Preview
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SimpleBottomSheetScaffoldSample() {
//    val scope = rememberCoroutineScope()
//    val scaffoldState = rememberBottomSheetScaffoldState()
//
//    BottomSheetScaffold(
//        scaffoldState = scaffoldState ,
//        sheetPeekHeight = 128.dp ,
//        sheetContent = {
//            Box(
//                Modifier
//                    .fillMaxWidth()
//                    .height(128.dp) ,
//                contentAlignment = Alignment.Center
//            ) {
//                Text("Swipe up to expand sheet")
//            }
//            Column(
//                Modifier
//                    .fillMaxWidth()
//                    .padding(64.dp) ,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text("Sheet content")
//                Spacer(Modifier.height(20.dp))
//                Button(
//                    onClick = {
//                        scope.launch { scaffoldState.bottomSheetState.partialExpand() }
//                    }
//                ) {
//                    Text("Click to collapse sheet")
//                }
//            }
//        }) { innerPadding ->
//        Box(Modifier.padding(innerPadding)) {
//            Text("Scaffold Content")
//        }
//    }
//}
//
//@Preview
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun BottomSheetScaffoldNestedScrollSample() {
//    val colors = listOf(
//        Color(0xFFffd7d7.toInt()) ,
//        Color(0xFFffe9d6.toInt()) ,
//        Color(0xFFfffbd0.toInt()) ,
//        Color(0xFFe3ffd9.toInt()) ,
//        Color(0xFFd0fff8.toInt())
//    )
//
//    val scaffoldState = rememberBottomSheetScaffoldState()
//    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
//
//    BottomSheetScaffold(
//        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection) ,
//        sheetContent = {
//            LazyColumn {
//                items(50) {
//                    ListItem(
//                        headlineContent = { Text("Item $it") } ,
//                        leadingContent = {
//                            Icon(
//                                Icons.Default.Favorite ,
//                                contentDescription = "Localized description"
//                            )
//                        }
//                    )
//                }
//            }
//        } ,
//        scaffoldState = scaffoldState ,
//        sheetPeekHeight = 128.dp ,
//        topBar = {
//            TopAppBar(
//                title = { Text("Bottom sheet scaffold") } ,
//                navigationIcon = {
//                    IconButton(onClick = { /*TODO*/ }) {
//                        Icon(Icons.Default.Menu , contentDescription = "Localized description")
//                    }
//                } ,
//                scrollBehavior = scrollBehavior
//            )
//        } ,
//    ) { innerPadding ->
//        LazyColumn(contentPadding = innerPadding) {
//            items(100) {
//                Box(
//                    Modifier
//                        .fillMaxWidth()
//                        .height(50.dp)
//                        .background(colors[it % colors.size])
//                )
//            }
//        }
//    }
//}
