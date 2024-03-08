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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.masterproject.core.presentation.BottomSheet
import com.example.masterproject.core.presentation.PermissionCallback
import com.example.masterproject.core.presentation.PermissionLocationType
import com.example.masterproject.core.presentation.PermissionStatus
import com.example.masterproject.core.presentation.PermissionType
import com.example.masterproject.core.presentation.createCameraManager
import com.example.masterproject.core.presentation.createGalleryManager
import com.example.masterproject.core.presentation.createPermissions
import com.example.masterproject.notes.domain.Note
import com.example.masterproject.notes.presentation.NoteListEvent
import com.example.masterproject.notes.presentation.NoteListState


@Composable
fun AddNote(
    state: NoteListState,
    newNote: Note?,
    isOpen: Boolean,
    onEvent: (NoteListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val galleryManager = createGalleryManager()
    galleryManager.registerGalleryManager { imageBytes ->
        onEvent(NoteListEvent.OnPhotoPicked(imageBytes))
    }
    val cameraManager = createCameraManager()
    cameraManager.registerCameraManager { imageBytes ->
        onEvent(NoteListEvent.OnPhotoPicked(imageBytes))
    }

    val permissionsManager = createPermissions(object : PermissionCallback {
        override fun onPermissionStatus(
            permissionType: PermissionType,
            status: PermissionStatus
        ) {
            when (status) {
                PermissionStatus.GRANTED -> {
                    when (permissionType) {
                        PermissionType.CAMERA -> onEvent(NoteListEvent.OnCameraClicked)
                        PermissionType.GALLERY -> onEvent(NoteListEvent.OnGalleryClicked)
                    }
                }

                else -> {
                    onEvent(NoteListEvent.OnAskForPermission)
                }
            }
        }

        override fun onPermissionLocationStatus(
            permissionType: PermissionLocationType,
            status: PermissionStatus
        ) {
            when (status) {
                PermissionStatus.GRANTED -> {
                    when (permissionType) {
                        PermissionLocationType.LOCATION_SERVICE_ON -> {
                            onEvent(NoteListEvent.OnLocationServiceOn)
                        }
                        PermissionLocationType.LOCATION_FOREGROUND -> {
                            onEvent(NoteListEvent.OnLocationServiceOn)
                        }
                        PermissionLocationType.LOCATION_BACKGROUND -> {
                            onEvent(NoteListEvent.OnLocationServiceOn)
                        }
                    }
                }
                else -> {
                    onEvent(NoteListEvent.OnLocationServiceOff)
                }
            }
        }
    })


    if(!state.isLocationServiceOn) {
        permissionsManager.askForLocationPermission(PermissionLocationType.LOCATION_BACKGROUND)
    }


    if (state.isImageSourceOptionDialogOpen) {
        ImageSourceOptionDialog(onDismissRequest = {
            onEvent(NoteListEvent.OnSelectImageSource)
        }, onGalleryRequest = {
            onEvent(NoteListEvent.OnSelectImageSource)
            onEvent(NoteListEvent.OnGalleryClicked)
        }, onCameraRequest = {
            onEvent(NoteListEvent.OnSelectImageSource)
            onEvent(NoteListEvent.OnCameraClicked)
        })
    }

    if (state.isGalleryOpen) {
        if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
            galleryManager.pickImage()
        } else {
            permissionsManager.askPermission(PermissionType.GALLERY)
        }
        onEvent(NoteListEvent.OnGalleryDismissed)
    }
    if (state.isCameraOpen) {
        if (permissionsManager.isPermissionGranted(PermissionType.CAMERA)) {
            cameraManager.takeImage()
        } else {
            permissionsManager.askPermission(PermissionType.CAMERA)
        }
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