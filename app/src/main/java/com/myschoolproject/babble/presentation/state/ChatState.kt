package com.myschoolproject.babble.presentation.state

import com.myschoolproject.babble.data.source.remote.firebase.Chat

data class ChatState(
    var chatList: ArrayList<Chat> = arrayListOf(),
    val loading: Boolean = false
)
