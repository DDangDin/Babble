package com.myschoolproject.babble.presentation.state

import com.myschoolproject.babble.data.source.remote.response.dto.TestResponse

data class TestState(
    val data: TestResponse? = null,
    val loading: Boolean = false,
    val error: String = ""
)
