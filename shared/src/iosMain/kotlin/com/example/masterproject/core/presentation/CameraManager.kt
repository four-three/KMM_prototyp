package com.example.masterproject.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.interop.LocalUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerCameraCaptureMode
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.UIKit.UIViewController
import platform.darwin.NSObject
import platform.posix.memcpy


@Composable
actual fun createCameraManager(): CameraManager {
    val rootController: UIViewController = LocalUIViewController.current
    return remember {
        CameraManager(rootController)
    }
}
actual class CameraManager(
    private val rootController: UIViewController
) {
    /**
     * "sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary"
     * says apple that we want to look into the phones photo library
     */
    private val imagePickerController = UIImagePickerController().apply {
        sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
        cameraCaptureMode = UIImagePickerControllerCameraCaptureMode.UIImagePickerControllerCameraCaptureModePhoto
    }
    private var onImagePicked: (ByteArray) -> Unit = {}
    private lateinit var delegate: UINavigationControllerDelegateProtocol

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    actual fun registerCameraManager(onImagePicked: (ByteArray) -> Unit) {
        this.onImagePicked = onImagePicked
        delegate = object : NSObject(), UIImagePickerControllerDelegateProtocol,
            UINavigationControllerDelegateProtocol {
                override fun imagePickerController(
                    picker: UIImagePickerController,
                    didFinishPickingMediaWithInfo: Map<Any?, *>
                ) {
                    val image = UIImageJPEGRepresentation(
                        didFinishPickingMediaWithInfo.getValue(UIImagePickerControllerOriginalImage) as UIImage,
                        0.99
                    ) ?: return
//                    UIImageJPEGRepresentation(didFinishPickingMediaWithInfo.getValue(UIImagePickerControllerOriginalImage) as UIImage, 0.99)
                    val bytes = ByteArray(image.length.toInt())
                    memcpy(bytes.refTo(0), image.bytes, image.length)
                    onImagePicked(bytes)
                    picker.dismissViewControllerAnimated(true, null)
                }

                override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
                    picker.dismissViewControllerAnimated(true, null)
                }
            }
    }

    actual fun takeImage() {
//        imagePickerController.setCameraCaptureMode(UIImagePickerControllerCameraCaptureMode.UIImagePickerControllerCameraCaptureModePhoto)
        //imagePickerController.setDelegate(delegate)
//        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
//            imagePickerController, true, null
//        )
        rootController.presentViewController(imagePickerController, true) {
            imagePickerController.delegate = delegate
        }
    }

}