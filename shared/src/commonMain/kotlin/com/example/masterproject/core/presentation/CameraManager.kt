package com.example.masterproject.core.presentation

import androidx.compose.runtime.Composable

@Composable
expect fun createCameraManager(): CameraManagerOld
expect class CameraManagerOld {
    @Composable
    fun registerCameraManager(onImagePicked: (ByteArray) -> Unit)
    fun takeImage()
}