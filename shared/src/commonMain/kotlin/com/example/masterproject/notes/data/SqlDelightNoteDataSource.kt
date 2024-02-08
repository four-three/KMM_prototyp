package com.example.masterproject.notes.data

import com.example.masterproject.database.NoteDatabase
import com.example.masterproject.notes.domain.Note
import com.example.masterproject.notes.domain.NoteDataSource
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightNoteDataSource(
    db: NoteDatabase
): NoteDataSource {

    private val queries = db.noteQueries

    override fun getNotes(): Flow<List<Note>> {
            return queries
            .getNotes()
            .asFlow()
            .mapToList()
            .map { noteEntities ->
                noteEntities.map { noteEntity ->
                    noteEntity.toNote()
                }
            }
    }

    override suspend fun insertNote(note: Note) {
        queries.insertNoteEntity(
            id = note.id,
            createdAt = Clock.System.now().toEpochMilliseconds(),
            updatedAt = Clock.System.now().toEpochMilliseconds(),
            location = note.location,
            title = note.title,
            note = note.note,
            imagePath = null
        )
    }

    override suspend fun deleteNote(id: Long) {
        queries.deleteNote(id)
    }
}
