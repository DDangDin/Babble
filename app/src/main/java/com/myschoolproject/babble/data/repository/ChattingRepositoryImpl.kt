package com.myschoolproject.babble.data.repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.myschoolproject.babble.data.source.remote.firebase.Chat
import com.myschoolproject.babble.domain.repository.ChattingRepository
import com.myschoolproject.babble.utils.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

class ChattingRepositoryImpl(
    private val chatRef: DatabaseReference
) : ChattingRepository {

    override fun getChatMessages(
        my_email: String,
        friend_email: String
    ): Flow<Resource<List<Chat>>> =
        callbackFlow {
            chatRef.child("${my_email}-${friend_email}")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        Log.d(
                            "ChattingLog",
                            "chattingRepository Trigger!"
                        )
                        val chatList = arrayListOf<Chat>()
                        snapshot.children.forEach { s ->
                            chatList.add(
                                Chat(
                                    email = s.getValue(Chat::class.java)!!.email,
                                    message = s.getValue(Chat::class.java)!!.message
                                )
                            )
                        }
                        trySend(Resource.Success(chatList)).isSuccess
                    }

                    override fun onCancelled(error: DatabaseError) {
                        trySend(Resource.Error(error.message))
                    }
                })

            awaitClose { close() }
        }
}