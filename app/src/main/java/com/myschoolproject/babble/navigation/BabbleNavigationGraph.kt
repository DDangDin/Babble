package com.myschoolproject.babble.navigation

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myschoolproject.babble.presentation.view.common.ErrorScreen
import com.myschoolproject.babble.presentation.view.login.FirstRegisterScreen
import com.myschoolproject.babble.presentation.view.login.SecondRegisterScreen
import com.myschoolproject.babble.presentation.view.onboard.OnBoardScreen
import com.myschoolproject.babble.presentation.view.onboard.SplashScreen
import com.myschoolproject.babble.presentation.viewmodel.LoginViewModel
import com.myschoolproject.babble.ui.theme.MainColorTop

@Composable
fun BabbleNavigationGraph(
    navController: NavHostController,
    onNavigate: () -> Unit,
) {

    val context = LocalContext.current
    val view = LocalView.current

    val loginViewModel: LoginViewModel = hiltViewModel()

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
                onNavigateHome = onNavigate,
                loginViewModel = loginViewModel,
                onNavigateRegister = {
                    navController.navigate(Routes.FIRST_REGISTER_SCREEN)
//                    {
//                        popUpTo(Routes.ONBOARD_SCREEN) {
//                            inclusive = true
//                        }
//                    }
                },
                onError = {
                    navController.navigate(Routes.ERROR_SCREEN) {
                        popUpTo(Routes.ONBOARD_SCREEN) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.FIRST_REGISTER_SCREEN) {
            FirstRegisterScreen(
                loginViewModel = loginViewModel,
                onNavigate = {
//                    navController.navigate(Routes.SECOND_REGISTER_SCREEN) -> 휴대폰 인증 뷰 완료 시
                    onNavigate()
                },
                onBackStack = {
                    /*TODO 백스택 되었을 때 온보딩화면에서 머물게 하기*/
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.SECOND_REGISTER_SCREEN) {
            // 휴대폰 인증 뷰
            SecondRegisterScreen()
        }

        composable(Routes.ERROR_SCREEN) {
            ErrorScreen(
                errorMsg = loginViewModel.googleSignInState.value.error,
                onBackStack = { navController.popBackStack() }
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