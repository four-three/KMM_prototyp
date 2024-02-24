package com.example.masterproject.core.presentation

import androidx.compose.runtime.Composable

actual class PermissionsFactory {
    @Composable
    actual fun createPermission(callback: PermissionCallback): Permissions {
        return Permissions(callback)
    }
}