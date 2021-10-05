package com.example.coroutines

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

const val BASE_URL="https://api.adviceslip.com/"

class ApiClient {

    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit?{
        retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }
}