package com.example.masterproject.notes.data

import com.example.masterproject.notes.domain.Note
import database.NoteEntity

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        createdAt = createdAt,
        updatedAt = updatedAt,
        location = location,
        title = title,
        note = note,
        photoBytes = null //TODO: get actual image
    )
}