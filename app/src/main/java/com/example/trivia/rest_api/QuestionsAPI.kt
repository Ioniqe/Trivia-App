package com.example.calatour.rest_api

import com.example.trivia.model.Question
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.HTTP
import retrofit2.http.Headers
import retrofit2.http.Query

interface QuestionsAPI {

    @HTTP(method = "GET", path = "random/", hasBody = false)
    @Headers("Content-Type: application/json")
    fun getQuestions(@Query("count") count: String): Call<List<Question>>

    companion object {
        val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val httpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        fun create(): QuestionsAPI {
            val retrofitInstance = Retrofit.Builder()
                .baseUrl("http://jservice.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()

            return retrofitInstance.create(QuestionsAPI::class.java)
        }
    }
}