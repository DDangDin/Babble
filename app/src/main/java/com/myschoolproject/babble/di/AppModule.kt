package com.myschoolproject.babble.di

import android.util.Log
import com.myschoolproject.babble.data.source.remote.BabbleApi
import com.myschoolproject.babble.utils.Constants
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

object AppModule {

    @Singleton
    @Provides
    fun provideBabbleApi(): BabbleApi {

        fun String?.isJsonObject(): Boolean {
            return this?.startsWith("{") == true && this.endsWith("}")
        }

        fun String?.isJsonArray(): Boolean {
            return this?.startsWith("[") == true && this.endsWith("]")
        }

        val client = OkHttpClient
            .Builder()
//            .readTimeout(25, TimeUnit.SECONDS)
//            .connectTimeout(15, TimeUnit.SECONDS)
//            .writeTimeout(15, TimeUnit.SECONDS)

        val loggingInterceptor = HttpLoggingInterceptor { message ->
            when {
                message.isJsonObject() ->
                    Log.d("Retrofit_Log", JSONObject(message).toString(4))

                message.isJsonArray() ->
                    Log.d("Retrofit_Log", JSONObject(message).toString(4))

                else -> {
                    try {
                        Log.d("Retrofit_Log", JSONObject(message).toString(4))
                    } catch (e: Exception) {
                        Log.d("Retrofit_Log", message)
                    }
                }
            }
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client.addInterceptor(loggingInterceptor)

        return Retrofit.Builder()
            .baseUrl(Constants.BABBLE_MOCK_API_SERVER)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BabbleApi::class.java)
    }

}