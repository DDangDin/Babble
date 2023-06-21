package com.myschoolproject.babble.presentation.view.profile

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.myschoolproject.babble.R
import com.myschoolproject.babble.data.source.remote.response.dto.user.User
import com.myschoolproject.babble.data.source.remote.response.dto.user.getEmptyUser
import com.myschoolproject.babble.presentation.state.UserState
import com.myschoolproject.babble.presentation.view.common.TopBarWithLogo
import com.myschoolproject.babble.ui.theme.PretendardFont
import com.myschoolproject.babble.ui.theme.TextDefault

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    image: Uri?,
    userState: UserState
) {

    val user = userState.userData

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
                model = image ?: Color.LightGray,
                contentDescription = "thumbnail"
            )
            Text(
                text = user?.nickname ?: "이름",
                fontFamily = PretendardFont,
                fontWeight = FontWeight.Medium,
                fontSize = 30.sp,
                color = TextDefault
            )
            Text(
                text = "${user?.city ?: "?"}, ${user?.age ?: "?"}",
                fontFamily = PretendardFont,
                fontWeight = FontWeight.Light,
                fontSize = 20.sp,
                color = TextDefault
            )


        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        image = null,
        userState = UserState(
            userData = getEmptyUser().copy(
                nickname = "홍길동",
                city = "천안시",
                age = 23
            )
        )
    )
}