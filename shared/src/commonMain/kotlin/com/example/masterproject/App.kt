package com.example.masterproject

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.masterproject.core.presentation.NotesTheme
import com.example.masterproject.notes.presentation.NoteListScreen
import com.example.masterproject.notes.presentation.NoteListViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

/*

Here the Wrapper is implemented

 */
@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean
) {
    NotesTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {
        val viewModel = getViewModel(
            key = "note-list-screen",
            factory = viewModelFactory {
                NoteListViewModel()
            }
        )
        val state by viewModel.state.collectAsState()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NoteListScreen(
                state = state,
                newNote = viewModel.newNote,
                onEvent = viewModel::onEvent
            )
        }
    }

}