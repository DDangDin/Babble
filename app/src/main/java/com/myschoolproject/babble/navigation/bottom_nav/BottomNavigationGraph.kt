package com.myschoolproject.babble.navigation.bottom_nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myschoolproject.babble.navigation.Routes
import com.myschoolproject.babble.presentation.view.chat.ChatScreen
import com.myschoolproject.babble.presentation.view.home.HomeScreen
import com.myschoolproject.babble.presentation.view.profile.ProfileScreen
import com.myschoolproject.babble.presentation.viewmodel.MainViewModel

@Composable
fun BottomNavigationGraph(
    navController: NavHostController,
) {

    /*TODO 나중에 스크린 전환 간 애니메이션 없애기*/

    val mainViewModel: MainViewModel = hiltViewModel()
    val userState = mainViewModel.userState.value

    NavHost(
        navController = navController,
        startDestination = Routes.HOME_SCREEN,
    ) {

        composable(route = Routes.HOME_SCREEN) {
            HomeScreen(
                userState = userState
            )
        }

        composable(route = Routes.CHAT_SCREEN) {
            ChatScreen()
        }

        composable(Routes.PROFILE_SCREEN) {
            ProfileScreen()
        }
    }
}
