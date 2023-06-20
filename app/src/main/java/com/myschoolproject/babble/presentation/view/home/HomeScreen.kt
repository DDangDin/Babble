package com.myschoolproject.babble.presentation.view.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myschoolproject.babble.presentation.state.FriendsState
import com.myschoolproject.babble.presentation.state.UserState
import com.myschoolproject.babble.presentation.view.common.TopBarWithLogo
import com.myschoolproject.babble.presentation.view.home.swipe_card.SwipePagesScreen
import com.myschoolproject.babble.ui.theme.MainColorMiddle
import com.myschoolproject.babble.ui.theme.PretendardFont
import com.myschoolproject.babble.utils.Constants

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    userState: UserState,
    friendsState: FriendsState,
    onNavigateLikeList: () -> Unit
) {

    LaunchedEffect(key1 = friendsState) {
        Log.d("HomeImagesState", "HomeImagesState Trigger!")
        if (friendsState.images.isNotEmpty() && !friendsState.loading) {
            friendsState.images.forEach { data ->
                Log.d("HomeImagesState", data.thumbnail)
            }
        }
        if (friendsState.error.isNotEmpty()) {
            Log.d("HomeImagesState", friendsState.error)
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
                    friendsState = friendsState
                )
            } else {
                // 사진 등록 뷰 필요
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "프로필 사진을 등록 해 주세요",
                    textAlign = TextAlign.Center,
                    fontFamily = PretendardFont,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp,
                    color = MainColorMiddle
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
        friendsState = FriendsState(),
        onNavigateLikeList = {}
    )
}