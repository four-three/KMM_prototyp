package com.example.masterproject.core.presentation

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun createGalleryManager(): GalleryManagerOld {
    val activity = LocalContext.current as ComponentActivity
    return remember(activity) {
        GalleryManagerOld(activity)
    }
}

actual class GalleryManagerOld(
    private val activity: ComponentActivity
) {
    private lateinit var getContent: ActivityResultLauncher<String>
    @Composable
    actual fun registerGalleryManager(onImagePicked: (ByteArray) -> Unit) {
        getContent = rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            uri?.let {
                activity.contentResolver.openInputStream(uri)?.use {
                    onImagePicked(it.readBytes())
                }
            }
        }
    }

    actual fun pickImage() {
        getContent.launch("image/*")
    }
}