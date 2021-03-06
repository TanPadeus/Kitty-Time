package com.example.kittytime.interfaces

import com.example.kittytime.models.Cat
import retrofit2.Call
import retrofit2.http.*

interface CatService {
    @GET("search")
    @Headers("x-api-key: 324d2614-a41a-438a-be52-e40834324276")
    fun getCats(): Call<List<Cat>>
}