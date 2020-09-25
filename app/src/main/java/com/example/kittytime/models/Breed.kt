package com.example.kittytime.models

import com.squareup.moshi.Json

data class Breed(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "temperament") val temperament: String,
    @Json(name = "life_span") val lifeSpan: String,
    @Json(name = "alt_names") val altNames: String,
    @Json(name = "wikipedia_url") val wikipediaUrl: String,
    @Json(name = "origin") val origin: String,
    @Json(name = "weight_imperial") val weightImperial: String,
    @Json(name = "experimental") val experimental: Int,
    @Json(name = "hairless") val hairless: Int,
    @Json(name = "natural") val natural: Int,
    @Json(name = "rare") val rare: Int,
    @Json(name = "rex") val rex: Int,
    @Json(name = "suppress_tail") val suppressTail: Int,
    @Json(name = "short_legs") val shortLegs: Int,
    @Json(name = "hypoallergenic") val hypoallergenic: Int,
    @Json(name = "adaptability") val adaptability: Int,
    @Json(name = "affection_level") val affectionLevel: Int,
    @Json(name = "country_code") val countryCode: String,
    @Json(name = "child_friendly") val childFriendly: Int,
    @Json(name = "dog_friendly") val dogFriendly: Int,
    @Json(name = "energy_level") val energyLevel: Int,
    @Json(name = "grooming") val grooming: Int,
    @Json(name = "health_issues") val healthIssues: Int,
    @Json(name = "intelligence") val intelligence: Int,
    @Json(name = "shedding_level") val sheddingLevel: Int,
    @Json(name = "social_needs") val socialNeeds: Int,
    @Json(name = "stranger_friendly") val strangerFriendly: Int,
    @Json(name = "vocalisation") val vocalisation: Int
)