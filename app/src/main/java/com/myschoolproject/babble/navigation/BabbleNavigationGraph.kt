package com.myschoolproject.babble.navigation

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myschoolproject.babble.presentation.view.onboard.OnBoardScreen
import com.myschoolproject.babble.presentation.view.onboard.SplashScreen
import com.myschoolproject.babble.ui.theme.MainColorTop

@Composable
fun BabbleNavigationGraph(
    navController: NavHostController,
    onNavigate: () -> Unit
) {

    val context = LocalContext.current
    val view = LocalView.current

    NavHost(navController = navController, startDestination = Routes.ONBOARD_SCREEN) {

        composable(Routes.SPLASH_SCREEN) {
            changeStatusBarColor(context, view)
            SplashScreen()
        }

        composable(Routes.ONBOARD_SCREEN) {
            changeStatusBarColor(context, view)
            // 로그인 정보 확인
            // O -> HomeScreen
            // X -> LoginScreen
            OnBoardScreen(
                onNavigate = onNavigate
            )
        }
    }
}

fun changeStatusBarColor(
    context: Context,
    view: View
) {
    // change statusBar & Icon color in only OnBoardingScreen
    val window = (context as Activity).window
    window.statusBarColor = MainColorTop.toArgb()
    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
}