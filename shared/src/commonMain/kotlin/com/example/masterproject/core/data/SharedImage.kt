package com.example.masterproject.core.data

import androidx.compose.ui.graphics.ImageBitmap

//TODO: combine with ImageStorage
expect class SharedImage {
    fun toByteArray(): ByteArray?
    fun toImageBitmap(): ImageBitmap?

}