package com.example.masterproject.core.presentation

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.util.UUID

@Composable
actual fun createCameraManager(): CameraManager {
    return remember() {
        CameraManager()
    }
}

actual class CameraManager() {
    private lateinit var tempPhotoUri: Uri
    private lateinit var launcher: ActivityResultLauncher<Uri>

    @Composable
    actual fun registerCameraManager(onImagePicked: (ByteArray) -> Unit) {
        val context = LocalContext.current
        val contentResolver: ContentResolver = context.contentResolver
        tempPhotoUri = remember {
            val fileName = UUID.randomUUID().toString()
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
        }

        launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = { success ->
                if (success) {
                    onImagePicked(toByteArray(getBitmapFromUri(tempPhotoUri, contentResolver)!!)!!)
                }
            }
        )
    }
    actual fun takeImage() {
        launcher.launch(tempPhotoUri)
    }
}