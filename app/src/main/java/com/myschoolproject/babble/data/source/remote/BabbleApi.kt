package com.myschoolproject.babble.data.source.remote

import com.myschoolproject.babble.data.source.remote.response.dto.TestResponseDto
import retrofit2.Call
import retrofit2.http.GET

interface BabbleApi {

    @GET("/test")
    fun getTest(): Call<TestResponseDto>
}