package com.example.masterproject

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.masterproject.core.presentation.CameraManager
import com.example.masterproject.core.presentation.GalleryManager
import com.example.masterproject.core.presentation.NotesTheme
import com.example.masterproject.di.AppModule
import com.example.masterproject.notes.presentation.NoteListEvent
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
    dynamicColor: Boolean,
    appModule: AppModule,
    galleryManager: GalleryManager,
    cameraManager: CameraManager
) {
    NotesTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {

        val viewModel = getViewModel(
            key = "note-list-screen",
            factory = viewModelFactory {
                NoteListViewModel(appModule.noteDataSource)
            }
        )
        val state by viewModel.state.collectAsState()
        val onEvent: (NoteListEvent) -> Unit = { event ->
            viewModel.onEvent(event)
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NoteListScreen(
                state = state,
                newNote = viewModel.newNote,
                onEvent = onEvent,
                galleryManager = galleryManager,
                cameraManager = cameraManager
            )
        }
    }

}