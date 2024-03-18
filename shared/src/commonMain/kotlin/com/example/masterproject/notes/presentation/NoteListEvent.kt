package com.example.masterproject.notes.presentation

import com.example.masterproject.notes.domain.Note

sealed interface NoteListEvent {
    //if i don't need to compare these classes i don't have to use data-class
    class OnPhotoPicked(val bytes: ByteArray): NoteListEvent
    data class OnTitleChanged(val value: String): NoteListEvent
    data class OnLocationSaved(val value: String): NoteListEvent
    data class EditNote(val note: Note): NoteListEvent
    data class OnNoteChanged(val value: String): NoteListEvent
    data class SelectNote(val note: Note): NoteListEvent //is always editable
    object DismissNote: NoteListEvent //also auto saves the Note if no errors occur
    object OnAddNewNoteClick: NoteListEvent
    object SaveNote: NoteListEvent
    class OnPhotoTaken(val bytes: ByteArray): NoteListEvent
    object OnAddPhotoClicked: NoteListEvent
    object OnAskForPermission: NoteListEvent
    object OnSelectImageSource: NoteListEvent
    object OnSelectedPermission: NoteListEvent
    object OnDeviceSettingClicked: NoteListEvent
    object OnCameraClicked: NoteListEvent
    object OnCameraDismissed: NoteListEvent
    object OnGalleryClicked: NoteListEvent
    object OnGalleryDismissed: NoteListEvent
    object OnDeviceSettingDismissed: NoteListEvent
    object OnLocationServiceOn: NoteListEvent
    object OnLocationServiceOff: NoteListEvent
    object DeleteNote: NoteListEvent
}