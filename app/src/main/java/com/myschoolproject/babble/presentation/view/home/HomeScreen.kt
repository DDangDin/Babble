package com.myschoolproject.babble.presentation.view.home

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myschoolproject.babble.presentation.state.HomeImagesState
import com.myschoolproject.babble.presentation.state.UserState
import com.myschoolproject.babble.presentation.view.common.TopBarWithLogo
import com.myschoolproject.babble.ui.theme.MainColorMiddle
import com.myschoolproject.babble.utils.Constants
import com.myschoolproject.babble.utils.Constants.TEST_IMAGES_LOCAL
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    userState: UserState,
    homeImagesState: HomeImagesState
) {

    LaunchedEffect(key1 = homeImagesState) {
        Log.d("HomeImagesState", "HomeImagesState Trigger!")
        if (homeImagesState.images.isNotEmpty() && !homeImagesState.loading) {
            homeImagesState.images.forEach { data ->
                Log.d("HomeImagesState", data.thumbnail)
            }
        }
        if (homeImagesState.error.isNotEmpty()) {
            Log.d("HomeImagesState", homeImagesState.error)
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
            TopBarWithLogo(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth(),
                onNavigateLikeList = {  },
                onlyLogo = false
            )

            // Include Controller
            SwipePagesScreen(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = Constants.BABBLE_BOTTOM_BAR_PADDING.dp),
                images = TEST_IMAGES_LOCAL
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        userState = UserState(loading = false),
        homeImagesState = HomeImagesState()
    )
}