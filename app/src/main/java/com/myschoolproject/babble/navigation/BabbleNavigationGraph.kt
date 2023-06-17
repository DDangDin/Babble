package com.myschoolproject.babble.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myschoolproject.babble.presentation.view.onboard.OnBoardScreen
import com.myschoolproject.babble.presentation.view.onboard.SplashScreen

@Composable
fun BabbleNavigationGraph(
    navController: NavHostController,
) {

    NavHost(navController = navController, startDestination = Routes.SPLASH_SCREEN) {

        composable(Routes.SPLASH_SCREEN) {
            SplashScreen()
        }

        composable(Routes.ONBOARD_SCREEN) {
            // 로그인 정보 확인
            // O -> HomeScreen
            // X -> LoginScreen
            OnBoardScreen()
        }
    }
}