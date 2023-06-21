package com.myschoolproject.babble.presentation.state

import com.myschoolproject.babble.data.source.remote.response.dto.CheckAccount

data class CheckAccountState(
    val data: CheckAccount? = null,
    val loading: Boolean = false,
    val error: String = ""
)
