package com.example.masterproject.notes.domain

import kotlinx.coroutines.flow.Flow

/**
 * This part here does not use any third-party libs.
 * That the whole reason for this architecture. To isolate the database accesses
 */
interface NoteDataSource {
    /**
     * Gets all notes from the data source.
     *
     * @note "Flow" updates the List when ever something changes
     * @return a list of all notes
     */
    fun getNotes(): Flow<List<Note>>
    /**
     * Saves a note.
     *
     * @note "suspend" is used when we want to make changes
     * @note2 "suspend" is use here because this function isn't constantly updating the list, also "Flow" does already have suspend in it
     * @param note the note to save
     */
    suspend fun insertNote(note: Note)
    /**
     * Deletes a note.
     *
     * @param note the note to delete
     */
    suspend fun deleteNote(id: Long)
}