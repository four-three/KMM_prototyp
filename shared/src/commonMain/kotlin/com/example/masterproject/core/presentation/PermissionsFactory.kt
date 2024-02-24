package com.example.masterproject.core.presentation

import androidx.compose.runtime.Composable

expect class PermissionsFactory {
    @Composable
    fun createPermission(callback: PermissionCallback): Permissions
}