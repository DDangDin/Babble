package com.myschoolproject.babble.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "like_list")
data class LikeListEntity(
    @PrimaryKey val id_email: String,
    val age: Int,
    val city: String,
    val nickname: String,
    val thumbnail: String
)
