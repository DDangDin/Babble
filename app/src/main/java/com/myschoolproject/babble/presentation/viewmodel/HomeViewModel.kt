package com.myschoolproject.babble.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myschoolproject.babble.domain.repository.UserRepository
import com.myschoolproject.babble.domain.use_case.display_friend_task.FirestoreUseCases
import com.myschoolproject.babble.presentation.state.HomeImagesState
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

    private val _homeImagesState = mutableStateOf(HomeImagesState())
    val homeImagesState: State<HomeImagesState> = _homeImagesState

    init {
        // get test dummy (firestore)
        viewModelScope.launch {
            firebaseUseCases.getDisplayFriends.invoke().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _homeImagesState.value = homeImagesState.value.copy(
                            images = result.data ?: emptyList(),
                            loading = false
                        )
                    }

                    is Resource.Loading -> {
                        _homeImagesState.value = homeImagesState.value.copy(
                            loading = true
                        )
                    }

                    is Resource.Error -> {
                        _homeImagesState.value = homeImagesState.value.copy(
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

    fun login(email: String) {
        viewModelScope.launch {
            userRepository.loginWithEmail(email).onEach { result ->
                when (result) {
                    is Resource.Success -> {
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
                        _userState.value = userState.value.copy(
                            error = result.message ?: "Unexpected Error",
                            loading = false
                        )
                    }
                }
            }
        }
    }
}