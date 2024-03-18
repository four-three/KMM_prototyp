package com.example.masterproject.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUUID
import platform.Foundation.NSUserDomainMask
import platform.Foundation.writeToFile
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerCameraCaptureMode
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.darwin.NSObject
import platform.posix.memcpy


@Composable
actual fun createCameraManager(): CameraManager {
    return remember {
        CameraManager()
    }
}
actual class CameraManager {
    private var onImagePicked: (ByteArray) -> Unit = {}

    @Composable
    actual fun registerCameraManager(onImagePicked: (ByteArray) -> Unit) {
        this.onImagePicked = onImagePicked
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun takeImage() {
        val picker = UIImagePickerController().apply {
            sourceType =  UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
            cameraCaptureMode = UIImagePickerControllerCameraCaptureMode.UIImagePickerControllerCameraCaptureModePhoto
            delegate = object : NSObject(), UIImagePickerControllerDelegateProtocol,
                UINavigationControllerDelegateProtocol {
                override fun imagePickerController(
                    picker: UIImagePickerController,
                    didFinishPickingMediaWithInfo: Map<Any?, *>
                ) {
                    val originalImage = didFinishPickingMediaWithInfo.getValue(
                        UIImagePickerControllerOriginalImage
                    ) as? UIImage

                    originalImage?.let { image ->
                        // Convert image to JPEG data
                        val data = UIImageJPEGRepresentation(image, 1.0)
                        // Save to documents directory
                        val path = NSSearchPathForDirectoriesInDomains(
                            NSDocumentDirectory,
                            NSUserDomainMask,
                            true
                        ).first().toString()
                        val filePath = "$path/" + NSUUID.UUID().UUIDString + ".jpg"
                        data?.writeToFile(filePath, atomically = true)

                        // Convert to bytes
                        val bytes = ByteArray(data!!.length.toInt())
                        //data?.copyBytes(to: bytes)
                        memcpy(bytes.refTo(0), data!!.bytes, data!!.length)

                        // Return image bytes
                        onImagePicked(bytes)
                    }
                    picker.dismissViewControllerAnimated(true, null)
                }
            }
        }
        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
            picker, true, null
        )
    }
}