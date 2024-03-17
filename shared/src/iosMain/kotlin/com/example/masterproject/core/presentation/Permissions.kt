package com.example.masterproject.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.AVFoundation.AVAuthorizationStatus
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusDenied
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.CoreLocation.kCLAuthorizationStatusRestricted
import platform.Foundation.NSURL
import platform.Photos.PHAuthorizationStatus
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusDenied
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHPhotoLibrary
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

@Composable
actual fun createPermissions(callback: PermissionCallback): Permissions {
    return Permissions(callback)
}
actual class Permissions actual constructor(private val callback: PermissionCallback) :
    PermissionHandler {
    @Composable
    override fun askPermission(permission: PermissionType) {
        when (permission) {
            PermissionType.CAMERA -> {
                val status: AVAuthorizationStatus =
                    remember { AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo) }
                askCameraPermission(status, permission, callback)
            }

            PermissionType.GALLERY -> {
                val status: PHAuthorizationStatus =
                    remember { PHPhotoLibrary.authorizationStatus() }
                askGalleryPermission(status, permission, callback)
            }
        }
    }

    private fun askCameraPermission(
        status: AVAuthorizationStatus, permission: PermissionType, callback: PermissionCallback
    ) {
        when (status) {
            AVAuthorizationStatusAuthorized -> {
                callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
            }

            AVAuthorizationStatusNotDetermined -> {
                return AVCaptureDevice.Companion.requestAccessForMediaType(AVMediaTypeVideo) { isGranted ->
                    if (isGranted) {
                        callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
                    } else {
                        callback.onPermissionStatus(permission, PermissionStatus.DENIED)
                    }
                }
            }

            AVAuthorizationStatusDenied -> {
                callback.onPermissionStatus(permission, PermissionStatus.DENIED)
            }

            else -> error("unknown camera status $status")
        }
    }

    private fun askGalleryPermission(
        status: PHAuthorizationStatus, permission: PermissionType, callback: PermissionCallback
    ) {
        when (status) {
            PHAuthorizationStatusAuthorized -> {
                callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
            }

            PHAuthorizationStatusNotDetermined -> {
                PHPhotoLibrary.Companion.requestAuthorization { newStatus ->
                    askGalleryPermission(newStatus, permission, callback)
                }
            }

            PHAuthorizationStatusDenied -> {
                callback.onPermissionStatus(
                    permission, PermissionStatus.DENIED
                )
            }

            else -> error("unknown gallery status $status")
        }
    }


    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean {
        return when (permission) {
            PermissionType.CAMERA -> {
                val status: AVAuthorizationStatus =
                    remember { AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo) }
                status == AVAuthorizationStatusAuthorized
            }

            PermissionType.GALLERY -> {
                val status: PHAuthorizationStatus =
                    remember { PHPhotoLibrary.authorizationStatus() }
                status == PHAuthorizationStatusAuthorized
            }
        }
    }

    @Composable
    override fun launchSettings() {
        NSURL.URLWithString(UIApplicationOpenSettingsURLString)?.let {
            UIApplication.sharedApplication.openURL(it)
        }
    }

    //--------------------------------------- Location ---------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Composable
    override fun askForLocationPermission(permission: PermissionLocationType) {
        //TODO: could be squashed into one function
        var locationManager = CLLocationManager()
        when (permission) {
            PermissionLocationType.LOCATION_SERVICE_ON -> {
                val status: CLAuthorizationStatus =
                    remember { locationManager.authorizationStatus }
                askLocationOnPermission(status, PermissionLocationType.LOCATION_SERVICE_ON, callback, locationManager)
            }

            PermissionLocationType.LOCATION_FOREGROUND -> {
                val status: CLAuthorizationStatus =
                    remember { locationManager.authorizationStatus }
                askLocationForegroundPermission(status, PermissionLocationType.LOCATION_FOREGROUND, callback, locationManager)
            }

            PermissionLocationType.LOCATION_BACKGROUND -> {
                val status: CLAuthorizationStatus =
                    remember { locationManager.authorizationStatus }
                askLocationBackgroundPermission(status, PermissionLocationType.LOCATION_BACKGROUND, callback, locationManager)
            }
        }
    }

    private fun askLocationOnPermission(
        status: CLAuthorizationStatus, permission: PermissionLocationType, callback: PermissionCallback, locationManager: CLLocationManager
    ) {
        when (status) {
            kCLAuthorizationStatusRestricted -> {
                callback.onPermissionLocationStatus(permission, PermissionStatus.GRANTED)
            }

            kCLAuthorizationStatusNotDetermined -> {
                locationManager.requestWhenInUseAuthorization()
            }

            kCLAuthorizationStatusDenied -> {
                callback.onPermissionLocationStatus(permission, PermissionStatus.DENIED)
            }

            else -> error("unknown location status $status")
        }
    }

    private fun askLocationForegroundPermission(
        status: CLAuthorizationStatus, permission: PermissionLocationType, callback: PermissionCallback, locationManager: CLLocationManager
    ) {
        when (status) {
            kCLAuthorizationStatusAuthorizedWhenInUse -> {
                callback.onPermissionLocationStatus(permission, PermissionStatus.GRANTED)
            }

            kCLAuthorizationStatusNotDetermined -> {
                locationManager.requestWhenInUseAuthorization()
            }

            kCLAuthorizationStatusDenied -> {
                callback.onPermissionLocationStatus(permission, PermissionStatus.DENIED)
            }

            else -> error("unknown location status $status")
        }
    }

    private fun askLocationBackgroundPermission(
        status: CLAuthorizationStatus, permission: PermissionLocationType, callback: PermissionCallback, locationManager: CLLocationManager
    ) {
        when (status) {
            kCLAuthorizationStatusAuthorizedAlways -> {
                callback.onPermissionLocationStatus(permission, PermissionStatus.GRANTED)
            }

            kCLAuthorizationStatusNotDetermined -> {
                locationManager.requestAlwaysAuthorization()
            }

            kCLAuthorizationStatusDenied -> {
                callback.onPermissionLocationStatus(permission, PermissionStatus.DENIED)
            }

            else -> error("unknown location status $status")
        }
    }

    @Composable
    override fun isLocationPermissionGranted(permission: PermissionLocationType): Boolean {
        return when (permission) {
            PermissionLocationType.LOCATION_SERVICE_ON -> {
                val status: CLAuthorizationStatus =
                    remember { CLLocationManager.authorizationStatus() }
                status == kCLAuthorizationStatusRestricted
            }

            PermissionLocationType.LOCATION_FOREGROUND -> {
                val status: CLAuthorizationStatus =
                    remember { CLLocationManager.authorizationStatus() }
                status == kCLAuthorizationStatusAuthorizedWhenInUse
            }

            PermissionLocationType.LOCATION_BACKGROUND -> {
                val status: CLAuthorizationStatus =
                    remember { CLLocationManager.authorizationStatus() }
                status == kCLAuthorizationStatusAuthorizedAlways
            }
        }
    }

}