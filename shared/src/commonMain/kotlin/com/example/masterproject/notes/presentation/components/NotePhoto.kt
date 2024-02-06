package com.example.masterproject.notes.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.masterproject.notes.domain.Note

@Composable
fun NotePhoto(
    note: Note?,
    modifier: Modifier = Modifier,
    iconSize: Dp = 25.dp
) {
    if(note?.photoBytes != null) {

    }

}