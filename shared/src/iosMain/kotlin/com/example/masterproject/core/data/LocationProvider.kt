package com.example.masterproject.core.data

import platform.CoreLocation.CLGeocodeCompletionHandler
import platform.CoreLocation.CLGeocoder
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLPlacemark
import platform.CoreLocation.kCLLocationAccuracyNearestTenMeters
import platform.Foundation.NSError
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class LocationProvider {
    private val locationManager = CLLocationManager()

    init {
        locationManager.desiredAccuracy = kCLLocationAccuracyNearestTenMeters
    }

    actual suspend fun getCurrentLocation(): String? = suspendCoroutine { continuation ->
        CLLocationManager().location?.let { location ->
            CLGeocoder().reverseGeocodeLocation(location, object : CLGeocodeCompletionHandler {
                override fun invoke(placemarks: List<*>?, error: NSError?) {
                    val clPlacemarks: List<CLPlacemark> = (placemarks as? List<CLPlacemark>?) ?: emptyList()
                    val decodedAddress = clPlacemarks.first().thoroughfare
                    continuation.resume(decodedAddress)
                }
            })
        } ?: run { continuation.resume(null) }
    }
//    @OptIn(ExperimentalForeignApi::class)
//    actual fun getCurrentLocation(): Location {
//
//        var currentLocation: CLLocation? = null
//
//        locationManager.requestLocation()
//
//        override fun locationManager(
//            manager: CLLocationManager,
//            didUpdateLocations: List<CLLocation>
//        ) {
//            currentLocation = didUpdateLocations.firstOrNull()
//        }
//
//        return Location(
//            latitude = currentLocation?.coordinate?.latitude ?: 0.0,
//            longitude = currentLocation?.coordinate?.longitude ?: 0.0
//        )
//    }
}