package com.myschoolproject.babble.presentation.view.home

import android.net.Uri
import android.util.DisplayMetrics
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myschoolproject.babble.data.source.remote.firebase.DisplayFriend
import com.myschoolproject.babble.presentation.state.RandomFriendsState
import com.myschoolproject.babble.presentation.state.UserState
import com.myschoolproject.babble.presentation.view.common.TopBarWithLogo
import com.myschoolproject.babble.presentation.view.home.swipe_card.SwipePagesScreen
import com.myschoolproject.babble.ui.theme.MainColorMiddle
import com.myschoolproject.babble.utils.Constants
import com.myschoolproject.babble.utils.CustomSharedPreference

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    userState: UserState,
    randomFriendsState: RandomFriendsState,
    randomFriendsList_filtered: List<DisplayFriend>,
    onNavigateLikeList: () -> Unit,
    updateMyProfilePhoto: (Uri) -> Unit,
    alreadyCheck: Array<Boolean>,
    checkLikeAndDislike: (Int, Boolean) -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = randomFriendsState) {
        Log.d("HomeImagesState", "HomeImagesState Trigger!")
        if (randomFriendsState.images.isNotEmpty() && !randomFriendsState.loading) {
            randomFriendsState.images.forEach { data ->
                Log.d("HomeImagesState", data.thumbnail)
            }
        }
        if (randomFriendsState.error.isNotEmpty()) {
            Log.d("HomeImagesState", randomFriendsState.error)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (userState.loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 50.dp),
                strokeWidth = 3.dp,
                color = MainColorMiddle
            )
        } else {
            if (userState.userData != null && userState.userData.thumbnail.isNotEmpty()) {
                TopBarWithLogo(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth(),
                    onNavigateLikeList = onNavigateLikeList,
                    onlyLogo = false
                )

                // Include (Controller & Friend Information)
                SwipePagesScreen(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(
                            top = (Constants.BABBLE_BOTTOM_BAR_PADDING + 10).dp,
                            bottom = 10.dp
                        ),
                    randomFriendsList = randomFriendsList_filtered,
                    alreadyCheck = alreadyCheck,
                    checkLikeAndDislike = { index, like -> checkLikeAndDislike(index, like) }
                )
            } else {
                // 사진 등록 뷰 필요
                RequireProfileThumbnail(
                    modifier = Modifier.align(Alignment.Center),
                    updateMyProfilePhoto = { uri ->
                        updateMyProfilePhoto(uri)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        userState = UserState(loading = false),
        randomFriendsState = RandomFriendsState(),
        onNavigateLikeList = {},
        updateMyProfilePhoto = {},
        alreadyCheck = Array(10) { false },
        checkLikeAndDislike = { index, like -> },
        randomFriendsList_filtered = emptyList()
    )
}

@Preview(showBackground = true)
@Composable
fun RequireProfileThumbnailPreview() {
    RequireProfileThumbnail() {}
}