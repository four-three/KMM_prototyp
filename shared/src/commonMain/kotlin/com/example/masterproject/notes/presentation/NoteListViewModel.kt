package com.example.masterproject.notes.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.masterproject.notes.domain.Note
import com.example.masterproject.notes.domain.NoteDataSource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * MOKO lib is needed in order to use a viewmodel here!
 */

class NoteListViewModel(
    private val noteDataSource: NoteDataSource
): ViewModel() {

    private val _state = MutableStateFlow(NoteListState())

    /**
     * Combines the flows and returns a copy of the most current list of notes
     */
    val state = combine(
        _state,
        noteDataSource.getNotes()
    ) { state, notes ->
        state.copy(
            notes = notes,

        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), NoteListState())

    var newNote: Note? by mutableStateOf(null)
        private set

    fun onEvent(event: NoteListEvent) {
        when(event) {
            NoteListEvent.DeleteNote -> {
                viewModelScope.launch {
                    //hide the note before deleting so it doesnt disturb the user when values are set to null/deleted
                    _state.value.selectedNote?.id?.let { id ->
                        _state.update { it.copy(
                            isSelectedNoteOpen = false
                        )}
                        noteDataSource.deleteNote(id)
                        //wait till the deleting is completed
                        delay(300L)
                        _state.update { it.copy(
                            selectedNote = null
                        ) }
                    }
                }
            }
            NoteListEvent.DismissNote -> TODO()
            NoteListEvent.OnAddNewNoteClick -> TODO()
            NoteListEvent.OnAddPhotoClicked -> TODO()
            is NoteListEvent.OnPhotoPicked -> TODO()
            is NoteListEvent.OnTitleChanged -> TODO()
            is NoteListEvent.SelectNote -> TODO()
        }
    }
}