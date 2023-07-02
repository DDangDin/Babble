package com.myschoolproject.babble.domain.repository

import com.myschoolproject.babble.data.source.local.entity.ChatEntity
import com.myschoolproject.babble.data.source.remote.firebase.Chat
import com.myschoolproject.babble.data.source.remote.firebase.FriendInFirebase
import com.myschoolproject.babble.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    // ChatRoom
    suspend fun createChatRoom(friend: FriendInFirebase)

    suspend fun getChatRoom(): List<ChatEntity>

    suspend fun deleteChatRoom(friend: ChatEntity): Flow<Resource<Boolean>>


    // Chat Message
    suspend fun sendMessage(id_email: String, friend_email: String, text: String)

    suspend fun getChat(id_email: String, friend_email: String): Flow<Resource<Chat>>


    // Get Friend Data
    suspend fun getFriendDataByEmail(friend_email: String): ChatEntity
}