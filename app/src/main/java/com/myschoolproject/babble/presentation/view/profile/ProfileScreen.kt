package com.myschoolproject.babble.presentation.view.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.myschoolproject.babble.R
import com.myschoolproject.babble.data.source.remote.response.dto.user.getEmptyUser
import com.myschoolproject.babble.presentation.state.UserState
import com.myschoolproject.babble.presentation.view.common.TopBarWithLogo
import com.myschoolproject.babble.ui.theme.PretendardFont
import com.myschoolproject.babble.ui.theme.TextDefault
import com.myschoolproject.babble.utils.CustomSharedPreference

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userState: UserState,
    updateMyProfilePhoto: (Uri) -> Unit
) {

    val context = LocalContext.current
    val user = userState.userData

    // 프로필사진을 변경하면 userPhoto가 바로 변하지 않음
    // -> state 값으로 변경해야 할듯
    val userPhoto = CustomSharedPreference(context).getUserPrefs("user_photo")

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                updateMyProfilePhoto(uri)
            }
        }
    )

    Box(modifier = modifier.fillMaxSize()) {
        TopBarWithLogo(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
            onlyLogo = true
        )
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp, alignment = Alignment.CenterVertically)
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(10.dp)
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.LightGray, CircleShape),
                model = if (userPhoto.isEmpty()) {
                    Color.LightGray
                } else {
                    ImageRequest.Builder(context)
                        .data(userPhoto)
                        .crossfade(true)
                        .build()
                },
                contentDescription = "thumbnail",
                contentScale = ContentScale.Crop
            )
            Text(
                text = user?.nickname ?: "이름",
                fontFamily = PretendardFont,
                fontWeight = FontWeight.Medium,
                fontSize = 27.sp,
                color = TextDefault
            )
            Text(
                text = "${user?.city ?: "?"}, ${user?.age ?: "?"}",
                fontFamily = PretendardFont,
                fontWeight = FontWeight.Light,
                fontSize = 18.sp,
                color = TextDefault
            )

            Column(
                modifier = Modifier.padding(top = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    15.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                ProfileCustomButton(
                    imageVector = R.drawable.ic_setting_friends_list,
                    text = "친구목록",
                    onClick = { }
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        100.dp,
                        alignment = Alignment.CenterHorizontally
                    )
                ) {
                    ProfileCustomButton(
                        imageVector = R.drawable.ic_setting_edit_profile,
                        text = "프로필 수정",
                        onClick = {
                            singlePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    )
                    ProfileCustomButton(
                        imageVector = R.drawable.ic_setting,
                        text = "설정",
                        onClick = { }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        userState = UserState(
            userData = getEmptyUser().copy(
                nickname = "홍길동",
                city = "천안시",
                age = 23
            )
        ),
        updateMyProfilePhoto = {}
    )
}