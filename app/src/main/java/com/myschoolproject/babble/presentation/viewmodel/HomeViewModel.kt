package com.myschoolproject.babble.presentation.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myschoolproject.babble.data.source.local.entity.LikeListEntity
import com.myschoolproject.babble.data.source.remote.firebase.DisplayFriend
import com.myschoolproject.babble.data.source.remote.firebase.FriendInFirebase
import com.myschoolproject.babble.domain.repository.FriendsRepository
import com.myschoolproject.babble.domain.repository.LikeListRepository
import com.myschoolproject.babble.domain.repository.UserRepository
import com.myschoolproject.babble.domain.use_case.display_friend_task.FirestoreUseCases
import com.myschoolproject.babble.presentation.state.RandomFriendsState
import com.myschoolproject.babble.presentation.state.UserState
import com.myschoolproject.babble.utils.Constants.TEST_IMAGES_FROM_FIREBASE_STORAGE
import com.myschoolproject.babble.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val firebaseUseCases: FirestoreUseCases,
    private val likeListRepository: LikeListRepository,
    private val friendsRepository: FriendsRepository
) : ViewModel() {
    private val TAG = "HomeViewModelLog"

    private val _userState = mutableStateOf(UserState())
    val userState: State<UserState> = _userState

    private val _randomFriendsState = mutableStateOf(RandomFriendsState())
    val randomFriendsState: State<RandomFriendsState> = _randomFriendsState

    var selectedImageUri = mutableStateOf<Uri?>(null)
        private set
    var updateUserThumbnail = mutableStateOf(false)
        private set

    var alreadyCheck = mutableStateOf(arrayOf<Boolean>())
        private set

    init {
        // test: initialize friends_list
//        viewModelScope.launch {
//            friendsRepository.initializeFriendsList("developmenttest924@gmail.com")
//            for (i in 0..9) {
//                friendsRepository.createTestDummy(
//                    "developmenttest924@gmail.com",
//                    friend_email = "friend_${i}@test.com",
//                    FriendInFirebase(
//                        chat = emptyList(),
//                        nickname = "nickname_$i",
//                        age = 20,
//                        city = "",
//                        id_email = "friend_${i}@test.com",
//                        friend_check = i%2 == 0,
//                        thumbnail = TEST_IMAGES_FROM_FIREBASE_STORAGE[i]
//                    )
//                )
//                delay(1000L)
//            }
//        }

        // test: get dummy (firestore)
        // -> 일단은 전부 다 가져 오게 했는데
        // 만약 실제 운영 하게 되면 랜덤 으로 몇개만 가져 오던가
        // 추천 알고리듬을 통해 몇 개씩만 가져 와서 표시 해야 함
        viewModelScope.launch {
            firebaseUseCases.getDisplayFriends.invoke().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _randomFriendsState.value = randomFriendsState.value.copy(
                            images = result.data ?: emptyList(),
                            loading = false
                        )
                        alreadyCheck.value = Array(randomFriendsState.value.images.size) { false }
                    }

                    is Resource.Loading -> {
                        _randomFriendsState.value = randomFriendsState.value.copy(
                            loading = true
                        )
                    }

                    is Resource.Error -> {
                        _randomFriendsState.value = randomFriendsState.value.copy(
                            error = result.message ?: "Firestore Error",
                            loading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)

        }

        // test: add dummy (firestore)
//        for (i in 0..9) {
//            val displayFriend = DisplayFriend(
//                id_email = "test@test_${i}.com",
//                nickname = "nickname_$i",
//                age = i.toString(),
//                city = "city_$i",
//                thumbnail = TEST_IMAGES_FROM_FIREBASE_STORAGE[i]
//            )
//
//            viewModelScope.launch {
//                firebaseUseCases.addDisplayFriend.invoke(displayFriend).data ?: false
//            }
//        }
    }

    fun checkLikeAndDislike(
        index: Int,
        like: Boolean,
        friend: DisplayFriend = DisplayFriend()
    ) {
        alreadyCheck.value[index] = true

        viewModelScope.launch {
            val entity = LikeListEntity(
                id_email = friend.id_email,
                age = friend.age.toInt(),
                city = friend.city,
                nickname = friend.nickname,
                thumbnail = friend.thumbnail
            )
            if (like) {
                likeListRepository.insertLikeLike(entity)
            } else {
                // 싫어요 버튼 눌렀을 때
                // -> 사실 상 그냥 넘어감
            }
        }
    }

    fun updateMyProfilePhoto(email: String, uri: Uri) {
        if (email.isNotEmpty()) {
            selectedImageUri.value = uri
            viewModelScope.launch {
                userRepository.updateUserThumbnail(email, uri.toString()).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            if (result.data!!.result)
                                updateUserThumbnail.value = true
                        }

                        is Resource.Loading -> {}
                        is Resource.Error -> {}
                    }
                }.launchIn(viewModelScope)
            }
        } else {
            // email을 입력 받지 못해 update 할 수 없음
        }
    }

    fun loginWithEmail(email: String) {
        viewModelScope.launch {
            userRepository.loginWithEmail(email).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.d(
                            TAG,
                            "my thumbnail: ${result.data?.thumbnail ?: "thumbnail is empty"}"
                        )
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