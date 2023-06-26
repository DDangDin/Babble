package com.myschoolproject.babble.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myschoolproject.babble.data.source.local.entity.ChatEntity
import com.myschoolproject.babble.data.source.local.entity.LikeListEntity

@Dao
interface ChatDao {

    @Query("SELECT * FROM chat_list")
    suspend fun getChatList(): List<ChatEntity>

    @Query("SELECT * FROM chat_list WHERE friend_email = :friend_email")
    suspend fun getFriendDataByEmail(friend_email: String): ChatEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChatList(vararg chatEntity: ChatEntity)

    @Query("DELETE FROM chat_list")
    suspend fun deleteChatList()

    @Delete
    suspend fun deleteRoomFromChatList(chatEntity: ChatEntity)
}