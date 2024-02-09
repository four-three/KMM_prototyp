package com.example.masterproject.notes.presentation

import com.example.masterproject.notes.domain.Note

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val selectedNote: Note? = null,
    val isAddNewNoteOpen: Boolean = false,
    val isSelectedNoteOpen: Boolean = false,
    //error states
    val titleError: String? = null,
    //val dateError: String? = null,
    val locationError: String? = null
    //val noteError: String? = null
)
