package com.myschoolproject.babble

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.myschoolproject.babble.navigation.bottom_nav.BottomNavigation
import com.myschoolproject.babble.navigation.bottom_nav.BottomNavigationGraph
import com.myschoolproject.babble.presentation.viewmodel.ChatViewModel
import com.myschoolproject.babble.ui.theme.BabbleTheme
import com.myschoolproject.babble.utils.Constants
import com.myschoolproject.babble.utils.CustomSharedPreference
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()

            BabbleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Scaffold(
                        bottomBar = {
                            BottomNavigation(
                                modifier = Modifier.height(Constants.BABBLE_BOTTOM_BAR_PADDING.dp),
                                navController = navController,
                            )
                        }
                    ) {
                        Box(Modifier.padding(it)) {
                            BottomNavigationGraph(
                                navController = navController,
                                onNavigateLikeList = {
                                    val intent = Intent(this@HomeActivity, LikeListActivity::class.java)
                                    startActivity(intent)
                                },
                                onNavigateFriendsList = {
                                    val intent = Intent(this@HomeActivity, FriendsListActivity::class.java)
                                    startActivity(intent)
                                },
                                onNavigateChatting = { friend_email ->
                                    val intent = Intent(this@HomeActivity, ChatActivity::class.java)
                                    intent.putExtra("friend_email", friend_email)
                                    startActivity(intent)
                                },
                                onNavigateSettings = {
                                    val intent = Intent(this@HomeActivity, SettingActivity::class.java)
                                    startActivity(intent)
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}