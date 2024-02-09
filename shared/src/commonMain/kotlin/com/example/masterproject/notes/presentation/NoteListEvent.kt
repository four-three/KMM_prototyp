package com.example.masterproject.notes.presentation

import com.example.masterproject.notes.domain.Note

sealed interface NoteListEvent {
    object OnAddNewNoteClick: NoteListEvent
    object DismissNote: NoteListEvent //also auto saves the Note if no errors occur
    data class OnTitleChanged(val value: String): NoteListEvent
    data class EditNote(val note: Note): NoteListEvent
    object SaveNote: NoteListEvent


    //TODO: Will come back later (https://www.youtube.com/watch?v=XWSzbMnpAgI) 18:40
    data class OnNoteChanged(val value: String): NoteListEvent

    //if i don't need to compare these classes i don't have to use data-class
    class OnPhotoPicked(val bytes: ByteArray): NoteListEvent
    object OnAddPhotoClicked: NoteListEvent
    data class SelectNote(val note: Note): NoteListEvent //is always editable
    object DeleteNote: NoteListEvent
}