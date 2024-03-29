package com.myschoolproject.babble.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.myschoolproject.babble.data.source.remote.request.RegisterRequest
import com.myschoolproject.babble.data.source.remote.firebase.DisplayFriend
import com.myschoolproject.babble.domain.repository.AuthRepository
import com.myschoolproject.babble.domain.repository.FriendsRepository
import com.myschoolproject.babble.domain.repository.TestRepository
import com.myschoolproject.babble.domain.repository.UserRepository
import com.myschoolproject.babble.domain.use_case.display_friend_task.FirestoreUseCases
import com.myschoolproject.babble.presentation.state.CheckAccountState
import com.myschoolproject.babble.presentation.state.FirestoreState
import com.myschoolproject.babble.presentation.state.GoogleSignInState
import com.myschoolproject.babble.presentation.state.TestState
import com.myschoolproject.babble.presentation.state.UserState
import com.myschoolproject.babble.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val firebaseUseCases: FirestoreUseCases,
    private val friendsRepository: FriendsRepository
) : ViewModel() {
    private val TAG = "BabbleLog_LoginViewModel"

    // State
    private val _testState = mutableStateOf(TestState())
    val testState: State<TestState> = _testState

    private val _checkAccountState = mutableStateOf(CheckAccountState())
    val checkAccountState: State<CheckAccountState> = _checkAccountState

    private val _googleSignInState = mutableStateOf(GoogleSignInState())
    val googleSignInState: State<GoogleSignInState> = _googleSignInState

    private val _userState = mutableStateOf(UserState())
    val userState: State<UserState> = _userState

    // For Firebase State
    private val _getDisplayFriendState = mutableStateOf(FirestoreState())
    val getDisplayFriendState: State<FirestoreState> = _getDisplayFriendState

    private val _addDisplayFriendState = mutableStateOf(false)
    val addDisplayFriendState: State<Boolean> = _addDisplayFriendState

    private val _deleteDisplayFriendState = mutableStateOf(false)
    val deleteDisplayFriendState: State<Boolean> = _deleteDisplayFriendState

    // For TextField
    var nickname = mutableStateOf("")
        private set
    var age = mutableStateOf("")
        private set
    var city = mutableStateOf("")
        private set
    var gender = mutableStateOf("")
        private set
    var phoneNumber = mutableStateOf("")
        private set


    fun googleSignIn(credential: AuthCredential) = viewModelScope.launch {
        authRepository.googleSignIn(credential).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _googleSignInState.value = googleSignInState.value.copy(
                        result = result.data,
                        loading = false
                    )
                }

                is Resource.Loading -> {
                    _googleSignInState.value = googleSignInState.value.copy(
                        loading = true
                    )
                }

                is Resource.Error -> {
                    _googleSignInState.value = googleSignInState.value.copy(
                        error = result.message ?: "Unexpected Error",
                        loading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun checkAccountInit() {
        _checkAccountState.value = checkAccountState.value.copy(
            data = null
        )
    }
    fun checkAccount(email: String) {
        viewModelScope.launch {
            userRepository.checkAccount(email).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _checkAccountState.value = checkAccountState.value.copy(
                            data = result.data!!,
                            loading = false
                        )
                    }

                    is Resource.Loading -> {
                        _checkAccountState.value = checkAccountState.value.copy(
                            loading = true
                        )
                    }

                    is Resource.Error -> {
                        _checkAccountState.value = checkAccountState.value.copy(
                            loading = false,
                            error = result.message ?: "Unexpected Error",
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun login(email: String = "") {

    }

    fun register() {

        val uuid = UUID.randomUUID().toString()

        val registerRequest = RegisterRequest(
            age = age.value.toInt(),
            city = city.value,
            email = googleSignInState.value.result?.user?.email ?: "test@test.com",
            friends = emptyList(),
            gender = gender.value,
            nickname = nickname.value,
            phoneNumber = uuid,
            thumbnail = "",
            username = googleSignInState.value.result?.user?.displayName ?: "",
        )

        val email = googleSignInState.value.result?.user?.email ?: ""
        val displayFriend = DisplayFriend(
            id_email = email,
            nickname = nickname.value,
            age = age.value,
            city = city.value,
            thumbnail = ""
        )

        viewModelScope.launch {

            // 본인 프로필도 다른 사용자 들에게 랜덤 으로 보여지기 때문
            _addDisplayFriendState.value = firebaseUseCases.addDisplayFriend.invoke(displayFriend).data ?: false

            // 친구 목록 초기화
            friendsRepository.initializeFriendsList(email)

            userRepository.register(registerRequest).onEach { result ->
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
            }.launchIn(viewModelScope)
        }
    }


    // for Register InputData
    fun genderChanged(value: String) {
        gender.value = value
    }

    fun nicknameChanged(value: String) {
        nickname.value = value
    }

    fun ageChanged(value: String) {
        age.value = value
    }

    fun cityChanged(value: String) {
        city.value = value
    }

    fun phoneNumberChanged(value: String) {
        phoneNumber.value = value
    }
}