package com.example.kittytime.models

import com.google.gson.annotations.SerializedName

class Cat {
    @SerializedName("url")
    private lateinit var imageUrl: String

    fun getImage() = imageUrl
    fun setImage(url: String) {
        imageUrl = url
    }
}