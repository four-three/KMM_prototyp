package com.example.masterproject.notes.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NoteLocation(
    value: String,
    modifier: Modifier = Modifier

) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = value?: "Location"
        )
    }
}