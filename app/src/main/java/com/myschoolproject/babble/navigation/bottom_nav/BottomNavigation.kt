package com.myschoolproject.babble.navigation.bottom_nav

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.myschoolproject.babble.R
import com.myschoolproject.babble.navigation.Routes
import com.myschoolproject.babble.utils.CustomNoRipple

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    androidx.compose.material.BottomNavigation(
        backgroundColor = Color(
            red = 245,
            green = 246,
            blue = 248
        ),
        modifier = modifier.fillMaxWidth(),
    ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        this.MainBottomNavigationItem(
            modifier = Modifier.offset(x = 20.dp),  // -> test 패키지 내에서 확인해봄
            selected = currentRoute == Routes.HOME_SCREEN,
            onClick = { },
            drawable = R.drawable.ic_nav_swipe,
            route = Routes.HOME_SCREEN,
            navController = navController,
            selectedColor = R.color.main_color_middle,
            unselectedColor = R.color.main_color_middle
        )
        this.MainBottomNavigationItem(
            selected = currentRoute == Routes.CHAT_SCREEN,
            onClick = {  },
            drawable = R.drawable.ic_nav_chat,
            route = Routes.CHAT_SCREEN,
            navController = navController,
            selectedColor = R.color.bottom_nav_click,
            unselectedColor = R.color.bottom_nav_unclick
        )
        this.MainBottomNavigationItem(
            modifier = Modifier.offset(x = (-20).dp),
            selected = currentRoute == Routes.PROFILE_SCREEN,
            onClick = { },
            drawable = R.drawable.ic_nav_profile,
            route = Routes.PROFILE_SCREEN,
            navController = navController,
            selectedColor = R.color.bottom_nav_click,
            unselectedColor = R.color.bottom_nav_unclick
        )
    }
}

@Composable
fun RowScope.MainBottomNavigationItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    @DrawableRes drawable: Int,
    navController: NavHostController,
    route: String,
    @ColorRes selectedColor: Int,
    @ColorRes unselectedColor: Int,
    enable: Boolean = true
) {
    CompositionLocalProvider(LocalRippleTheme provides CustomNoRipple.NoRippleTheme) {
        BottomNavigationItem(
            modifier = modifier,
            selected = selected,
            onClick = {
                navController.navigate(route) {
                    navController.graph.startDestinationRoute?.let {
                        // 첫번째 화면만 스택에 쌓이게 -> 백버튼 클릭 시 첫번째 화면으로 감
                        popUpTo(it) { saveState = true }
                    }
                    launchSingleTop = true  // 화면 인스턴스 하나만 만들어지게
                    restoreState = true     // 버튼을 재클릭했을 때 이전 상태가 남아있게
                }
                onClick()
            },
            icon = {
                Icon(
                    modifier = Modifier.size((24.5).dp),
                    imageVector = ImageVector.vectorResource(id = drawable),
                    contentDescription = null
                )
            },
            interactionSource = remember { MutableInteractionSource() },
            selectedContentColor = colorResource(id = selectedColor),
            unselectedContentColor = colorResource(id = unselectedColor),
            enabled = enable
        )
    }
}