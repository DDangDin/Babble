package com.myschoolproject.babble.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myschoolproject.babble.data.source.remote.firebase.FriendInFirebase
import com.myschoolproject.babble.domain.repository.FriendsRepository
import com.myschoolproject.babble.presentation.state.FriendsListState
import com.myschoolproject.babble.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsListViewModel @Inject constructor(
    private val friendsRepository: FriendsRepository
): ViewModel() {

    private val _friendsListState = mutableStateOf(FriendsListState())
    val friendsListState: State<FriendsListState> = _friendsListState

    fun getFriends(id_email: String) {
        friendsRepository.getFriends(id_email).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _friendsListState.value = friendsListState.value.copy(
                        friends = result.data ?: emptyList(),
                        loading = false
                    )
                }

                is Resource.Loading -> {
                    _friendsListState.value = friendsListState.value.copy(
                        loading = true
                    )
                }

                is Resource.Error -> {
                    _friendsListState.value = friendsListState.value.copy(
                        error = result.message ?: "Unexpected Error",
                        loading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteFriend(id_email: String, friend: FriendInFirebase) {
        viewModelScope.launch {
            friendsRepository.deleteFriend(id_email, friend)
        }
    }

    fun addFriend(id_email: String, friend: FriendInFirebase) {
        viewModelScope.launch {
            friendsRepository.addFriend(id_email, friend)
        }
    }
}