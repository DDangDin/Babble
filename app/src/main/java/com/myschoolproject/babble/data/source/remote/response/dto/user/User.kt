package com.myschoolproject.babble.data.source.remote.response.dto.user

data class User(
    val age: Int,
    val email: String,
    val friends: List<Friend>,
    val gender: String,
    val nickname: String,
    val phoneNumber: String,
    val thumbnail: String,
    val username: String
) {
    fun getEmptyUser(): User = User(
        age = 0,
        email = "",
        friends = emptyList(),
        gender = "",
        nickname = "",
        phoneNumber = "",
        username = "",
        thumbnail = ""
    )
}