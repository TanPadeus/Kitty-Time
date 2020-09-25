package com.example.kittytime.models

import com.squareup.moshi.Json


data class Cat (@Json(name = "url") val url: String) {
    override fun equals(other: Any?): Boolean {
        if (other == null || javaClass != other.javaClass) return false
        return url == (other as Cat).url
    }

    override fun hashCode(): Int {
        return url.hashCode()
    }
}