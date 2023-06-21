package com.myschoolproject.babble.presentation.state

import com.myschoolproject.babble.data.source.remote.response.dto.CommonResponse

data class TestState(
    val data: CommonResponse? = null,
    val loading: Boolean = false,
    val error: String = ""
)
