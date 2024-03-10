package com.example.masterproject.core.presentation

import androidx.compose.runtime.Composable

@Composable
expect fun createCameraManager(): CameraManagerOld
expect class CameraManagerOld {
    @Composable
    fun registerCameraManager(onImagePicked: (ByteArray) -> Unit)
    fun takeImage()
}

// ------------------------------------------------------------------------------------------

@Composable
expect fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager


expect class CameraManager(
    onLaunch: () -> Unit
) {
    fun launch()
}