package com.myschoolproject.babble.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.myschoolproject.babble.domain.repository.AuthRepository
import com.myschoolproject.babble.domain.repository.TestRepository
import com.myschoolproject.babble.domain.repository.UserRepository
import com.myschoolproject.babble.presentation.state.CheckAccountState
import com.myschoolproject.babble.presentation.state.GoogleSignInState
import com.myschoolproject.babble.presentation.state.TestState
import com.myschoolproject.babble.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val testRepository: TestRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val TAG = "BabbleLog_LoginViewModel"

    private val _testState = mutableStateOf(TestState())
    val testState: State<TestState> = _testState

    private val _checkAccountState = mutableStateOf(CheckAccountState())
    val checkAccountState: State<CheckAccountState> = _checkAccountState

    private val _googleSignInState = mutableStateOf(GoogleSignInState())
    val googleSignInState: State<GoogleSignInState> = _googleSignInState

    init {

    }

    fun googleSignIn(credential: AuthCredential) = viewModelScope.launch {
        authRepository.googleSignIn(credential).onEach{ result ->
            when(result) {
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

    fun checkAccount(email: String) {
        viewModelScope.launch {
            userRepository.checkAccount("mock@test.com").onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _checkAccountState.value = checkAccountState.value.copy(
                            data = result.data,
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
}