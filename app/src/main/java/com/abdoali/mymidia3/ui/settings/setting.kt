package com.abdoali.mymidia3.ui.settings

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.abdoali.mymidia3.R
import com.abdoali.mymidia3.ui.MAIN_UI
import com.abdoali.mymidia3.ui.theme.color.BLUE_COLOR
import com.abdoali.mymidia3.ui.theme.color.BROWN_Color
import com.abdoali.mymidia3.ui.theme.color.RED_COLOR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Setting(vm: SettingVM) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val scrollModifier = rememberScrollState()

    val context = LocalContext.current //context

    fun onClickRefreshActivity(language: String) {

        context.findActivity()?.runOnUiThread {
            val appLocale = LocaleListCompat.forLanguageTags(language)
            AppCompatDelegate.setApplicationLocales(appLocale)
        }
    }

    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            LargeTopAppBar(title = {
                Text(
                    text = stringResource(R.string.setting),
                    style = MaterialTheme.typography.bodyLarge
                )

            }, scrollBehavior = scrollBehavior, actions = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.setting)
                )
            })

        }) { padding ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollModifier)
        ) {

            Card(
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {

                    Text(
                        text = stringResource(R.string.theme),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth()
                        ,horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        SuggestionChip(onClick = { vm.changeTheme(Theme.Brown) }, label = {
                            Text(
                                text = stringResource(R.string.brown),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }, colors = SuggestionChipDefaults.suggestionChipColors(BROWN_Color)
                        )

                        SuggestionChip(onClick = { vm.changeTheme(Theme.Blue) }, label = {
                            Text(
                                text = stringResource(R.string.blue),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }, colors = SuggestionChipDefaults.suggestionChipColors(BLUE_COLOR)
                        )

                        SuggestionChip(onClick = { vm.changeTheme(Theme.Red) }, label = {
                            Text(
                                text = stringResource(R.string.red),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }, colors = SuggestionChipDefaults.suggestionChipColors(RED_COLOR)
                        )

//                        Button(
//                            onClick = { vm.changeTheme(Theme.Blue) },
//                            colors = ButtonDefaults.buttonColors(
//                                BLUE_COLOR
//                            )
//                        ) {
//                            Text(text = stringResource(R.string.blue))
//                        }
//                        Button(
//                            onClick = { vm.changeTheme(Theme.Red) },
//                            colors = ButtonDefaults.buttonColors(
//                                RED_COLOR
//                            )
//                        ) {
//                            Text(text = stringResource(R.string.red))
//                        }
                    }
                }
            }

            Card(
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth()

            ) {
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {

                    Text(
                        text = stringResource(R.string.language),
                        style = MaterialTheme.typography.titleLarge
                    )

                    Row (modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceAround){

                        SuggestionChip(onClick = {onClickRefreshActivity("ar") }, label = { Text(text = stringResource(R.string.arab)) })
                       SuggestionChip(onClick = {onClickRefreshActivity("en") }, label = { Text(text = stringResource(R.string.eng)) })
//                        Button(onClick = {
//                            // set app locale given the user's selected locale
//                            onClickRefreshActivity("ar")
//
//                        }) {
//                            Text(text = stringResource(R.string.arab))
//                        }
//                        Button(onClick = {
//
//                            onClickRefreshActivity("en")
//                        }) {
//                            Text(text = stringResource(R.string.eng))
//                        }
                    }
                }
            }
        }


    }
}

@Composable
@ReadOnlyComposable
fun getLocale(): java.util.Locale {
    val configuration = LocalConfiguration.current
    return ConfigurationCompat.getLocales(configuration).get(0)
        ?: LocaleListCompat.getDefault()[0]!!
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

const val SETTING_UI = "SETTING_UI_SETTING_UI"
fun NavController.navToSetting() {

    navigate(SETTING_UI) {
        popUpTo(MAIN_UI) {
            inclusive = false
        }
        launchSingleTop = true
        restoreState = true
    }

}

fun NavGraphBuilder.setting(vm: SettingVM) {
    composable(SETTING_UI) {
        Setting(vm = vm)
    }
}
