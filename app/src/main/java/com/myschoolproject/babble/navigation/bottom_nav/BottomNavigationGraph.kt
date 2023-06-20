package com.myschoolproject.babble.navigation.bottom_nav

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myschoolproject.babble.navigation.Routes
import com.myschoolproject.babble.presentation.view.chat.ChatScreen
import com.myschoolproject.babble.presentation.view.home.HomeScreen
import com.myschoolproject.babble.presentation.view.home.LikeListScreen
import com.myschoolproject.babble.presentation.view.profile.ProfileScreen
import com.myschoolproject.babble.presentation.viewmodel.HomeViewModel
import com.myschoolproject.babble.utils.CustomSharedPreference

@Composable
fun BottomNavigationGraph(
    navController: NavHostController,
) {
    /*TODO 나중에 스크린 전환 간 애니메이션 없애기*/

    val context = LocalContext.current

    val homeViewModel: HomeViewModel = hiltViewModel()

    val userState = homeViewModel.userState.value
    val friendsState = homeViewModel.friendsState.value

    LaunchedEffect(Unit) {
        if (userState.userData == null) {
            val email = CustomSharedPreference(context).getUserPrefs("email")
            homeViewModel.loginWithEmail(email)
        }
    }

    NavHost(
        navController = navController,
        startDestination = Routes.HOME_SCREEN,
    ) {

        composable(route = Routes.HOME_SCREEN) {
            HomeScreen(
                userState = userState,
                friendsState = friendsState,
                onNavigateLikeList = {
                    navController.navigate(Routes.LIKE_LIST_SCREEN) {
                        navController.graph.startDestinationRoute?.let {
                            // 첫번째 화면만 스택에 쌓이게 -> 백버튼 클릭 시 첫번째 화면으로 감
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true  // 화면 인스턴스 하나만 만들어지게
                        restoreState = true     // 버튼을 재클릭했을 때 이전 상태가 남아있게
                    }
                }
            )
        }

        composable(route = Routes.CHAT_SCREEN) {
            ChatScreen()
        }

        composable(Routes.PROFILE_SCREEN) {
            ProfileScreen()
        }

        composable(Routes.LIKE_LIST_SCREEN) {
            LikeListScreen(Modifier.background(Color.Black))
        }
    }
}
