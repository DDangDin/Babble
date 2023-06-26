package com.myschoolproject.babble

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
import com.myschoolproject.babble.presentation.view.home.like_list.LikeListScreen
import com.myschoolproject.babble.presentation.viewmodel.FriendsListViewModel
import com.myschoolproject.babble.presentation.viewmodel.LikeListViewModel
import com.myschoolproject.babble.ui.theme.BabbleTheme
import com.myschoolproject.babble.utils.CustomSharedPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikeListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BabbleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val likeListViewModel: LikeListViewModel = hiltViewModel()
                    val friendsListViewModel: FriendsListViewModel = hiltViewModel()

                    val context = LocalContext.current

                    val email = CustomSharedPreference(context).getUserPrefs("email")

                    LikeListScreen(
                        onBackStack = { finish() },
                        likeList = likeListViewModel.likeList.value,
                        onSend = { likeListEntity ->
                            val friendInFirebase = FriendInFirebase(
                                id_email = likeListEntity.id_email,
                                age = likeListEntity.age,
                                city = likeListEntity.city,
                                nickname = likeListEntity.nickname,
                                thumbnail = likeListEntity.thumbnail,
                                friend_check = false,
                                chat = emptyList()
                            )
                            val user_data = FriendInFirebase(
                                id_email = CustomSharedPreference(context).getUserPrefs("user_data_email"),
                                age = CustomSharedPreference(context).getUserPrefs("user_data_age")
                                    .toInt(),
                                city = CustomSharedPreference(context).getUserPrefs("user_data_city"),
                                nickname = CustomSharedPreference(context).getUserPrefs("user_data_nickname"),
                                thumbnail = CustomSharedPreference(context).getUserPrefs("user_data_thumbnail"),
                            )
                            friendsListViewModel.addFriend(user_data, friendInFirebase)
                            likeListViewModel.deleteFriendFromLikeList(likeListEntity)
                        },
                        onDelete = { likeListEntity ->
                            likeListViewModel.deleteFriendFromLikeList(likeListEntity)
                        },
                    )
                }
            }
        }
    }
}
