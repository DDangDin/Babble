package com.myschoolproject.babble.presentation.state

import com.myschoolproject.babble.data.source.remote.response.dto.user.DisplayFriend

data class HomeImagesState(
    val images: List<DisplayFriend> = emptyList(),
    val loading: Boolean = false,
    val error: String = ""
)
