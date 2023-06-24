package com.myschoolproject.babble.presentation.state

import com.myschoolproject.babble.data.source.remote.firebase.FriendInFirebase

data class FriendsListState(
    val friends: List<FriendInFirebase> = emptyList(),
    val loading: Boolean = false,
    val error: String = ""
)
