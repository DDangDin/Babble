package com.myschoolproject.babble.data.source.remote.firebase

data class FriendInFirebase(
    val id_email: String = "",
    val nickname: String = "",
    val age: Int = 0,
    val city: String = "",
    val thumbnail: String = "",
    val chat: List<Chat> = emptyList(),
    val friend_check: Boolean = false
)

fun getEmptyFriend() = FriendInFirebase(
    id_email = "",
    age = 0,
    city = "",
    nickname = "",
    thumbnail = "",
    chat = emptyList(),
    friend_check = false
)