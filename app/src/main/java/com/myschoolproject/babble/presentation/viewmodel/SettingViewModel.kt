package com.myschoolproject.babble.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.myschoolproject.babble.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    fun googleLogout() {
        authRepository.googleLogout()
    }
}