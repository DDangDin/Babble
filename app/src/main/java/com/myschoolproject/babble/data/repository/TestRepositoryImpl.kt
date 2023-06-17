package com.myschoolproject.babble.data.repository

import android.util.Log
import com.myschoolproject.babble.data.source.remote.BabbleApi
import com.myschoolproject.babble.data.source.remote.response.dto.TestResponse
import com.myschoolproject.babble.domain.repository.TestRepository
import com.myschoolproject.babble.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.await
import java.io.IOException

class TestRepositoryImpl(
    private val api: BabbleApi
): TestRepository {
    private val TAG = "BabbleLog_TestRepositoryImpl"

    override suspend fun getTestResult(): Flow<Resource<TestResponse>> = flow {
        emit(Resource.Loading())

        try {

            val call = api.getTest()
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