package com.example.masterproject.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.interop.LocalUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.UIKit.UIViewController
import platform.darwin.NSObject
import platform.posix.memcpy

@Composable
actual fun createGalleryManager(): GalleryManager {
    val rootController: UIViewController = LocalUIViewController.current
    return remember {
        GalleryManager(rootController)
    }
}

actual class GalleryManager(
    private val rootController: UIViewController
) {
    /**
     * "sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary"
     * says apple that we want to look into the phones photo library
     */
    val imagePickerController = UIImagePickerController().apply {
        sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary
    }
    private var onImagePicked: (ByteArray) -> Unit = {}
    private lateinit var delegate: UINavigationControllerDelegateProtocol

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    actual fun registerGalleryManager(onImagePicked: (ByteArray) -> Unit) {
        this.onImagePicked = onImagePicked
        delegate = remember {
            object : NSObject(), UIImagePickerControllerDelegateProtocol,
                UINavigationControllerDelegateProtocol {
                override fun imagePickerController(
                    picker: UIImagePickerController,
                    didFinishPickingImage: UIImage,
                    editingInfo: Map<Any?, *>?
                ) {
                    val imageNsData =
                        UIImageJPEGRepresentation(didFinishPickingImage, 0.99) ?: return
                    val bytes = ByteArray(imageNsData.length.toInt())
                    memcpy(bytes.refTo(0), imageNsData.bytes, imageNsData.length)

                    onImagePicked(bytes)

                    picker.dismissViewControllerAnimated(true, null)
                }

                override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
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
}