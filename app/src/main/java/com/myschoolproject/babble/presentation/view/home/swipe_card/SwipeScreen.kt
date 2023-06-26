package com.myschoolproject.babble.presentation.view.home.swipe_card

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.myschoolproject.babble.R
import com.myschoolproject.babble.data.source.remote.firebase.DisplayFriend
import com.myschoolproject.babble.presentation.state.RandomFriendsState
import com.myschoolproject.babble.ui.theme.BabbleGreen
import com.myschoolproject.babble.ui.theme.MainColorMiddle
import com.myschoolproject.babble.utils.CustomSharedPreference
import com.myschoolproject.babble.utils.CustomThemeEffect.shimmerEffect
import kotlinx.coroutines.launch
import java.util.Random

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipePagesScreen(
    modifier: Modifier = Modifier,
    randomFriendsList: List<DisplayFriend>,
    alreadyCheck: Array<Boolean>,
    checkLikeAndDislike: (Int, Boolean) -> Unit
) {
    val TAG = "SwipePagesScreenLog"

    val context = LocalContext.current

    val images = randomFriendsList.map { it.thumbnail}
    val nicknames = randomFriendsList.map { it.nickname }
    val ages = randomFriendsList.map { it.age }
    val cities = randomFriendsList.map { it.city }

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val matrix = remember { ColorMatrix() }
    var isLoading by remember { mutableStateOf(false) }

    Log.d(TAG, images.size.toString())
//    Box(modifier = modifier.padding(vertical = 48.dp)) {
    Box(modifier = modifier) {
        HorizontalPager(
            pageCount = images.size,
            state = pagerState,
            key = { images[it] },
            pageSize = PageSize.Fill,
        ) { index ->
            Log.d(TAG, "index: $index")
//            val index = tempIndex % images.size

            val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
            val imageSize by animateFloatAsState(
                targetValue = if (pageOffset != 0.0f) 0.85f else 1f,
                animationSpec = tween(durationMillis = 300)
            )

            val iconImageSize by animateFloatAsState(
                targetValue = if (pageOffset != 0.0f) 0.85f else 1f,
                animationSpec = tween(durationMillis = 300)
            )
            val iconOffsetY by animateDpAsState(
                targetValue = if (pageOffset != 0.0f) -(54).dp else -(16).dp,
                animationSpec = tween(durationMillis = 300)
            )

            LaunchedEffect(key1 = imageSize) {
                if (pageOffset != 0.0f) {
                    matrix.setToSaturation(0f)
                } else {
                    matrix.setToSaturation(1f)
                }
            }

            Box() {
                // thumbnail
                AsyncImage(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .graphicsLayer {
                            scaleX = imageSize
                            scaleY = imageSize
                            clip = true
                            shape = RoundedCornerShape(16.dp)
                        }
                        .shimmerEffect(isLoading),
                    model = ImageRequest.Builder(context)
                        .data(images[index])
                        .build(),
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.colorMatrix(matrix),
                    onLoading = { isLoading = true },
                    onSuccess = { isLoading = false }
                )

                // Friend Info
                FriendInfoBar(
                    modifier = Modifier
                        .offset(y = iconOffsetY + -(150).dp)
                        .fillMaxWidth()
                        .padding(start = 25.dp, end = 20.dp)
                        .align(Alignment.BottomCenter)
                        .graphicsLayer {
                            scaleX = iconImageSize
                            scaleY = iconImageSize
                        },
                    nickname = nicknames[index],
                    age = ages[index],
                    city = cities[index]
                )

                // Controller (icons)
                if (!alreadyCheck[index]) {
                    Box(
                        modifier = Modifier
                            .offset(y = iconOffsetY)
                            .fillMaxWidth(0.5f)
                            .clip(RoundedCornerShape(100))
                            .padding(8.dp)
                            .align(Alignment.BottomCenter)
                    ) {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    // 싫어요를 눌렀으니 추천목록에서 삭제
                                    pagerState.animateScrollToPage(
                                        pagerState.currentPage + 1
                                    )
                                }
                                Log.d(
                                    "RandomFriend_Dislike",
                                    images[index]
                                )
                                checkLikeAndDislike(index, false)
                            },
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(65.dp)
                                    .graphicsLayer {
                                        scaleX = iconImageSize
                                        scaleY = iconImageSize
                                    },
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_dislike),
                                contentDescription = "dislike",
                                tint = MainColorMiddle
                            )
                        }
                        IconButton(
                            onClick = {
                                // 좋아요를 눌렀으니 LikeList에 추가
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        pagerState.currentPage + 1
                                    )
                                }
                                Log.d(
                                    "RandomFriend_Like",
                                    images[index]
                                )
                                checkLikeAndDislike(index, true)
                            },
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(65.dp)
                                    .graphicsLayer {
                                        scaleX = iconImageSize
                                        scaleY = iconImageSize
                                    },
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_like),
                                contentDescription = "like",
                                tint = BabbleGreen
                            )
                        }
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun SwipePagesScreenPreview() {
//    SwipePagesScreen(images = TEST_IMAGES_LOCAL)
}