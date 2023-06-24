package com.myschoolproject.babble.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myschoolproject.babble.data.source.local.entity.LikeListEntity

@Dao
interface LikeListDao {

    @Query("SELECT * FROM like_list")
    suspend fun getLikeList(): List<LikeListEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLikeLike(vararg likeList: LikeListEntity)

    @Query("DELETE FROM like_list")
    suspend fun deleteLikeList()

    @Delete
    suspend fun deleteFriendFromLikeList(likeFriend: LikeListEntity)

    @Query("SELECT (SELECT COUNT(*) FROM like_list) == 0")
    suspend fun isEmpty(): Boolean
}