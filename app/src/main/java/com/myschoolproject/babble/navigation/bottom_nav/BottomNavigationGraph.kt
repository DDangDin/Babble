package com.myschoolproject.babble.navigation.bottom_nav

import android.util.Log
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
import com.myschoolproject.babble.presentation.view.home.like_list.LikeListScreen
import com.myschoolproject.babble.presentation.view.profile.ProfileScreen
import com.myschoolproject.babble.presentation.viewmodel.ChatViewModel
import com.myschoolproject.babble.presentation.viewmodel.HomeViewModel
import com.myschoolproject.babble.utils.CustomSharedPreference

@Composable
fun BottomNavigationGraph(
    navController: NavHostController,
    onNavigateLikeList: () -> Unit,
    onNavigateFriendsList: () -> Unit,
    onNavigateChatting: (String) -> Unit
) {
    /*TODO 나중에 스크린 전환 간 애니메이션 없애기*/

    val context = LocalContext.current

    val homeViewModel: HomeViewModel = hiltViewModel()
    val chatViewModel: ChatViewModel = hiltViewModel()

    val userState = homeViewModel.userState.value
    val randomFriendsState = homeViewModel.randomFriendsState.value

    val my_email = CustomSharedPreference(context).getUserPrefs("email")
    val randomFriendsList_filtered = randomFriendsState.images.filter { it.id_email != my_email }

    LaunchedEffect(userState) {
        if (userState.userData == null) {
            val email = CustomSharedPreference(context).getUserPrefs("email")
            homeViewModel.loginWithEmail(email)
        } else {
            if (!CustomSharedPreference(context).isContain("user_photo") &&
                userState.userData.thumbnail.isNotEmpty()
            ) {
                // 이미 사진이 등록 되어 있으므로 SharedPreference에 저장
                Log.d("userPhoto", "alreadyPhoto: ${userState.userData.thumbnail}")
                CustomSharedPreference(context).setUserPrefs(
                    "user_photo",
                    userState.userData.thumbnail
                )
            }
            if (userState.userData.thumbnail != CustomSharedPreference(context).getUserPrefs("user_photo")) {
                homeViewModel.getProfilePhoto(my_email)
            }
            CustomSharedPreference(context).setUserPrefs("user_data_email", userState.userData.email)
            CustomSharedPreference(context).setUserPrefs("user_data_nickname", userState.userData.nickname)
            CustomSharedPreference(context).setUserPrefs("user_data_age", userState.userData.age.toString())
            CustomSharedPreference(context).setUserPrefs("user_data_city", userState.userData.city)
            CustomSharedPreference(context).setUserPrefs("user_data_thumbnail", userState.userData.thumbnail)
        }
    }

    LaunchedEffect(key1 = homeViewModel.thumbnailState.value) {
        CustomSharedPreference(context).setUserPrefs(
            "user_photo",
            homeViewModel.thumbnailState.value.toString()
        )
    }

    NavHost(
        navController = navController,
        startDestination = Routes.HOME_SCREEN,
    ) {

        // Nav Items (start)
        composable(route = Routes.HOME_SCREEN) {
            HomeScreen(
                userState = userState,
                randomFriendsState = randomFriendsState,
                randomFriendsList_filtered = randomFriendsList_filtered,
                onNavigateLikeList = onNavigateLikeList,
                updateMyProfilePhoto = { uri ->
                    homeViewModel.updateMyProfilePhoto(userState.userData?.email ?: "", uri)
                    CustomSharedPreference(context).setUserPrefs("user_photo", uri.toString())
                    navigateSaveState(
                        navController,
                        Routes.PROFILE_SCREEN
                    )
                },
                alreadyCheck = homeViewModel.alreadyCheck.value,
                checkLikeAndDislike = { index, like ->
                    homeViewModel.checkLikeAndDislike(index, like, randomFriendsList_filtered[index])
                }
            )
        }

        composable(route = Routes.CHAT_SCREEN) {
            ChatScreen(
                chatRoom = chatViewModel.chatRoomState.value,
                onNavigateChatting = { friend_email ->
                    onNavigateChatting(friend_email)
                },
                getChatRoom = { chatViewModel.getChatRoom() }
            )
        }

        composable(Routes.PROFILE_SCREEN) {
            ProfileScreen(
                userState = userState,
                updateMyProfilePhoto = { uri ->
                    CustomSharedPreference(context).setUserPrefs("user_photo", uri.toString())
                    homeViewModel.updateMyProfilePhoto(userState.userData?.email ?: "", uri)
                },
                onNavigateFriendsList = {
                    onNavigateFriendsList()
                    navigateSaveState(
                        navController,
                        Routes.CHAT_SCREEN
                    )
                }
            )
        }
        // Nav Items (end)

    }
}

fun navigateSaveState(
    navController: NavHostController,
    route: String
) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let {
            // 첫번째 화면만 스택에 쌓이게 -> 백버튼 클릭 시 첫번째 화면으로 감
            popUpTo(it) { saveState = true }
        }
        launchSingleTop = true  // 화면 인스턴스 하나만 만들어지게
        restoreState = true     // 버튼을 재클릭했을 때 이전 상태가 남아있게
    }
}