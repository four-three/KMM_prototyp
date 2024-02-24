package com.example.masterproject.notes.presentation.components

import AlertMessageDialog
import ImageSourceOptionDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.masterproject.core.presentation.BottomSheet
import com.example.masterproject.core.presentation.ImagePicker
import com.example.masterproject.core.presentation.PermissionCallback
import com.example.masterproject.core.presentation.PermissionStatus
import com.example.masterproject.core.presentation.PermissionType
import com.example.masterproject.core.presentation.createPermissions
import com.example.masterproject.notes.domain.Note
import com.example.masterproject.notes.presentation.NoteListEvent
import com.example.masterproject.notes.presentation.NoteListState
import kotlinx.coroutines.launch


@Composable
fun AddNote(
    state: NoteListState,
    newNote: Note?,
    isOpen: Boolean,
    imagePicker: ImagePicker,
    onEvent: (NoteListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    imagePicker.registerPicker { imageBytes ->
        onEvent(NoteListEvent.OnPhotoPicked(imageBytes))
    }
    val coroutineScope = rememberCoroutineScope()
    val permissionsManager = createPermissions(object : PermissionCallback {
        override fun onPermissionStatus(
            permissionType: PermissionType,
            status: PermissionStatus
        ) {
            when (status) {
                PermissionStatus.GRANTED -> {
                    when (permissionType) {
                        PermissionType.CAMERA -> NoteListEvent.OnCameraClicked
                        PermissionType.GALLERY -> NoteListEvent.OnCameraClicked
                    }
                }

                else -> {
                    onEvent(NoteListEvent.OnAskForPermission)
                }
            }
        }
    })

//    val cameraManager = remember {
//        coroutineScope.launch{
//            imagePicker.takeImage()
//        }
//    }

    if (state.isImageSourceOptionDialogOpen) {
        ImageSourceOptionDialog(onDismissRequest = {
            onEvent(NoteListEvent.OnSelectImageSource)
        }, onGalleryRequest = {
            onEvent(NoteListEvent.OnSelectImageSource)
            //TODO: launchGallery = true
        }, onCameraRequest = {
            onEvent(NoteListEvent.OnSelectImageSource)
            coroutineScope.launch {
                onEvent(NoteListEvent.OnAskForPermission)
            }
            //onEvent(NoteListEvent.OnAskForPermission)
//            onEvent(NoteListEvent.OnCameraClicked)
        })
    }
    if (state.isCameraOpen) {
        imagePicker.takeImage()
//        if (permissionsManager.isPermissionGranted(PermissionType.CAMERA)) {
////            cameraManager.start()
//            imagePicker.takeImage()
//        } else {
//            permissionsManager.askPermission(PermissionType.CAMERA)
//            onEvent(NoteListEvent.OnAskForPermission)
//
//        }
        onEvent(NoteListEvent.OnCameraDismissed)
    }
    if (state.isDeviceSettingOpen) {
        permissionsManager.launchSettings()
        onEvent(NoteListEvent.OnDeviceSettingDismissed)
    }
    if (state.isPermissionsDialogOpen) {
        AlertMessageDialog(title = "Permission Required",
            message = "To set your profile picture, please grant this permission. You can manage permissions in your device settings.",
            positiveButtonText = "Settings",
            negativeButtonText = "Cancel",
            onPositiveClick = {
                onEvent(NoteListEvent.OnSelectedPermission)
                onEvent(NoteListEvent.OnDeviceSettingClicked)
//                onEvent(NoteListEvent.OnAddPhotoClicked)
                onEvent(NoteListEvent.OnCameraClicked)
            },
            onNegativeClick = {
                onEvent(NoteListEvent.OnSelectedPermission)
            })
    }

    BottomSheet(
        visible = isOpen,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(16.dp))
                if(newNote?.photoBytes == null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(40))
                            .size(150.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .clickable {
                                onEvent(NoteListEvent.OnAddPhotoClicked)
//                                if(state.isPermissionForCameraAccessGranted) {
//                                    onEvent(NoteListEvent.OnAddPhotoClicked)
//                                } else {
//                                    onEvent(NoteListEvent.OnAskForPermission)
//                                }
                            }
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(40)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add photo",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                } else {
                    NotePhoto(
                        note = newNote,
                        modifier = Modifier
                            .size(150.dp)
                            .clickable {
                                onEvent(NoteListEvent.OnAddPhotoClicked)
                            }
                    )
                }
                Spacer(Modifier.height(16.dp))
                NoteTitle(
                    value = newNote?.title ?: "",
                    placeholder = "Title",
                    error = state.titleError,
                    onValueChanged = {
                        onEvent(NoteListEvent.OnTitleChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                NoteMainTextField(
                    value = newNote?.note ?: "",
                    placeholder = "Note",
                    error = null,
                    onValueChanged = {
                        onEvent(NoteListEvent.OnNoteChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth().height(400.dp)
                )
                Spacer(Modifier.height(16.dp))
                Button(

                    onClick = {
                        onEvent(NoteListEvent.SaveNote)
                    },
                ) {
                    Text(text = "Save")
                }
            }

            IconButton(
                onClick = {
                    onEvent(NoteListEvent.DismissNote)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close"
                )
            }
        }
    }
}