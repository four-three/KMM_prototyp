package com.example.masterproject.core.presentation

import androidx.compose.runtime.Composable

@Composable
expect fun createPicker(): ImagePicker
expect class ImagePicker {
    @Composable
    fun registerPicker(onImagePicked: (ByteArray) -> Unit)

    fun pickImage()

    fun takeImage()

}