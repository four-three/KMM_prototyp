package com.example.masterproject.core.data

expect class LocationProvider {
    suspend fun getCurrentLocation(): String?

    suspend fun getLocation(): String?
}