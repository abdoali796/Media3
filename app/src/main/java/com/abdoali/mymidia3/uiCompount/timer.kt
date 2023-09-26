package com.abdoali.mymidia3.uiCompount

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abdoali.mymidia3.R
import com.abdoali.mymidia3.data.UIEvent
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Timer(
    showTimer: (Boolean) -> Unit , onUIEvent: (UIEvent) -> Unit
) {
    var time by rememberSaveable { mutableIntStateOf(20) }
    var increasePrs by remember {
        mutableStateOf(false)
    }
    var decreasePrs by remember {
        mutableStateOf(false)
    }
    AlertDialog(onDismissRequest = { showTimer(false) }) {
        Card(onClick = {}) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally ,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.choose_the_time) ,
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = stringResource(R.string.min , time) ,
                    style = MaterialTheme.typography.titleLarge
                )
                Row {

                    Icon(
                        Icons.Default.ArrowUpward ,
                        contentDescription = null ,
                        Modifier
                            .padding(16.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(onPress = {
                                    try {
                                        increasePrs = true

                                        awaitRelease()
                                    } finally {
                                        increasePrs = false
                                    }
                                })
                            })


                    Icon(Icons.Default.ArrowDownward ,
                        contentDescription = null ,
                        Modifier
                            .padding(16.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(onPress = {
                                    try {
                                        decreasePrs = true

                                        awaitRelease()
                                    } finally {
                                        decreasePrs = false
                                    }
                                })
                            })
                }
                Row {
                    SuggestionChip(onClick = { time = 60 } ,
                        label = { Text(stringResource(R.string._60_min)) })
                    SuggestionChip(onClick = { time = 90 } ,
                        label = { Text(stringResource(R.string._90_min)) })
                    SuggestionChip(onClick = { time = 120 } ,
                        label = { Text(stringResource(R.string._120_min)) })
                }
                Row {
                    Button(onClick = {
                        onUIEvent(UIEvent.Timer(time))
                        showTimer(false)
                    }) {
                        Text(text = stringResource(R.string.save))
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Button(onClick = { showTimer(false) } ,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)) {
                        Text(text = stringResource(R.string.cancel))
                    }
                }
            }

        }
    }
    LaunchedEffect(key1 = increasePrs) {
        val speedUp = 200L
        while (increasePrs) {
            time ++
            delay(speedUp)

        }
    }
    LaunchedEffect(key1 = decreasePrs) {
        val speedUp = 200L
        while (decreasePrs) {
            if (time == 0) break
            time --

            delay(speedUp)

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun timerPre() {
    Timer({} , {})
}