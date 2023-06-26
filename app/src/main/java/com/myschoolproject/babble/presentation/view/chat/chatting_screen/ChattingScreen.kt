package com.myschoolproject.babble.presentation.view.chat.chatting_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myschoolproject.babble.data.source.local.entity.ChatEntity
import com.myschoolproject.babble.data.source.remote.firebase.Chat
import com.myschoolproject.babble.ui.theme.SpacerCustomColor
import com.myschoolproject.babble.utils.CustomSharedPreference
import kotlinx.coroutines.launch

@Composable
fun ChattingScreen(
    modifier: Modifier = Modifier,
    onBackStack: () -> Unit,
    chatList: List<Chat>,
    friendData: ChatEntity,
    inputMessage: String,
    inputMessageChanged: (String) -> Unit,
    onSend: () -> Unit,
    onExitRoom: () -> Unit
) { // ChatActivity 에서 실행

    val context = LocalContext.current

    val my_email = CustomSharedPreference(context).getUserPrefs("email")

    val scrollState = rememberLazyListState(
        if(CustomSharedPreference(context).getUserPrefs("chat_scroll_value").isEmpty()) {
            0
        } else {
            CustomSharedPreference(context).getUserPrefs("chat_scroll_value").toInt()
        }
    )
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(9f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ChattingScreenTopBar(
                modifier = Modifier.fillMaxWidth(),
                onBackStack = onBackStack,
                nickname = friendData.friend_nickname,
                onSiren = {},
                onExitRoom = onExitRoom
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(SpacerCustomColor)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    17.dp,
                    alignment = Alignment.CenterVertically
                ),
                state = scrollState
            ) {
                items(chatList) { chat ->
                    if (chat.message.isNotEmpty()) {
                        val isMyChat = chat.email == my_email.split("@")[0]
                        ChatCardView(
                            modifier = Modifier
                                .fillMaxWidth(),
                            isMyChat = isMyChat,
                            chat = chat,
                            friendData = friendData
                        )
                    }
                }
            }
        }
        ChattingScreenBottomBar(
            modifier = modifier
                .weight(.8f)
                .fillMaxWidth(),
            inputMessage = inputMessage,
            inputMessageChanged = { inputMessageChanged(it) },
            onSend = {
                onSend()
                coroutineScope.launch {
                    scrollState.animateScrollToItem(chatList.size - 1)
                    CustomSharedPreference(context).setUserPrefs("chat_scroll_value", (chatList.size - 1).toString())
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ChattingScreenPreview() {
    ChattingScreen(
        onBackStack = { }, friendData = ChatEntity(
            "123@test.com",
            "서민아",
            "123"
        ),
        inputMessage = "",
        inputMessageChanged = {},
        chatList = emptyList(),
        onSend = {},
        onExitRoom = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ChattingScreenTopBarPreview() {
    ChattingScreenTopBar(
        modifier = Modifier.fillMaxWidth(),
        onBackStack = {},
        nickname = "서민아",
        onSiren = {},
        onExitRoom = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ChattingScreenBottomBarPreview() {
    ChattingScreenBottomBar(
        modifier = Modifier.fillMaxWidth(),
        inputMessage = "",
        inputMessageChanged = {},
        onSend = {}
    )
}
        