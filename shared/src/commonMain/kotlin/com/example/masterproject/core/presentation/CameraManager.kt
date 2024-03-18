package com.example.masterproject.core.presentation

import androidx.compose.runtime.Composable

@Composable
expect fun createCameraManager(): CameraManager
expect class CameraManager {
    @Composable
    fun registerCameraManager(onImagePicked: (ByteArray) -> Unit)
    fun takeImage()
}