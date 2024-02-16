package com.example.masterproject.notes.data

import com.example.masterproject.core.data.ImageStorage
import com.example.masterproject.database.NoteDatabase
import com.example.masterproject.notes.domain.Note
import com.example.masterproject.notes.domain.NoteDataSource
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import kotlinx.datetime.Clock

class SqlDelightNoteDataSource(
    db: NoteDatabase,
    private val imageStorage: ImageStorage
): NoteDataSource {

    private val queries = db.noteQueries

    override fun getNotes(): Flow<List<Note>> {
            return queries
            .getNotes()
            .asFlow()
            .mapToList()
            .map { noteEntities ->
                // load images parallel
                supervisorScope {
                    noteEntities
                        .map { async { it.toNote(imageStorage) } } //only works if toNote is suspend function
                        .map { it.await() }
                }
            }
    }

    override suspend fun insertNote(note: Note) {
        val imagePath = note.photoBytes?.let {
            imageStorage.saveImage(it)
        }
        queries.insertNoteEntity(
            id = note.id,
            createdAt = Clock.System.now().toEpochMilliseconds(),
            updatedAt = Clock.System.now().toEpochMilliseconds(),
            location = note.location,
            title = note.title,
            note = note.note,
            imagePath = imagePath
        )
    }

    override suspend fun deleteNote(id: Long) {
        val entity = queries.getNoteById(id).executeAsOne()
        entity.imagePath?.let {
            imageStorage.deleteImage(it)
        }
        queries.deleteNote(id)
    }
}
