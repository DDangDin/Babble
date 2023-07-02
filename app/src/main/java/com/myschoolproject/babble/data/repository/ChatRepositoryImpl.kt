package com.myschoolproject.babble.data.repository

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.CollectionReference
import com.myschoolproject.babble.data.source.local.ChatDao
import com.myschoolproject.babble.data.source.local.entity.ChatEntity
import com.myschoolproject.babble.data.source.remote.firebase.Chat
import com.myschoolproject.babble.data.source.remote.firebase.FriendInFirebase
import com.myschoolproject.babble.domain.repository.ChatRepository
import com.myschoolproject.babble.utils.Constants
import com.myschoolproject.babble.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID

class ChatRepositoryImpl(
    private val dao: ChatDao
) : ChatRepository {

    // Chat Room
    override suspend fun createChatRoom(friend: FriendInFirebase) {
        dao.insertChatList(
            ChatEntity(
                friend_email = friend.id_email,
                friend_nickname = friend.nickname,
                friend_thumbnail = friend.thumbnail
            )
        )
    }

    override suspend fun getChatRoom(): List<ChatEntity> = dao.getChatList()

    override suspend fun deleteChatRoom(friend: ChatEntity): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            dao.deleteRoomFromChatList(friend)
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "DB Control Error"))
        }
    }

    // Chat Message
    override suspend fun sendMessage(id_email: String, friend_email: String, text: String) {

    }

    override suspend fun getChat(id_email: String, friend_email: String): Flow<Resource<Chat>> =
        flow {

        }


    override suspend fun getFriendDataByEmail(friend_email: String): ChatEntity =
        dao.getFriendDataByEmail(friend_email)
}