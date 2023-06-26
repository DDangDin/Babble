package com.myschoolproject.babble.data.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.myschoolproject.babble.domain.repository.AuthRepository
import com.myschoolproject.babble.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
): AuthRepository {

    override fun googleSignIn(credential: AuthCredential): Flow<Resource<AuthResult>> = flow {
        emit(Resource.Loading())
        val result = firebaseAuth.signInWithCredential(credential).await()
        emit(Resource.Success(result))
    }.catch {
        emit(Resource.Error(it.message.toString()))
    }

    override fun googleLogout() {
        firebaseAuth.signOut()
    }
}