package com.myschoolproject.babble.domain.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.myschoolproject.babble.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun googleSignIn(credential: AuthCredential): Flow<Resource<AuthResult>>
}