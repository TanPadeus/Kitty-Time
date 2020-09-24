package com.example.kittytime.interfaces

import com.example.kittytime.models.Cat
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitInterface {
    @GET("cat.json")
    fun getCat(): Call<Cat>
}