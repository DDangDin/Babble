package com.myschoolproject.babble.presentation.state

import com.myschoolproject.babble.data.source.remote.response.dto.user.DisplayFriend

data class FirestoreState(
    val data: DisplayFriend? = null,
    val loading: Boolean = false,
    val error: String = "",
)
