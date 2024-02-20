package com.example.masterproject.notes.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masterproject.core.presentation.BottomSheet
import com.example.masterproject.notes.domain.Note
import com.example.masterproject.notes.presentation.NoteListEvent

@Composable
fun NoteDetail(
    isOpen: Boolean,
    selectedNote: Note?,
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
                if(selectedNote?.photoBytes == null) {
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
                        note = selectedNote,
                        modifier = Modifier
                            .size(150.dp)
                    )
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "${selectedNote?.title}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
                Spacer(Modifier.height(16.dp))
                EditRow(
                    onEditClick = {
                        selectedNote?.let {
                            onEvent(NoteListEvent.EditNote(it))
                        }
                    },
                    onDeleteClick = {
                        onEvent(NoteListEvent.DeleteNote)
                    }
                )
                Text(
                    text = selectedNote?.note ?: "",
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp
                )
//                NoteMainTextField(
//                    value = selectedNote?.note ?: "",
//                    placeholder = "Note",
//                    error = null,
//                    onValueChanged = {
//                        onEvent(NoteListEvent.OnNoteChanged(it))
//                    },
//                    modifier = Modifier.fillMaxWidth().height(400.dp)
//                )
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

@Composable
private fun EditRow(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        FilledTonalIconButton(
            onClick = onEditClick,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = "Edit note"
            )
        }
        FilledTonalIconButton(
            onClick = onDeleteClick,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Delete note"
            )
        }
    }
}