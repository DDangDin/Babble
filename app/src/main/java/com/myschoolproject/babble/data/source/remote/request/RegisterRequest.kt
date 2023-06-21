package com.myschoolproject.babble.data.source.remote.request

data class RegisterRequest(
    val age: Int,
    val city: String,
    val email: String,
    val friends: List<FriendReq>,
    val gender: String,
    val nickname: String,
    val phoneNumber: String,
    val thumbnail: String,
    val username: String
)