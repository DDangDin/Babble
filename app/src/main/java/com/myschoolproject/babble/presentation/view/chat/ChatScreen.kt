package com.myschoolproject.babble.presentation.view.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myschoolproject.babble.data.source.local.entity.ChatEntity

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    chatRoom: List<ChatEntity>,
    onNavigateChatting: (String) -> Unit,
    getChatRoom: () -> Unit
) {

    LaunchedEffect(key1 = chatRoom) {
        getChatRoom()
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ChatTopBar(
            modifier = Modifier.fillMaxWidth(),
            title = "채팅 목록"
        )
        if (chatRoom.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    25.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                items(chatRoom) { chatEntity ->
                    ChatListCardView(
                        modifier = Modifier.clickable {
                            onNavigateChatting(chatEntity.friend_email)
                        },
                        chatEntity = chatEntity
                    )
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun ChatScreenPreview() {
//    ChatScreen()
//}