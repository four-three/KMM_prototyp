package com.example.masterproject.core.presentation

import androidx.compose.runtime.Composable


interface PermissionHandler {
    @Composable
    fun askPermission(permission: PermissionType)

    @Composable
    fun isPermissionGranted(permission: PermissionType): Boolean

    @Composable
    fun launchSettings()

}
expect class Permissions(callback: PermissionCallback) : PermissionHandler

interface PermissionCallback {
    fun onPermissionStatus(permissionType: PermissionType, status: PermissionStatus)
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