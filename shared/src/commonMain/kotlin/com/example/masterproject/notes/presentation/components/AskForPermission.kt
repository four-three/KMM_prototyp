package com.example.masterproject.notes.presentation.components

//@Composable
//fun AskForPermission(
//    state: NoteListState,
//    newNote: Note?,
//    isOpen: Boolean,
//    imagePicker: ImagePicker,
//    onEvent: (NoteListEvent) -> Unit,
//) {
//    val coroutineScope = rememberCoroutineScope()
//    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
//    var imageSourceOptionDialog by remember { mutableStateOf(value = false) }
//    var launchCamera by remember { mutableStateOf(value = false) }
//    var launchGallery by remember { mutableStateOf(value = false) }
//    var launchSetting by remember { mutableStateOf(value = false) }
//    var permissionRationalDialog by remember { mutableStateOf(value = false) }
//    val permissionsManager = createPermissionsManager(object : PermissionCallback {
//        override fun onPermissionStatus(
//            permissionType: PermissionType,
//            status: PermissionStatus
//        ) {
//            when (status) {
//                PermissionStatus.GRANTED -> {
//                    when (permissionType) {
//                        PermissionType.CAMERA -> launchCamera = true
//                        PermissionType.GALLERY -> launchGallery = true
//                    }
//                }
//
//                else -> {
//                    permissionRationalDialog = true
//                }
//            }
//        }
//
//
//    })
//
//    val cameraManager = rememberCameraManager {
//        coroutineScope.launch {
//            val bitmap = withContext(Dispatchers.Default) {
//                it?.toImageBitmap()
//            }
//            imageBitmap = bitmap
//        }
//    }
//
//    val galleryManager = rememberGalleryManager {
//        coroutineScope.launch {
//            val bitmap = withContext(Dispatchers.Default) {
//                it?.toImageBitmap()
//            }
//            imageBitmap = bitmap
//        }
//    }
//    if (imageSourceOptionDialog) {
//        ImageSourceOptionDialog(onDismissRequest = {
//            imageSourceOptionDialog = false
//        }, onGalleryRequest = {
//            imageSourceOptionDialog = false
//            launchGallery = true
//        }, onCameraRequest = {
//            imageSourceOptionDialog = false
//            launchCamera = true
//        })
//    }
//    if (launchGallery) {
//        if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
//            galleryManager.launch()
//        } else {
//            permissionsManager.askPermission(PermissionType.GALLERY)
//        }
//        launchGallery = false
//    }
//    if (launchCamera) {
//        if (permissionsManager.isPermissionGranted(PermissionType.CAMERA)) {
//            cameraManager.launch()
//        } else {
//            permissionsManager.askPermission(PermissionType.CAMERA)
//        }
//        launchCamera = false
//    }
//    if (launchSetting) {
//        permissionsManager.launchSettings()
//        launchSetting = false
//    }
//    if (permissionRationalDialog) {
//        AlertMessageDialog(title = "Permission Required",
//            message = "To set your profile picture, please grant this permission. You can manage permissions in your device settings.",
//            positiveButtonText = "Settings",
//            negativeButtonText = "Cancel",
//            onPositiveClick = {
//                permissionRationalDialog = false
//                launchSetting = true
//
//            },
//            onNegativeClick = {
//                permissionRationalDialog = false
//            })
//
//    }

//    AddNote(
//        state = state,
//        newNote = newNote,
//        isOpen = state.isAddNewNoteOpen,
//        onEvent = { event ->
//            if (event is NoteListEvent.OnAddPhotoClicked) {
//                //imagePicker.pickImage()
//                imagePicker.takeImage()
//                onEvent(NoteListEvent.OnSelectedPermission)
//            }
//            onEvent(event)
//        }
//    )
//}

