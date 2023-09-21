package com.abdoali.mymidia3.ui.settings


import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
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
import org.intellij.lang.annotations.Language

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Setting(vm: SettingVM) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val context = LocalContext.current //context

    fun onClickRefreshActivity(language: String) {

        context.findActivity()?.runOnUiThread {
            val appLocale = LocaleListCompat.forLanguageTags(language)
            AppCompatDelegate.setApplicationLocales(appLocale)
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection) ,

        topBar = {
            LargeTopAppBar(title = {
                Text(text = stringResource(R.string.setting) , style = MaterialTheme.typography.titleLarge)
            } , scrollBehavior = scrollBehavior
            )


        }
    ) { padding ->


        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .border(1.dp , MaterialTheme.colorScheme.onBackground)
        ) {
            item {
                Text(text = "theme " , style = MaterialTheme.typography.titleLarge)
            }
            item {
                Row(
                    Modifier
                        .border(
                            1.dp ,
                            MaterialTheme.colorScheme.onBackground ,
                            MaterialTheme.shapes.large
                        )
                        .fillMaxWidth()


                ) {
                    Button(onClick = { vm.changeTheme(Theme.Brown) } ,
                        colors = ButtonDefaults.buttonColors(
                            BROWN_Color
                        )) {

                        Text(text = stringResource(R.string.brown))
                    }
                    Button(
                        onClick = { vm.changeTheme(Theme.Blue) } ,
                        colors = ButtonDefaults.buttonColors(
                            BLUE_COLOR
                        )
                    ) {
                        Text(text = stringResource(R.string.blue))
                    }
                    Button(onClick = { vm.changeTheme(Theme.Red) } ,
                        colors = ButtonDefaults.buttonColors(
                            RED_COLOR
                        )) {
                        Text(text = stringResource(R.string.red))
                    }
                }
            }
            item {
                Row {


                    Button(onClick = {
                        // set app locale given the user's selected locale
                        onClickRefreshActivity("ar")

                    }) {
                        Text(text = stringResource(R.string.arab))
                    }
                    Button(onClick = {

                    onClickRefreshActivity("en")
                    }) {
                        Text(text = stringResource(R.string.eng))
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
    return ConfigurationCompat.getLocales(configuration).get(0) ?: LocaleListCompat.getDefault()[0]!!
}

fun Context.findActivity() : Activity? = when(this){
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
