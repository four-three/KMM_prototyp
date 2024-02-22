package com.example.masterproject.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerCameraCaptureMode
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerEditedImage
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.UIKit.UIViewController
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
actual class ImagePicker(
    private val rootController: UIViewController
) {
    /**
     * "sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary"
     * says apple that we want to loook into the phone photo library
     */
    private val imagePickerController = UIImagePickerController().apply {
        //sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary
        sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
    }
    private var onImagePicked: (ByteArray) -> Unit = {}
    private lateinit var delegate: UINavigationControllerDelegateProtocol

//    private object delegate: NSObject(), UIImagePickerControllerDelegateProtocol,
//        UINavigationControllerDelegateProtocol
//    private val delegate = object : NSObject(), UIImagePickerControllerDelegateProtocol,
//        UINavigationControllerDelegateProtocol {
//
//        override fun imagePickerController(
//            picker: UIImagePickerController,
//            didFinishPickingImage: UIImage,
//            editingInfo: Map<Any?, *>?
//        ) {
//            val imageNsData = UIImageJPEGRepresentation(didFinishPickingImage, 1.0) ?: return
//            val bytes = ByteArray(imageNsData.length.toInt())
//            memcpy(bytes.refTo(0), imageNsData.bytes, imageNsData.length)
//
//            onImagePicked(bytes)
//
//            picker.dismissViewControllerAnimated(true, null)
//        }
//
//
//        override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
//            picker.dismissViewControllerAnimated(true, null)
//        }
//    }
//------------------------------------------------------- for image selection via gallery
//    @Composable
//    actual fun registerPicker(onImagePicked: (ByteArray) -> Unit) {
//        this.onImagePicked = onImagePicked
//    delegate = object : NSObject(), UIImagePickerControllerDelegateProtocol,
//        UINavigationControllerDelegateProtocol {
//
//        override fun imagePickerController(
//            picker: UIImagePickerController,
//            didFinishPickingImage: UIImage,
//            editingInfo: Map<Any?, *>?
//        ) {
//            val imageNsData = UIImageJPEGRepresentation(didFinishPickingImage, 1.0) ?: return
//            val bytes = ByteArray(imageNsData.length.toInt())
//            memcpy(bytes.refTo(0), imageNsData.bytes, imageNsData.length)
//
//            onImagePicked(bytes)
//
//            picker.dismissViewControllerAnimated(true, null)
//        }
//
//
//        override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
//            picker.dismissViewControllerAnimated(true, null)
//        }
//    }
//    }
// -------------------------------------------------------

    @Composable
    actual fun registerPicker(onImagePicked: (ByteArray) -> Unit) {
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

    actual fun pickImage() {
        rootController.presentViewController(imagePickerController, true) {
            imagePickerController.delegate = delegate
        }
    }

    actual fun takeImage() {
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