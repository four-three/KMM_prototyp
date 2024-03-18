package com.example.masterproject.core.data
/**
 * Common interface for accessing device location.
 *
 * The location and the saving of it is very platform specific. And so it does seem that there is no
 * common way to get the location. Further the implementation seems to be harder when trying to use KMM or Compose Multiplatform.
 */
expect class LocationProvider {
    /**
     * Gets the last known location of the device.
     *
     * @return The current location as String or as empty String if not available.
     */
    suspend fun getLocation(): String?

//    suspend fun getCurrentLocation(): String?
}