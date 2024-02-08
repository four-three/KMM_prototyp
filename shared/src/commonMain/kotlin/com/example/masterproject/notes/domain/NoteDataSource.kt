package com.example.masterproject.notes.domain

import kotlinx.coroutines.flow.Flow

/**
 * This part here does not use any third-party libs.
 * That the whole reason for this architecture. To isolate the database accesses
 */
interface NoteDataSource {
    fun getNotes(): Flow<List<Note>> //"Flow" updates the List when ever something changes
    //"suspend" is used when we want to make changes
    suspend fun insertNote(note: Note)//"suspend" is use here because this function isn't constantly updating the list, also "Flow" does already have suspend in it
    suspend fun deleteNote(id: Long)
}