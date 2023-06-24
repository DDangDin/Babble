package com.myschoolproject.babble.presentation.state

import com.myschoolproject.babble.data.source.remote.firebase.DisplayFriend

data class RandomFriendsState(
    val images: List<DisplayFriend> = emptyList(),
    val loading: Boolean = false,
    val error: String = ""
)
