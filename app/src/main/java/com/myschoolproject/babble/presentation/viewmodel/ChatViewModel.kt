package com.myschoolproject.babble.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.values
import com.google.firebase.ktx.Firebase
import com.myschoolproject.babble.data.source.local.entity.ChatEntity
import com.myschoolproject.babble.data.source.remote.firebase.Chat
import com.myschoolproject.babble.data.source.remote.firebase.FriendInFirebase
import com.myschoolproject.babble.domain.repository.ChatRepository
import com.myschoolproject.babble.domain.repository.FriendsRepository
import com.myschoolproject.babble.presentation.state.ChatRoomListState
import com.myschoolproject.babble.presentation.state.ChatState
import com.myschoolproject.babble.presentation.state.FriendsListState
import com.myschoolproject.babble.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val friendsRepository: FriendsRepository
) : ViewModel() {
    private val TAG = "ChatViewModel_Log"

    private val _chatState = mutableStateOf(ChatState())
    val chatState: State<ChatState> = _chatState

    private val _chatRoomState = mutableStateOf(ChatRoomListState())
    val chatRoomState: State<ChatRoomListState> = _chatRoomState

    var friendData = mutableStateOf(ChatEntity("", "", ""))
        private set

    var chatRef = Firebase.database.reference

    var inputMessage = mutableStateOf("")
        private set

    private var checkFriendsList = mutableStateOf(listOf<FriendInFirebase>())

    init {
        Log.d("ChatViewModel", "ChatViewModel Init!!")
//        viewModelScope.launch {
//            chatRepository.getChatRoom()
//        }
    }

//    var chatRoomState = mutableStateOf(emptyList<ChatEntity>())
//        private set

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

    // checkChatRoom() 이 완료 되면 실행
    private fun getChatRoom() {
        Log.d("ChatViewModel", "getChatRoom()")
        viewModelScope.launch {
            if (chatRepository.getChatRoom().isNotEmpty()) {
                val chatRoomList = chatRepository.getChatRoom()
                _chatRoomState.value = _chatRoomState.value.copy(loading = true)
                _chatRoomState.value =
                    _chatRoomState.value.copy(chatRoomList = chatRoomList, loading = false)
            }
        }
    }

    // RoomDB 안 쓴 버전
//    fun checkChatRoom(id_email: String) {
//        Log.d("ChatViewModel", "checkChatRoom()")
//
//        val id_email2 = id_email.split("@")[0]
//
//        viewModelScope.launch {
//            friendsRepository.getFriends(id_email).onEach { result ->
//                when (result) {
//                    is Resource.Success -> {
//                        val friendsList = result.data
//                        var resultFriendsList = arrayListOf<ChatEntity>()
//                        friendsList?.let {
//                            it.forEach { friend ->
//                                val friend_email2 = friend.id_email.split("@")[0]
//
//                                if (!chatRef.child("${id_email2}-${friend_email2}").key.isNullOrEmpty()) {
//                                    chatRepository.createChatRoom(friend)
//                                    resultFriendsList.add(
//                                        ChatEntity(
//                                            friend_email = friend.id_email,
//                                            friend_nickname = friend.nickname,
//                                            friend_thumbnail = friend.thumbnail
//                                        )
//                                    )
////                                    getChatRoom()
//                                }
//                            }
//                            _chatRoomState.value = chatRoomState.value.copy(
//                                chatRoomList = resultFriendsList,
//                                loading = false
//                            )
//                        }
//                    }
//                    is Resource.Loading -> {
//                        _chatRoomState.value = chatRoomState.value.copy(
//                            loading = true
//                        )
//                    }
//                    is Resource.Error -> {
//                        _chatRoomState.value = chatRoomState.value.copy(
//                            error = result.message ?: "Unexpected Error",
//                            loading = false
//                        )
//                    }
//                }
//            }.launchIn(viewModelScope)
//
////            getChatRoom()
//        }
//    }

    fun checkChatRoom(id_email: String) {
        Log.d("ChatViewModel", "checkChatRoom()")

        val id_email2 = id_email.split("@")[0]

        viewModelScope.launch {
            friendsRepository.getFriends(id_email).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        val friendsList = result.data
                        friendsList?.let {
                            it.forEach { friend ->

                                val friend_email2 = friend.id_email.split("@")[0]

                                chatRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        if (snapshot.child("${id_email2}-${friend_email2}").exists()) {
                                            Log.d(
                                                "ChatViewModel",
                                                "checkChatRoom(), ${id_email2}-${friend_email2}"
                                            )
                                            viewModelScope.launch {
                                                chatRepository.createChatRoom(friend)
                                            }
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        TODO("Not yet implemented")
                                    }
                                })
                            }
                        }
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {}
                }
            }.launchIn(viewModelScope)

            getChatRoom()
        }
    }

    fun deleteChatRoomByEmail(id_email: String, friendData: ChatEntity) {

        val id_email2 = id_email.split("@")[0]
        val friend_email2 = friendData.friend_email.split("@")[0]

        viewModelScope.launch {
            chatRepository.deleteChatRoom(friendData)

            // delete firebase(realtime database)
            if (!chatRef.child("${id_email2}-${friend_email2}").key.isNullOrEmpty()) {
                chatRef
                    .child("${id_email2}-${friend_email2}")
                    .removeValue()
            }
            if (!chatRef.child("${friend_email2}-${id_email2}").key.isNullOrEmpty()) {
                chatRef
                    .child("${friend_email2}-${id_email2}")
                    .removeValue()
            }

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