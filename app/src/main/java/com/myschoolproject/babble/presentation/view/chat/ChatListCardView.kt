package com.myschoolproject.babble.presentation.view.chat

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.myschoolproject.babble.R
import com.myschoolproject.babble.data.source.local.entity.ChatEntity
import com.myschoolproject.babble.data.source.remote.firebase.FriendInFirebase
import com.myschoolproject.babble.data.source.remote.firebase.getEmptyFriend
import com.myschoolproject.babble.ui.theme.PretendardFont
import com.myschoolproject.babble.ui.theme.TextDefault

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatListCardView(
    modifier: Modifier = Modifier,
    chatEntity: ChatEntity,
    onNavigateChatting: (String) -> Unit,
    deleteChatRoom: () -> Unit,
) {

    val context = LocalContext.current

    var isLongClick by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth()
            .combinedClickable(
                onLongClick = {
                    isLongClick = !isLongClick
                },
                onClick = {
                    onNavigateChatting(chatEntity.friend_email)
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                15.dp,
                alignment = Alignment.CenterHorizontally
            ),
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(58.dp)
                    .clip(CircleShape),
                model = ImageRequest.Builder(context)
                    .data(chatEntity.friend_thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = "thumbnail",
                contentScale = ContentScale.Crop
            )
            Text(
                text = chatEntity.friend_nickname,
                fontFamily = PretendardFont,
                fontWeight = FontWeight.Light,
                fontSize = 20.sp,
                color = TextDefault
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                25.dp,
                alignment = Alignment.CenterHorizontally
            ),
        ) {
            if (isLongClick) {
                IconButton(
                    modifier = Modifier.size(18.dp),
                    onClick = {
                        deleteChatRoom()
                        isLongClick = false
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete),
                        contentDescription = "highlight",
                        tint = Color(0xFFDF604D)
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun FriendCardViewPreview() {
//    ChatListCardView(
//        friend = getEmptyFriend().copy(
//            nickname = "워니",
//        ),
//    )
//}