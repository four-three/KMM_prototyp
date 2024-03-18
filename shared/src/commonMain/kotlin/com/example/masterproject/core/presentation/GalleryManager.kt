package com.example.masterproject.core.presentation

import androidx.compose.runtime.Composable

@Composable
expect fun createGalleryManager(): GalleryManager
expect class GalleryManager {
    @Composable
    fun registerGalleryManager(onImagePicked: (ByteArray) -> Unit)
    fun pickImage()
}