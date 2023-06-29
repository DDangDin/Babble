package com.myschoolproject.babble.presentation.state

import com.myschoolproject.babble.data.source.local.entity.ChatEntity

data class ChatRoomListState(
    val chatRoomList: List<ChatEntity> = emptyList(),
    val loading: Boolean = false,
    val error: String = ""
)
