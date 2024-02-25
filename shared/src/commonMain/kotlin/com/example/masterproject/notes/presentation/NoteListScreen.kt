package com.example.masterproject.notes.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.masterproject.core.presentation.CameraManager
import com.example.masterproject.core.presentation.GalleryManager
import com.example.masterproject.notes.domain.Note
import com.example.masterproject.notes.presentation.components.AddNote
import com.example.masterproject.notes.presentation.components.NoteDetail
import com.example.masterproject.notes.presentation.components.NoteListItem


@Composable
fun NoteListScreen(
    state: NoteListState,
    newNote: Note?,
    //this is a Lambda call which allows me to send the event to the Parent-Composable (a ViewModel in this case)
    onEvent: (NoteListEvent) -> Unit,
    galleryManager: GalleryManager,
    cameraManager: CameraManager
) {
    Scaffold(
        //TODO: put FAB(FloatingActionButton) in the center
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(NoteListEvent.OnAddNewNoteClick)
                },
                shape = CircleShape,
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add note"
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "My notes (${state.notes.size})",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    fontWeight = FontWeight.Bold
                )
            }

            items(state.notes) { note ->
                NoteListItem(
                    note = note,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onEvent(NoteListEvent.SelectNote(note))
                        }
                        .padding(horizontal = 16.dp)
                        .shadow(2.dp, RoundedCornerShape(16.dp))
                )
            }
        }
    }

    NoteDetail(
        isOpen = state.isSelectedNoteOpen,
        selectedNote = state.selectedNote,
        onEvent = onEvent,
    )

    AddNote(
        state = state,
        newNote = newNote,
        isOpen = state.isAddNewNoteOpen,
        galleryManager = galleryManager,
        cameraManager = cameraManager,
        onEvent = onEvent
    )
    //{ event ->
    //            if (event is NoteListEvent.OnAddPhotoClicked) {
    //                //imagePicker.pickImage()
    //                imagePicker.takeImage()
    //            }
    //            onEvent(event)
    //        }
}