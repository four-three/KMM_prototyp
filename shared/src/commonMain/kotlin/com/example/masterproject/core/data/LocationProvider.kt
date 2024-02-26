package com.example.masterproject.core.data

expect class LocationProvider {

    suspend fun getCurrentLocation(): String?

}

data class Location(
    val latitude: Double,
    val longitude: Double
)