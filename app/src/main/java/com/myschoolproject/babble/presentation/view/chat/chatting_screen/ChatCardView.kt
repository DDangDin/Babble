package com.myschoolproject.babble.presentation.view.chat.chatting_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.myschoolproject.babble.data.source.local.entity.ChatEntity
import com.myschoolproject.babble.data.source.remote.firebase.Chat
import com.myschoolproject.babble.ui.theme.PretendardFont
import com.myschoolproject.babble.ui.theme.TextBig

@Composable
fun ChatCardView(
    modifier: Modifier = Modifier,
    isMyChat: Boolean,
    chat: Chat,
    friendData: ChatEntity,
) {

    val context = LocalContext.current
    val textColor = if (isMyChat) TextBig else Color.White
    val backgroundColor = if (isMyChat) Color(0xFFF6F6F6) else Color(0xFF4992ED)
    val horizontalArrangement = if (isMyChat) Arrangement.End else Arrangement.Start

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                10.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            if (!isMyChat) {
                AsyncImage(
                    modifier = Modifier
                        .size(37.dp)
                        .clip(CircleShape),
                    model = if (friendData.friend_thumbnail.isEmpty()) {
                        Color.LightGray
                    } else {
                        ImageRequest.Builder(context)
                            .data(friendData.friend_thumbnail)
                            .crossfade(true)
                            .build()
                    },
                    contentDescription = "thumbnail",
                    contentScale = ContentScale.Crop
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(23.dp))
                    .background(backgroundColor)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(12.dp),
                    text = chat.message,
                    fontFamily = PretendardFont,
                    fontWeight = FontWeight.Light,
                    fontSize = 17.sp,
                    color = textColor,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatCardViewPreview() {
    ChatCardView(
        modifier = Modifier.fillMaxWidth(),
        isMyChat = false,
        friendData = ChatEntity("asd", "서민아", "123"),
        chat = Chat("","오늘 기분도 안좋은데 한잔?")
    )
}

@Preview(showBackground = true)
@Composable
fun ChatCardViewPreview2() {
    ChatCardView(
        modifier = Modifier.fillMaxWidth(),
        isMyChat = true,
        friendData = ChatEntity("asd", "서민아", "123"),
        chat = Chat("", "혼자마시기 좀 그랬는데 좋지!")
    )
}