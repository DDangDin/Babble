package com.myschoolproject.babble.data.repository

import android.util.Log
import com.myschoolproject.babble.data.source.remote.BabbleApi
import com.myschoolproject.babble.data.source.remote.request.RegisterRequest
import com.myschoolproject.babble.data.source.remote.response.dto.CheckAccount
import com.myschoolproject.babble.data.source.remote.response.dto.CommonResponse
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

    override suspend fun updateUserThumbnail(
        email: String,
        uri: String
    ): Flow<Resource<CommonResponse>> = flow {
        Log.d("ServerCall_Log_Babble", "UpdateUserThumbnail Try")
        emit(Resource.Loading())

        try {
            val call = api.updateUserThumbnail(email, uri)
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

    override suspend fun checkAccount(email: String): Flow<Resource<CheckAccount>> = flow {
        Log.d("ServerCall_Log_Babble", "CheckAccount Try")
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
        Log.d("ServerCall_Log_Babble", "LoginWithEmail Try")
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

    override suspend fun register(registerRequest: RegisterRequest): Flow<Resource<User>> = flow {
        Log.d("ServerCall_Log_Babble", "Register Try")
        emit(Resource.Loading())

        try {

            val call = api.register(registerRequest)
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