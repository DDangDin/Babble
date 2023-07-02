package com.myschoolproject.babble.presentation.state

import com.myschoolproject.babble.data.source.remote.firebase.Chat

data class ChatState(
    var chatList: List<Chat> = emptyList(),
    val loading: Boolean = false,
    val error: String = ""
)
