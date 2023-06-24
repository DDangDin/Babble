package com.myschoolproject.babble.domain.repository

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myschoolproject.babble.data.source.local.entity.LikeListEntity
import kotlinx.coroutines.flow.Flow

interface LikeListRepository {

    suspend fun getLikeList(): List<LikeListEntity>

    suspend fun insertLikeLike(likeList: LikeListEntity)

    suspend fun deleteLikeList()

    suspend fun deleteFriendFromLikeList(likeList: LikeListEntity)

    suspend fun isEmpty(): Boolean
}