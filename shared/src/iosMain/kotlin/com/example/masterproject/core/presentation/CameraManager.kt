package com.example.masterproject.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.interop.LocalUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUUID
import platform.Foundation.NSUserDomainMask
import platform.Foundation.writeToFile
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerCameraCaptureMode
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerEditedImage
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

    private var onImagePicked: (ByteArray) -> Unit = {}
    private lateinit var delegate: UINavigationControllerDelegateProtocol

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    actual fun registerCameraManager(onImagePicked: (ByteArray) -> Unit) {
        this.onImagePicked = onImagePicked
        delegate = remember {
            object : NSObject(), UIImagePickerControllerDelegateProtocol,
                UINavigationControllerDelegateProtocol {
                override fun imagePickerController(
                    picker: UIImagePickerController,
                    didFinishPickingMediaWithInfo: Map<Any?, *>
                ) {

                    val imageNSData = UIImageJPEGRepresentation(
                        didFinishPickingMediaWithInfo.getValue(UIImagePickerControllerOriginalImage) as UIImage,
                        1.0
                    ) ?: UIImageJPEGRepresentation(
                        didFinishPickingMediaWithInfo.getValue(
                            UIImagePickerControllerEditedImage
                        ) as UIImage, 1.0
                    )
                    val bytes = ByteArray(imageNSData!!.length.toInt())
                    memcpy(bytes.refTo(0), imageNSData!!.bytes, imageNSData!!.length)
                    onImagePicked(bytes)
                    picker.dismissViewControllerAnimated(true, null)
                    val path = NSSearchPathForDirectoriesInDomains(
                        NSDocumentDirectory,
                        NSUserDomainMask,
                        true
                    )[0] as String
                    val filePath = "$path/" + NSUUID.UUID().UUIDString + ".jpg"
                    imageNSData.writeToFile(filePath, atomically = true)
                }


                override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
                    picker.dismissViewControllerAnimated(true, null)
                }
            }
        }
    }

    actual fun takeImage() {
        val imagePickerController = UIImagePickerController().apply {
            allowsEditing = true
            sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
            cameraCaptureMode = UIImagePickerControllerCameraCaptureMode.UIImagePickerControllerCameraCaptureModePhoto
        }
//        imagePickerController.setCameraCaptureMode(UIImagePickerControllerCameraCaptureMode.UIImagePickerControllerCameraCaptureModePhoto)
//        imagePickerController.setDelegate(delegate)
//        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
//            imagePickerController, true, null
//        )
        rootController.presentViewController(imagePickerController, true) {
            imagePickerController.delegate = delegate
        }

    }

}