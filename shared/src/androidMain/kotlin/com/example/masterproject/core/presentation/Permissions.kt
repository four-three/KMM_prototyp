package com.example.masterproject.core.presentation

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch

@Composable
actual fun createPermissions(callback: PermissionCallback): Permissions {
    return Permissions(callback)
}
actual class Permissions actual constructor(private val callback: PermissionCallback) :
    PermissionHandler {
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    override fun askPermission(permission: PermissionType) {
        val lifecycleOwner = LocalLifecycleOwner.current

        when (permission) {
            PermissionType.CAMERA -> {
                val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
                LaunchedEffect(cameraPermissionState) {
                    val permissionResult = cameraPermissionState.status
                    if (!permissionResult.isGranted) {
                        if (permissionResult.shouldShowRationale) {
                            callback.onPermissionStatus(
                                permission, PermissionStatus.SHOW_RATIONAL
                            )
                        } else {
                            lifecycleOwner.lifecycleScope.launch {
                                cameraPermissionState.launchPermissionRequest()
                            }
                        }
                    } else {
                        callback.onPermissionStatus(
                            permission, PermissionStatus.GRANTED
                        )
                    }
                }
            }

            PermissionType.GALLERY -> {
                // Granted by default because in Android GetContent API does not require any
                // runtime permissions, and i am using it to access gallery in my app
                callback.onPermissionStatus(
                    permission, PermissionStatus.GRANTED
                )
            }
        }
    }


    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean {
        return when (permission) {
            PermissionType.CAMERA -> {
                val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
                cameraPermissionState.status.isGranted
            }

            PermissionType.GALLERY -> {
                // Granted by default because in Android GetContent API does not require any
                // runtime permissions, and i am using it to access gallery in my app
                true
            }
        }
    }

    @Composable
    override fun launchSettings() {
        val context = LocalContext.current
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        ).also {
            context.startActivity(it)
        }
    }

    //----------------------------------------- Location -------------------------------------------
    //----------------------------------------------------------------------------------------------

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    override fun askForLocationPermission(permission: PermissionLocationType) {
        val lifecycleOwner = LocalLifecycleOwner.current
        when (permission) {
            PermissionLocationType.LOCATION_SERVICE_ON -> {
                val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

                LaunchedEffect(locationPermissionState) {
                    val permissionResult = locationPermissionState.status
                    if (!permissionResult.isGranted) {
                        if (permissionResult.shouldShowRationale) {
                            callback.onPermissionLocationStatus(
                                permission,
                                PermissionStatus.SHOW_RATIONAL
                            )
                        } else {
                            lifecycleOwner.lifecycleScope.launch {
                                locationPermissionState.launchPermissionRequest()
                            }
                        }
                    } else {
                        callback.onPermissionLocationStatus(
                            permission, PermissionStatus.GRANTED
                        )
                    }
                }
            }
            PermissionLocationType.LOCATION_FOREGROUND -> {
                val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_COARSE_LOCATION)

                LaunchedEffect(locationPermissionState) {
                    val permissionResult = locationPermissionState.status
                    if (!permissionResult.isGranted) {
                        if (permissionResult.shouldShowRationale) {
                            callback.onPermissionLocationStatus(
                                permission,
                                PermissionStatus.SHOW_RATIONAL
                            )
                        } else {
                            lifecycleOwner.lifecycleScope.launch {
                                locationPermissionState.launchPermissionRequest()
                            }
                        }
                    } else {
                        callback.onPermissionLocationStatus(
                            permission, PermissionStatus.GRANTED
                        )
                    }
                }
            }
            PermissionLocationType.LOCATION_BACKGROUND -> {
                val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_BACKGROUND_LOCATION)

                LaunchedEffect(locationPermissionState) {
                    val permissionResult = locationPermissionState.status
                    if (!permissionResult.isGranted) {
                        if (permissionResult.shouldShowRationale) {
                            callback.onPermissionLocationStatus(
                                permission,
                                PermissionStatus.SHOW_RATIONAL
                            )
                        } else {
                            lifecycleOwner.lifecycleScope.launch {
                                locationPermissionState.launchPermissionRequest()
                            }
                        }
                    } else {
                        callback.onPermissionLocationStatus(
                            permission, PermissionStatus.GRANTED
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    override fun isLocationPermissionGranted(permission: PermissionLocationType): Boolean {
        return when (permission) {
            PermissionLocationType.LOCATION_SERVICE_ON -> {
                val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
                locationPermissionState.status.isGranted
            }
            PermissionLocationType.LOCATION_FOREGROUND -> {
                val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_COARSE_LOCATION)
                locationPermissionState.status.isGranted
            }
            PermissionLocationType.LOCATION_BACKGROUND -> {
                val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                locationPermissionState.status.isGranted
            }
        }
    }
}