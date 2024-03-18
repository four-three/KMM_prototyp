package com.example.masterproject.core.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class LocationProvider(
    private val context: Context
) {
    @SuppressLint("MissingPermission")
    actual suspend fun getCurrentLocation(): String? = suspendCoroutine { continuation ->
        LocationServices.getFusedLocationProviderClient(context).lastLocation
            .addOnFailureListener { continuation.resume("") }
            .addOnSuccessListener { location ->
                location?.let { _ ->
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val address: String? = geocoder
                        .getFromLocation(location.latitude, location.longitude, 1)
                        ?.firstOrNull()
                        ?.thoroughfare

                    continuation.resume(address)
                }
            }
    }
    @SuppressLint("MissingPermission")
    actual suspend fun getLocation(): String? = suspendCoroutine { continuation ->
        LocationServices.getFusedLocationProviderClient(context)
            .getCurrentLocation(CurrentLocationRequest.Builder().build(), null)
            .apply {
                addOnSuccessListener {
                    val address: String? = it.latitude.toString() +  it.longitude.toString()
                    continuation.resume(address)
                }
                addOnFailureListener { continuation.resume("") }
            }
    }
}