package com.myschoolproject.babble.domain.repository

import com.myschoolproject.babble.data.source.remote.response.dto.TestResponse
import com.myschoolproject.babble.utils.Resource
import kotlinx.coroutines.flow.Flow

interface TestRepository {

    suspend fun getTestResult(): Flow<Resource<TestResponse>>
}