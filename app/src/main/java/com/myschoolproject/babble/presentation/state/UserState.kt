package com.myschoolproject.babble.presentation.state

import com.myschoolproject.babble.data.source.remote.response.dto.user.User

data class UserState(
    val userData: User? = null,
    val loading: Boolean = false,
    val error: String = ""
)
