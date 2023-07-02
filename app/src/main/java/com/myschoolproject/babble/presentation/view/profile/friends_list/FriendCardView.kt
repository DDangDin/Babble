package com.myschoolproject.babble.presentation.view.profile.friends_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import com.myschoolproject.babble.data.source.remote.firebase.FriendInFirebase
import com.myschoolproject.babble.data.source.remote.firebase.getEmptyFriend
import com.myschoolproject.babble.ui.theme.PretendardFont
import com.myschoolproject.babble.ui.theme.TextDefault
import com.myschoolproject.babble.utils.CustomThemeEffect

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FriendCardView(
    modifier: Modifier = Modifier,
    friend: FriendInFirebase,
    onDelete: () -> Unit,
    isRequest: Boolean,
    deleteForRequest: () -> Unit,
    addForRequest: () -> Unit,
    createChatRoom: () -> Unit,
    onNavigateChatting: () -> Unit
) {

    val context = LocalContext.current

    var isLongClick by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onLongClick = {
                    if (!isRequest) {
                        isLongClick = true
                    }
                },
                onClick = {
                    if (!isRequest) {
                        if (isLongClick) {
                            isLongClick = false
                        }
                        createChatRoom()
                        onNavigateChatting()
                    }
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
                    .data(friend.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = "thumbnail",
                contentScale = ContentScale.Crop
            )
            Text(
                text = if (isRequest) "${friend.nickname}(친구요청)" else friend.nickname,
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
            if (isRequest) {
                CompositionLocalProvider(LocalRippleTheme provides CustomThemeEffect.NoRippleTheme) {
                    IconButton(onClick = {
                        deleteForRequest()
                    }) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete),
                            contentDescription = "block",
                            tint = Color(0xFFF86A6A)
                        )
                    }
                    IconButton(onClick = {
                        addForRequest()
                    }) {
                        Icon(
                            modifier = Modifier.size(35.dp),
                            imageVector = Icons.Default.Check,
                            contentDescription = "block",
                            tint = Color(0xFF87E786)
                        )
                    }
                }
            } else {
                if (isLongClick) {
                    CompositionLocalProvider(LocalRippleTheme provides CustomThemeEffect.NoRippleTheme) {
                        IconButton(onClick = {
                            onDelete()
                            isLongClick = false
                        }) {
                            Icon(
                                modifier = Modifier.size(18.dp),
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_block),
                                contentDescription = "block",
                                tint = Color(0xFFF86A6A)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FriendCardViewPreview() {
    FriendCardView(
        friend = getEmptyFriend().copy(
            nickname = "워니",
        ),
        onDelete = {},
        isRequest = true,
        deleteForRequest = {},
        addForRequest = {},
        createChatRoom = {},
        onNavigateChatting = {}
    )
}