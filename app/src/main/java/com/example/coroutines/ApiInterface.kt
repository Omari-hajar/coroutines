package com.example.coroutines

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("advice")

    fun getData(): Call<Data>
}