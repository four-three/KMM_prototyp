package com.example.masterproject.core.presentation

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun createPicker(): ImagePicker {
    val activity = LocalContext.current as ComponentActivity
    return remember(activity) {
        ImagePicker(activity)
    }
}

actual class ImagePicker(
    private val activity: ComponentActivity
) {
    private lateinit var tempPhotoUri: Uri
    private lateinit var launcher: ActivityResultLauncher<Uri>

//------------------------------------------------------- for image selection via gallery
    private lateinit var getContent: ActivityResultLauncher<String>
    @Composable
    actual fun registerPicker(onImagePicked: (ByteArray) -> Unit) {
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
//-------------------------------------------------------


//    @Composable
//    actual fun registerPicker(onImagePicked: (ByteArray) -> Unit) {
//        val context = LocalContext.current
//        val contentResolver: ContentResolver = context.contentResolver
//        tempPhotoUri = remember {
//            val fileName = "temp_photo"
//            val contentValues = ContentValues().apply {
//                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
//                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
//            }
//            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
//        }
//
//        launcher = rememberLauncherForActivityResult(
//            contract = ActivityResultContracts.TakePicture(),
//            onResult = { success ->
//                if (success) {
//                    onImagePicked(toByteArray(getBitmapFromUri(tempPhotoUri, contentResolver)!!)!!)
//                }
//            }
//        )
////            contract = ActivityResultContracts.StartActivityForResult()) { result ->
////            if (result.resultCode == Activity.RESULT_OK) {
////                val data: Intent? = result.data
////                data?.data?.let { uri ->
////                    val bitmap = getBitmapFromUri(uri, activity.contentResolver)
////                    if(bitmap != null) {
////                        val byteArray = bitmapToByteArray(bitmap)
////                        onImagePicked(byteArray)
////                    }
////                }
////            }
////        }
////        intent = remember {
////            //Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
////            Intent(MediaStore.ACTION_IMAGE_CAPTURE)
////        }
//    }
    actual fun pickImage() {
        getContent.launch("image/*")
    }

    actual fun takeImage() {
        launcher.launch(tempPhotoUri)
    }
}