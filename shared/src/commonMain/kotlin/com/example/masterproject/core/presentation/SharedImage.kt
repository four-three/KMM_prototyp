package com.example.masterproject.core.presentation

import androidx.compose.ui.graphics.ImageBitmap

expect class SharedImage {
    fun toByteArray(): ByteArray?
    fun toImageBitmap(): ImageBitmap?
}