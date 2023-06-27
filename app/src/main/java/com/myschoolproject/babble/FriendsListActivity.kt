package com.myschoolproject.babble

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.myschoolproject.babble.data.source.remote.firebase.FriendInFirebase
import com.myschoolproject.babble.presentation.view.profile.friends_list.FriendsListScreen
import com.myschoolproject.babble.presentation.viewmodel.FriendsListViewModel
import com.myschoolproject.babble.ui.theme.BabbleTheme
import com.myschoolproject.babble.utils.CustomSharedPreference
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FriendsListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BabbleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val friendsListViewModel: FriendsListViewModel = hiltViewModel()
                    val context = LocalContext.current

                    val email = CustomSharedPreference(context).getUserPrefs("email")
                    friendsListViewModel.getFriends(email)

                    val friendsListState = friendsListViewModel.friendsListState.value

                    FriendsListScreen(
                        onBackStack = { finish() },
                        friendsListState = friendsListState,
                        onDelete = { friend ->
                            friendsListViewModel.deleteFriend(email, friend)
                        },
                        addForRequest = { friend ->
                            val user_data = FriendInFirebase(
                                id_email = CustomSharedPreference(context).getUserPrefs("user_data_email"),
                                age = CustomSharedPreference(context).getUserPrefs("user_data_age")
                                    .toInt(),
                                city = CustomSharedPreference(context).getUserPrefs("user_data_city"),
                                nickname = CustomSharedPreference(context).getUserPrefs("user_data_nickname"),
                                thumbnail = CustomSharedPreference(context).getUserPrefs("user_data_thumbnail"),
                            )
                            friendsListViewModel.acceptFriend(user_data, friend)
                        },
                        deleteForRequest = { friend ->
                            friendsListViewModel.rejectFriend(email, friend)
                        },
                        createChatRoom = { friend ->
                            friendsListViewModel.createChatRoom(friend)
                            finish()
                        },
                        onNavigateChatting = { friend ->
                            val intent = Intent(this@FriendsListActivity, ChatActivity::class.java)
                            intent.putExtra("friend_email", friend.id_email)
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}