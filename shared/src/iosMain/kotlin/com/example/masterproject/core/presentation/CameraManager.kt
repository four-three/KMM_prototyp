package com.example.masterproject.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerCameraCaptureMode
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerEditedImage
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.darwin.NSObject


@Composable
actual fun createCameraManager(): CameraManager {
    return remember {
        CameraManager()
    }
}
actual class CameraManager() {
    /**
     * "sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary"
     * says apple that we want to look into the phones photo library
     */
    private val imagePickerController = UIImagePickerController().apply {
        sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
    }
    private var onImagePicked: (ByteArray) -> Unit = {}
    private lateinit var delegate: UINavigationControllerDelegateProtocol

    @Composable
    actual fun registerCameraManager(onImagePicked: (ByteArray) -> Unit) {
        this.onImagePicked = onImagePicked
        delegate = remember {
            object : NSObject(), UIImagePickerControllerDelegateProtocol,
                UINavigationControllerDelegateProtocol {
                override fun imagePickerController(
                    picker: UIImagePickerController, didFinishPickingMediaWithInfo: Map<Any?, *>
                ) {
                    val image =
                        didFinishPickingMediaWithInfo.getValue(UIImagePickerControllerEditedImage) as? UIImage
                            ?: didFinishPickingMediaWithInfo.getValue(
                                UIImagePickerControllerOriginalImage
                            ) as? UIImage
                    onImagePicked(toByteArray(image)!!)
                    picker.dismissViewControllerAnimated(true, null)
                }
            }
        }
    }

    actual fun takeImage() {
        imagePickerController.setSourceType(UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera)
        imagePickerController.setAllowsImageEditing(true)
        imagePickerController.setCameraCaptureMode(UIImagePickerControllerCameraCaptureMode.UIImagePickerControllerCameraCaptureModePhoto)
        imagePickerController.setDelegate(delegate)
        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
            imagePickerController, true, null
        )
//        rootController.presentViewController(imagePickerController, true) {
//            imagePickerController.delegate = delegate
//        }
    }

}