package com.myschoolproject.babble.data.source.remote.response.dto.user

import android.service.controls.templates.ThumbnailTemplate

data class Friend(
    val _id: String,
    val age: Int,
    val city: String,
    val id: String,
    val nickname: String,
    val thumbnail: String
)