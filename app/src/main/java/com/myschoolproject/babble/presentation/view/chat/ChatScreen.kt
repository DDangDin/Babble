package com.myschoolproject.babble.presentation.view.chat

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.myschoolproject.babble.data.source.local.entity.ChatEntity
import com.myschoolproject.babble.presentation.state.ChatRoomListState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    chatRoomListState: ChatRoomListState,
    onNavigateChatting: (String) -> Unit,
    checkChatRoom: () -> Unit,
    deleteChatRoom: (ChatEntity) -> Unit
) {

    LaunchedEffect(key1 = true) {
        Log.d("ChatViewModel", "trigger!! (chat screen)")
        checkChatRoom()
    }

    val chatRoomList = chatRoomListState.chatRoomList

//    val chatRoomList_filterd = if (chatRoomList.isNotEmpty()) {
//        chatRoomList.filter { it.friend_nickname.isNotEmpty() }
//    } else {
//        emptyList()
//    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ChatTopBar(
            modifier = Modifier.fillMaxWidth(),
            title = "채팅 목록"
        )
        if (!chatRoomListState.loading) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    25.dp,
                    alignment = Alignment.CenterVertically
                ),
            ) {
                items(chatRoomList) { chatEntity ->
                    ChatListCardView(
                        modifier = Modifier,
                        chatEntity = chatEntity,
                        onNavigateChatting = { friend_email ->
                            onNavigateChatting(friend_email)
                        },
                        deleteChatRoom = { deleteChatRoom(chatEntity) }
                    )
                }
            }
        } else {
            CircularProgressIndicator(color = Color.Transparent)
        }
    }
}

//@Preview
//@Composable
//fun ChatScreenPreview() {
//    ChatScreen()
//}