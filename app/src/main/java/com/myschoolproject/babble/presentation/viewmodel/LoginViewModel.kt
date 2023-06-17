package com.myschoolproject.babble.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myschoolproject.babble.domain.repository.TestRepository
import com.myschoolproject.babble.domain.repository.UserRepository
import com.myschoolproject.babble.presentation.state.CheckAccountState
import com.myschoolproject.babble.presentation.state.TestState
import com.myschoolproject.babble.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val testRepository: TestRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private val TAG = "BabbleLog_LoginViewModel"

    private val _testState = mutableStateOf(TestState())
    val testState: State<TestState> = _testState

    private val _checkAccountState = mutableStateOf(CheckAccountState())
    val checkAccountState: State<CheckAccountState> = _checkAccountState

    init {

    }

    // 만약 isExist 값이 true가 되면 밑에 함수에 사용된 email로 가입된
    // 계정이 있으므로 email 값만 (HomeScreen?으로) 넘기면 됨
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