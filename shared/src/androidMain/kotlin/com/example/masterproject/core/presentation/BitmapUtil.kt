package com.example.masterproject.core.presentation

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream
import java.io.InputStream

// also known as "toImageBitmap"
@Composable
actual fun rememberBitmapFromBytes(bytes: ByteArray?): ImageBitmap? {
    return remember(bytes) {
        if (bytes != null) {
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size).asImageBitmap()
        } else {
            null
        }
    }
}

fun getBitmapFromUri(uri: Uri, contentResolver: ContentResolver): android.graphics.Bitmap? {
    var inputStream: InputStream? = null
    try {
        inputStream = contentResolver.openInputStream(uri)
        val s = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()
        return s
    } catch (e: Exception) {
        e.printStackTrace()
        println("getBitmapFromUri Exception: ${e.message}")
        println("getBitmapFromUri Exception: ${e.localizedMessage}")
        return null
    }
}

fun toByteArray(bitmap: Bitmap): ByteArray? {
    return if (bitmap != null) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        @Suppress("MagicNumber") bitmap.compress(
            android.graphics.Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream
        )
        byteArrayOutputStream.toByteArray()
    } else {
        println("toByteArray null")
        null
    }
}

