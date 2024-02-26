package com.example.masterproject.core.presentation

import androidx.compose.runtime.Composable


interface PermissionHandler {
    @Composable
    fun askPermission(permission: PermissionType)

    @Composable
    fun isPermissionGranted(permission: PermissionType): Boolean

    @Composable
    fun launchSettings()

    //--------------------------------- Location ---------------------------------
    @Composable
    fun askForLocationPermission(permission: PermissionLocationType)

    @Composable
    fun isLocationPermissionGranted(permission: PermissionLocationType): Boolean

}
expect class Permissions(callback: PermissionCallback) : PermissionHandler

interface PermissionCallback {
    fun onPermissionStatus(permissionType: PermissionType, status: PermissionStatus)

    //--------------------------------- Location ---------------------------------
    fun onPermissionLocationStatus(permissionType: PermissionLocationType, status: PermissionStatus)
}

@Composable
expect fun createPermissions(callback: PermissionCallback): Permissions

enum class PermissionStatus {
    GRANTED,
    DENIED,
    SHOW_RATIONAL
}

enum class PermissionType {
    CAMERA,
    GALLERY
}

enum class PermissionLocationType {
    LOCATION_SERVICE_ON,
    LOCATION_FOREGROUND,
    LOCATION_BACKGROUND
}