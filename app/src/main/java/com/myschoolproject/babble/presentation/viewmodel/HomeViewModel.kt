package com.myschoolproject.babble.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myschoolproject.babble.domain.repository.UserRepository
import com.myschoolproject.babble.domain.use_case.display_friend_task.FirestoreUseCases
import com.myschoolproject.babble.presentation.state.FriendsState
import com.myschoolproject.babble.presentation.state.UserState
import com.myschoolproject.babble.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val firebaseUseCases: FirestoreUseCases
) : ViewModel() {
    private val TAG = "HomeViewModelLog"

    private val _userState = mutableStateOf(UserState())
    val userState: State<UserState> = _userState

    private val _friendsState = mutableStateOf(FriendsState())
    val friendsState: State<FriendsState> = _friendsState

    init {
        // get test dummy (firestore)
        // -> 일단은 전부 다 가져 오게 했는데
        // 만약 실제 운영 하게 되면 랜덤 으로 몇개만 가져 오던가
        // 추천 알고리듬을 통해 몇 개씩만 가져 와서 표시 해야 함
        viewModelScope.launch {
            firebaseUseCases.getDisplayFriends.invoke().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _friendsState.value = friendsState.value.copy(
                            images = result.data ?: emptyList(),
                            loading = false
                        )
                    }

                    is Resource.Loading -> {
                        _friendsState.value = friendsState.value.copy(
                            loading = true
                        )
                    }

                    is Resource.Error -> {
                        _friendsState.value = friendsState.value.copy(
                            error = result.message ?: "Firestore Error",
                            loading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)

        }

        // add test dummy (firestore)
//        for (i in 1..10) {
//            val displayFriend = DisplayFriend(
//                nickname = "nickname_$i",
//                age = i.toString(),
//                city = "city_$i",
//                thumbnail = "https://firebasestorage.googleapis.com/v0/b/babble-d075c.appspot.com/o/dog1.jpg?alt=media&token=abc3e0bb-2254-45cb-8598-22f105e54b1d"
//            )
//
//            viewModelScope.launch {
//                firebaseUseCases.addDisplayFriend.invoke(displayFriend).data ?: false
//            }
//        }

    }

    fun loginWithEmail(email: String) {
        viewModelScope.launch {
            userRepository.loginWithEmail(email).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.d(TAG, "my thumbnail: ${result.data?.thumbnail ?: "thumbnail is empty"}")
                        _userState.value = userState.value.copy(
                            userData = result.data,
                            loading = false
                        )
                    }

                    is Resource.Loading -> {
                        _userState.value = userState.value.copy(
                            loading = true
                        )
                    }

                    is Resource.Error -> {
                        Log.d(TAG, "my error: ${result.message ?: "error"}")
                        _userState.value = userState.value.copy(
                            error = result.message ?: "Unexpected Error",
                            loading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}