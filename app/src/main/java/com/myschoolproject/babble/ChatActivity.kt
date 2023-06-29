package com.myschoolproject.babble

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.myschoolproject.babble.data.source.local.entity.ChatEntity
import com.myschoolproject.babble.data.source.remote.firebase.Chat
import com.myschoolproject.babble.presentation.view.chat.chatting_screen.ChattingScreen
import com.myschoolproject.babble.presentation.viewmodel.ChatViewModel
import com.myschoolproject.babble.ui.theme.BabbleTheme
import com.myschoolproject.babble.utils.CustomSharedPreference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatActivity : ComponentActivity() {

    private val TAG = "ChatActivity_Log"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BabbleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    // ChatScreen 에서의 ChatViewModel 과는 다른 인스턴스
                    val chatViewModel: ChatViewModel = hiltViewModel()
                    val chatState = chatViewModel.chatState

//                    var chatList by remember { mutableStateOf(arrayListOf<Chat>()) }

                    val coroutineScope = rememberCoroutineScope()

                    val email = CustomSharedPreference(context).getUserPrefs("email")
                    val friend_email = intent.getStringExtra("friend_email")
                    chatViewModel.getFriendDataByEmail(friend_email ?: "")

                    LaunchedEffect(key1 = true) {
                        chatViewModel.getAllChat(email, friend_email ?: "")
                        Log.d(TAG, "getAllChat")
                    }

//                    if (chatState.value.chatList.isNotEmpty()) {
//                        chatList = chatState.value.chatList
//                    }

                    ChattingScreen(
                        onBackStack = { finish() },
                        chatState = chatState.value,
                        friendData = chatViewModel.friendData.value,
                        inputMessage = chatViewModel.inputMessage.value,
                        inputMessageChanged = chatViewModel::inputMessageChanged,
                        onSend = {
                            chatViewModel.sendMessage(email, friend_email ?: "")
                            chatViewModel.getAllChat(email, friend_email ?: "")
                            chatViewModel.inputMessageFinished()
                        },
                        onExitRoom = {
//                            chatViewModel.deleteChatRoomByEmail()
//                            finish()
                        },
                    )
                }
            }
        }
    }
}