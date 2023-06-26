package com.myschoolproject.babble.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_list")
data class ChatEntity(
    @PrimaryKey val friend_email: String,
    val friend_nickname: String,
    val friend_thumbnail: String
)
