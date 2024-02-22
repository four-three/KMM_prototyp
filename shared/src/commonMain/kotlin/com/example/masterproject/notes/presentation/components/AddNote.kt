package com.example.masterproject.notes.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.masterproject.core.presentation.BottomSheet
import com.example.masterproject.notes.domain.Note
import com.example.masterproject.notes.presentation.NoteListEvent
import com.example.masterproject.notes.presentation.NoteListState


@Composable
fun AddNote(
    state: NoteListState,
    newNote: Note?,
    isOpen: Boolean,
    onEvent: (NoteListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomSheet(
        visible = isOpen,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(16.dp))
                if(newNote?.photoBytes == null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(40))
                            .size(150.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .clickable {
                                onEvent(NoteListEvent.OnAddPhotoClicked)
                            }
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(40)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add photo",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                } else {
                    NotePhoto(
                        note = newNote,
                        modifier = Modifier
                            .size(150.dp)
                            .clickable {
                                onEvent(NoteListEvent.OnAddPhotoClicked)
                            }
                    )
                }
                Spacer(Modifier.height(16.dp))
                NoteTitle(
                    value = newNote?.title ?: "",
                    placeholder = "Title",
                    error = state.titleError,
                    onValueChanged = {
                        onEvent(NoteListEvent.OnTitleChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                NoteMainTextField(
                    value = newNote?.note ?: "",
                    placeholder = "Note",
                    error = null,
                    onValueChanged = {
                        onEvent(NoteListEvent.OnNoteChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth().height(400.dp)
                )
                Spacer(Modifier.height(16.dp))
                Button(

                    onClick = {
                        onEvent(NoteListEvent.SaveNote)
                    },
                ) {
                    Text(text = "Save")
                }
            }

            IconButton(
                onClick = {
                    onEvent(NoteListEvent.DismissNote)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close"
                )
            }
        }
    }
}