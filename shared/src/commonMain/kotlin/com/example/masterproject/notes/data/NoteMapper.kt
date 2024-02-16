package com.example.masterproject.notes.data

import com.example.masterproject.core.data.ImageStorage
import com.example.masterproject.notes.domain.Note
import database.NoteEntity

suspend fun NoteEntity.toNote(imageStorage: ImageStorage): Note {
    return Note(
        id = id,
        createdAt = createdAt,
        updatedAt = updatedAt,
        location = location,
        title = title,
        note = note,
        photoBytes = imagePath?.let { imageStorage.getImage(it) }
    )
}