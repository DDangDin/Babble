package com.myschoolproject.babble.presentation.view.profile.friends_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.myschoolproject.babble.data.source.remote.firebase.FriendInFirebase
import com.myschoolproject.babble.presentation.state.FriendsListState
import com.myschoolproject.babble.presentation.view.common.TopBar
import com.myschoolproject.babble.ui.theme.MainColorMiddle
import com.myschoolproject.babble.ui.theme.SpacerCustomColor
import com.myschoolproject.babble.utils.CustomThemeEffect

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FriendsListScreen(
    modifier: Modifier = Modifier,
    onBackStack: () -> Unit,
    friendsListState: FriendsListState,
    onDelete: (FriendInFirebase) -> Unit,
    addForRequest: (FriendInFirebase) -> Unit,
    deleteForRequest: (FriendInFirebase) -> Unit,
    createChatRoom: (FriendInFirebase) -> Unit,
    onNavigateChatting: (FriendInFirebase) -> Unit
) {

    val friendsList = friendsListState.friends.filter { it.id_email != "test" }
//    val friendsRequestList_ = friendsListState.friends.filter { it.id_email != "test" && it.friend_check == false }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TopBar(
            modifier = Modifier,
            title = "친구들",
            onBackStack = onBackStack
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
                .height(1.dp)
                .background(SpacerCustomColor)
        )
        if (friendsListState.loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(bottom = 50.dp),
                strokeWidth = 3.dp,
                color = MainColorMiddle
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 13.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    25.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                items(friendsList) { friend ->
                    CompositionLocalProvider(LocalRippleTheme provides CustomThemeEffect.NoRippleTheme) {
                        FriendCardView(
                            modifier = Modifier,
                            friend = friend,
                            onDelete = {
                                onDelete(friend)
                            },
                            isRequest = !friend.friend_check,
                            addForRequest = {
                                addForRequest(friend)
                            },
                            deleteForRequest = {
                                deleteForRequest(friend)
                            },
                            createChatRoom = {
                                if (friend.friend_check) {
                                    createChatRoom(friend)
                                }
                            },
                            onNavigateChatting = {
                                onNavigateChatting(friend)
                            }
                        )
                    }
                }
            }
        }
    }
}