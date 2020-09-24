package com.example.kittytime.interfaces

import com.example.kittytime.models.Cat
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitInterface {
    @GET
    fun getCat(): Call<Cat>
}