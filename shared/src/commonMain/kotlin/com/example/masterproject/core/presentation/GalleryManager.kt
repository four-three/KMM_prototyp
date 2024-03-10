package com.example.masterproject.core.presentation

import androidx.compose.runtime.Composable

@Composable
expect fun createGalleryManager(): GalleryManagerOld
expect class GalleryManagerOld {
    @Composable
    fun registerGalleryManager(onImagePicked: (ByteArray) -> Unit)
    fun pickImage()
}

// ------------------------------------------------------------------------------------------
@Composable
expect fun rememberGalleryManager(onResult: (SharedImage?) -> Unit): GalleryManager

expect class GalleryManager(
    onLaunch: () -> Unit
) {
    fun launch()
}