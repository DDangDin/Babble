package com.myschoolproject.babble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.myschoolproject.babble.presentation.view.profile.setting.SettingScreen
import com.myschoolproject.babble.presentation.viewmodel.SettingViewModel
import com.myschoolproject.babble.ui.theme.BabbleTheme
import com.myschoolproject.babble.utils.CustomSharedPreference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@AndroidEntryPoint
class SettingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BabbleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val context = LocalContext.current
                    val settingViewModel: SettingViewModel = hiltViewModel()
                    val coroutineScope = rememberCoroutineScope()

                    SettingScreen(
                        modifier = Modifier.fillMaxSize(),
                        onBackStack = { finish() },
                        onLogout = {
                            coroutineScope.launch {
                                CustomSharedPreference(context).deleteUserPrefs("email")
                                delay(500L)
                                settingViewModel.googleLogout()
                                finishAffinity()
                                System.runFinalization()
                                exitProcess(0)
                            }
                        }
                    )
                }
            }
        }
    }
}