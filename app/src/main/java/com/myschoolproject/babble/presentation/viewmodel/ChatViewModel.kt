package com.myschoolproject.babble.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.myschoolproject.babble.data.source.local.entity.ChatEntity
import com.myschoolproject.babble.data.source.remote.firebase.Chat
import com.myschoolproject.babble.domain.repository.ChatRepository
import com.myschoolproject.babble.presentation.state.ChatState
import com.myschoolproject.babble.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {
    private val TAG = "ChatViewModel_Log"

    private val _chatState = mutableStateOf(ChatState())
    val chatState: State<ChatState> = _chatState

    var friendData = mutableStateOf(ChatEntity("", "", ""))
        private set

    var chatRef = Firebase.database.reference

    var inputMessage = mutableStateOf("")
        private set

    init {

    }

    var chatRoomState = mutableStateOf(emptyList<ChatEntity>())
        private set

    fun updateChatState(chat: Chat) {
        _chatState.value.chatList.add(chat)
    }

    fun getFriendDataByEmail(friend_email: String) {
        if (friend_email.isNotEmpty()) {
            viewModelScope.launch {
                friendData.value = chatRepository.getFriendDataByEmail(friend_email)
            }
        }
    }

    // Chat Message
    fun sendMessage(id_email: String, friend_email: String) {
        if (id_email.isNotEmpty() && friend_email.isNotEmpty()) {
            val id_email2 = id_email.split("@")[0]
            val friend_email2 = friend_email.split("@")[0]
            viewModelScope.launch {
                sendMessageExecute(id_email2, friend_email2, inputMessage.value)
            }
        }
    }

    private fun sendMessageExecute(id_email: String, friend_email: String, message: String) {
        // email 주소에서 문자열 자르기 해야함( '@' 기준으로 앞에 문자열만 자르기)
        val now = System.currentTimeMillis()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.KOREAN).format(now)

        val chat = Chat(
            email = id_email,
            message = message
        )

        // 이건 비효율... 쓰기 작업이 2배로 든다 (start)
        chatRef
            .child("${id_email}-${friend_email}")
            .child(simpleDateFormat)
            .setValue(chat)
        chatRef
            .child("${friend_email}-${id_email}")
            .child(simpleDateFormat)
            .setValue(chat)
        // 이건 비효율... 쓰기 작업이 2배로 든다 (end)
    }


    fun getAllChat(id_email: String, friend_email: String) {
        if (friend_email.isNotEmpty()) {
            val id_email2 = id_email.split("@")[0]
            val friend_email2 = friend_email.split("@")[0]

            var chatList = arrayListOf<Chat>()

            _chatState.value = chatState.value.copy(
                chatList = chatList,
                loading = true
            )

            // 이건 비효율... 읽기 작업이 2배로 든다 (start)
            if (!chatRef.child("${id_email2}-${friend_email2}").key.isNullOrEmpty()) {
                chatRef.child("${id_email2}-${friend_email2}")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            snapshot.children.forEach { s ->
                                chatList.add(
                                    Chat(
                                        email = s.getValue(Chat::class.java)!!.email,
                                        message = s.getValue(Chat::class.java)!!.message
                                    )
                                )
                                Log.d(TAG, s.getValue(Chat::class.java)!!.message)
                            }
                            _chatState.value = chatState.value.copy(
                                chatList = chatList,
                                loading = false
                            )
                            Log.d(TAG, "chatState: ${chatState.value.chatList.toString()}")
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.d(TAG, "firebase database get error")
                        }
                    })
            } else {
                chatRef.child("${friend_email2}-${id_email2}")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            snapshot.children.forEach { s ->
                                chatList.add(
                                    Chat(
                                        email = s.getValue(Chat::class.java)!!.email,
                                        message = s.getValue(Chat::class.java)!!.message
                                    )
                                )
                                Log.d(TAG, s.getValue(Chat::class.java)!!.message)
                            }
                            _chatState.value = chatState.value.copy(
                                chatList = chatList,
                                loading = false
                            )
                            Log.d(TAG, "chatState: ${chatState.value.chatList.toString()}")
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.d(TAG, "firebase database get error")
                        }
                    })
            }

        }
        // 이건 비효율... 읽기 작업이 2배로 든다 (end)
    }


    // Chat Room
    fun getChatRoom() {
        viewModelScope.launch {
            if (chatRepository.getChatRoom().isNotEmpty()) {
                chatRoomState.value = chatRepository.getChatRoom()
            } else {
                chatRoomState.value = emptyList()
            }
        }
    }

    fun deleteChatRoomByEmail() {
        viewModelScope.launch {
            chatRepository.deleteChatRoom(friendData.value)
            getChatRoom()
        }
    }

    fun inputMessageChanged(value: String) {
        inputMessage.value = value
    }

    fun inputMessageFinished() {
        inputMessage.value = ""
    }

}