package com.myschoolproject.babble.presentation.state

import com.google.firebase.auth.AuthResult

data class GoogleSignInState(
    val result: AuthResult? = null,
    val loading: Boolean = false,
    val error: String = "",
    val isExist: Boolean = false
)
