package com.myschoolproject.babble.data.repository

import android.util.Log
import com.myschoolproject.babble.data.source.remote.BabbleApi
import com.myschoolproject.babble.data.source.remote.response.dto.CheckAccount
import com.myschoolproject.babble.data.source.remote.response.dto.user.User
import com.myschoolproject.babble.domain.repository.UserRepository
import com.myschoolproject.babble.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.await
import java.io.IOException

class UserRepositoryImpl(
    private val api: BabbleApi
): UserRepository {
    private val TAG = "BabbleLog_UserRepositoryImple"

    override suspend fun checkAccount(email: String): Flow<Resource<CheckAccount>> = flow {
        emit(Resource.Loading())

        try {

            val call = api.checkAccount(email)
            val response = call.await()
            emit(Resource.Success(response))

        } catch (e: IOException) {

            Log.d(TAG, "error: ${e.localizedMessage}")
            emit(Resource.Error(message = "error: ${e.localizedMessage ?: "internet connection error"}"))

        } catch (e: HttpException) {

            Log.d(TAG, "error: ${e.localizedMessage}")
            emit(Resource.Error(message = "error: ${e.localizedMessage ?: "unexpected error"}"))

        }
    }

    override suspend fun loginWithEmail(email: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading())

        try {

            val call = api.loginWithEmail(email)
            val response = call.await()
            emit(Resource.Success(response))

        } catch (e: IOException) {

            Log.d(TAG, "error: ${e.localizedMessage}")
            emit(Resource.Error(message = "error: ${e.localizedMessage ?: "internet connection error"}"))

        } catch (e: HttpException) {

            Log.d(TAG, "error: ${e.localizedMessage}")
            emit(Resource.Error(message = "error: ${e.localizedMessage ?: "unexpected error"}"))

        }
    }
}