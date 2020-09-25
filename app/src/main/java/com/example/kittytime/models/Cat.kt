package com.example.kittytime.models

import com.squareup.moshi.Json


data class Cat (
//    @Json(name = "breeds") val breeds: Array<Breed>,
//    @Json(name = "categories") val categories: Array<Category>,
    @Json(name = "id") val id: String,
    @Json(name = "url") val imageUrl: String,
    @Json(name = "width") val width: Int,
    @Json(name = "height") val height: Int
) {
    override fun equals(other: Any?): Boolean {
        if (other == null || javaClass != other.javaClass) return false
        return id == (other as Cat).id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}