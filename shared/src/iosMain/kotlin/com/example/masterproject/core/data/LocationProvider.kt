package com.example.masterproject.core.data

import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreLocation.CLGeocodeCompletionHandler
import platform.CoreLocation.CLGeocoder
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLPlacemark
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.CoreLocation.kCLLocationAccuracyNearestTenMeters
import platform.Foundation.NSError
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class LocationProvider {
//    private val locationManager = CLLocationManager()

    private val locationManager =
        CLLocationManager().apply {
            desiredAccuracy = kCLLocationAccuracyBest
            requestWhenInUseAuthorization()
        }
    init {
        locationManager.desiredAccuracy = kCLLocationAccuracyNearestTenMeters
    }

    actual suspend fun getLocation(): String? = suspendCoroutine { continuation ->
        CLLocationManager().location?.let { location ->
            CLGeocoder().reverseGeocodeLocation(location, object : CLGeocodeCompletionHandler {
                override fun invoke(placemarks: List<*>?, error: NSError?) {
                    val gps = locationManager.location?.toString()
                    val clPlacemarks: List<CLPlacemark> = (placemarks as? List<CLPlacemark>?) ?: emptyList()
                    val decodedAddress = clPlacemarks.first().thoroughfare
                    continuation.resume(decodedAddress)
                }
            })
        } ?: run { continuation.resume(null) }
    }
    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun getCurrentLocation(): String? {

        var currentLocation: CLLocation? = null

        locationManager.requestLocation()

        fun locationManager(
            manager: CLLocationManager,
            didUpdateLocations: List<CLLocation>
        ) {
            currentLocation = didUpdateLocations.firstOrNull()
        }

        return (currentLocation?.coordinate?.toString() ?: "0.0")
    }

//    actual suspend fun getCurrentLocation(): String? = suspendCoroutine { //continuation ->
        //locationManager.requestLocation()
//        locationManager.delegate = object : CLLocationManagerDelegateProtocol {
//            override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
//                val location = didUpdateLocations.first() as? CLLocation
//                location?.let {
//                    CLGeocoder().reverseGeocodeLocation(it, object : CLGeocodeCompletionHandler {
//                        override fun invoke(placemarks: List<*>?, error: NSError?) {
//                            val clPlacemarks: List<CLPlacemark> = (placemarks as? List<CLPlacemark>?) ?: emptyList()
//                            val decodedAddress = clPlacemarks.first().thoroughfare
//                            continuation.resume(decodedAddress)
//                        }
//                    })
//                }
//            }
//        }
//    }
}

//fun saveLocation() {
//    val locationManager = remember {
//        CLLocationManager().apply {
//            desiredAccuracy = kCLLocationAccuracyBest
//            requestWhenInUseAuthorization()
//        }
//    }
//    val gps = locationManager.location?: ""
//}
