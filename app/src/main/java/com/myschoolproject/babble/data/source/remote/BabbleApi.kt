package com.myschoolproject.babble.data.source.remote

import com.myschoolproject.babble.data.source.remote.request.RegisterRequest
import com.myschoolproject.babble.data.source.remote.response.dto.CheckAccount
import com.myschoolproject.babble.data.source.remote.response.dto.TestResponse
import com.myschoolproject.babble.data.source.remote.response.dto.user.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BabbleApi {

    @GET("/test")
    fun getTest(): Call<TestResponse>

    @GET("/api/auth/exists/email/{email}")
    fun checkAccount(
        @Path("email") email: String
    ): Call<CheckAccount>

    @POST("/api/auth/login/{email}")
    fun loginWithEmail(
        @Path("email") email: String
    ): Call<User>

    @POST("api/auth/register")
    fun register(
        @Body registerRequest: RegisterRequest
    ): Call<User>
}