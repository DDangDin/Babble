package com.myschoolproject.babble.domain.repository

import com.myschoolproject.babble.data.source.remote.request.RegisterRequest
import com.myschoolproject.babble.data.source.remote.response.dto.CheckAccount
import com.myschoolproject.babble.data.source.remote.response.dto.CommonResponse
import com.myschoolproject.babble.data.source.remote.response.dto.user.User
import com.myschoolproject.babble.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun checkAccount(email: String): Flow<Resource<CheckAccount>>

    suspend fun loginWithEmail(email: String): Flow<Resource<User>>

    suspend fun register(registerRequest: RegisterRequest): Flow<Resource<User>>

    suspend fun updateUserThumbnail(email: String, uri: String): Flow<Resource<CommonResponse>>
}