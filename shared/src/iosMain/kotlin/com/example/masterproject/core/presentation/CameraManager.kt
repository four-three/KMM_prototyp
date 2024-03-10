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
import platform.UIKit.UIApplication
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
actual fun createCameraManager(): CameraManagerOld {
    val rootController: UIViewController = LocalUIViewController.current
    return remember {
        CameraManagerOld(rootController)
    }
}
actual class CameraManagerOld(
    private val rootController: UIViewController
) {
//    val imagePickerController = UIImagePickerController().apply {
////            allowsEditing = true
//        sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
//        cameraCaptureMode = UIImagePickerControllerCameraCaptureMode.UIImagePickerControllerCameraCaptureModePhoto
//    }
    private var onImagePicked: (ByteArray) -> Unit = {}
    //private lateinit var delegate: UINavigationControllerDelegateProtocol


    @Composable
    actual fun registerCameraManager(onImagePicked: (ByteArray) -> Unit) {
        this.onImagePicked = onImagePicked
    }

//    @OptIn(ExperimentalForeignApi::class)
//    @Composable
//    actual fun registerCameraManager(onImagePicked: (ByteArray) -> Unit) {
//        this.onImagePicked = onImagePicked
//        delegate = remember {
//            object : NSObject(), UIImagePickerControllerDelegateProtocol,
//                UINavigationControllerDelegateProtocol {
//                override fun imagePickerController(
//                    picker: UIImagePickerController,
//                    didFinishPickingMediaWithInfo: Map<Any?, *>
//                ) {
//
//                    val imageNSData = UIImageJPEGRepresentation(
//                        didFinishPickingMediaWithInfo.getValue(UIImagePickerControllerOriginalImage) as UIImage,
//                        1.0
//                    ) ?: UIImageJPEGRepresentation(
//                        didFinishPickingMediaWithInfo.getValue(
//                            UIImagePickerControllerEditedImage
//                        ) as UIImage, 1.0
//                    )
//                    val bytes = ByteArray(imageNSData!!.length.toInt())
//                    memcpy(bytes.refTo(0), imageNSData!!.bytes, imageNSData!!.length)
//
//                    val path = NSSearchPathForDirectoriesInDomains(
//                        NSDocumentDirectory,
//                        NSUserDomainMask,
//                        true
//                    )[0] as String
//                    val filePath = "$path/" + NSUUID.UUID().UUIDString + ".jpg"
//                    imageNSData.writeToFile(filePath, atomically = true)
//                    onImagePicked(bytes)
//                    picker.dismissViewControllerAnimated(true, null)
//                }
//
//
//                override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
//                    picker.dismissViewControllerAnimated(true, null)
//                }
//            }
//        }
//    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun takeImage() {

        val picker = UIImagePickerController().apply {
            sourceType =  UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
            delegate = object : NSObject(), UIImagePickerControllerDelegateProtocol,
                UINavigationControllerDelegateProtocol {

                override fun imagePickerController(
                    picker: UIImagePickerController,
                    didFinishPickingMediaWithInfo: Map<Any?, *>
                ) {

                    val originalImage = didFinishPickingMediaWithInfo.getValue(UIImagePickerControllerOriginalImage) as? UIImage

                    originalImage?.let { image ->

                        // Convert image to JPEG data
                        val data = UIImageJPEGRepresentation(image, 1.0)

                        // Save to documents directory
                        val path = NSSearchPathForDirectoriesInDomains(
                        NSDocumentDirectory,
                        NSUserDomainMask,
                        true
                    )[0] as String
                        val filePath = "$path/" + NSUUID.UUID().UUIDString + ".jpg"
                    data?.writeToFile(filePath, atomically = true)
//                        val filename = NSUUID.UUID().UUIDString + ".jpg"
//                        val documentsUrl = FileManager.default.urls(
//                            for(NSDocumentDirectory in NSUserDomainMask)
//                        ).first()
//                        val fileUrl = documentsUrl.appendingPathComponent(filename)
//                        data?.writeToFile(fileUrl.path, atomically = true)

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
//        imagePickerController.setCameraCaptureMode(UIImagePickerControllerCameraCaptureMode.UIImagePickerControllerCameraCaptureModePhoto)
//        imagePickerController.setDelegate(delegate)
        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
            picker, true, null
        )
//        rootController.presentViewController(imagePickerController, true) {
//            imagePickerController.delegate = delegate
//        }
    }
}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager {
    val imagePicker = UIImagePickerController()
    val cameraDelegate = remember {
        object : NSObject(), UIImagePickerControllerDelegateProtocol,
            UINavigationControllerDelegateProtocol {
            override fun imagePickerController(
                picker: UIImagePickerController, didFinishPickingMediaWithInfo: Map<Any?, *>
            ) {
                val image = didFinishPickingMediaWithInfo.getValue(
                    UIImagePickerControllerEditedImage
                ) as? UIImage ?: didFinishPickingMediaWithInfo.getValue(
                    UIImagePickerControllerOriginalImage
                ) as? UIImage


                onResult.invoke(SharedImage(image))
                picker.dismissViewControllerAnimated(true, null)
            }
            override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
                picker.dismissViewControllerAnimated(true, null)
            }
        }
    }
    return remember {
        CameraManager {
            imagePicker.setSourceType(UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera)
//            imagePicker.setAllowsEditing(true)
//            imagePicker.setMediaTypes(listOf("public.image"))
            imagePicker.setCameraCaptureMode(UIImagePickerControllerCameraCaptureMode.UIImagePickerControllerCameraCaptureModePhoto)
            imagePicker.setDelegate(cameraDelegate)
            UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
                imagePicker, true, null
            )
        }
    }
}

actual class CameraManager actual constructor(
    private val onLaunch: () -> Unit
) {
    actual fun launch() {
        onLaunch()
    }
}
