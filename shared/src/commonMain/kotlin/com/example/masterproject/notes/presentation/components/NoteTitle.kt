package com.example.masterproject.notes.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable    
fun NoteTitle(
    value: String,
    placeholder: String,
    error: String?,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChanged,
            shape = RoundedCornerShape(16.dp),
            placeholder = { Text(text = placeholder) },
            isError = error != null,
            modifier = Modifier.fillMaxWidth()
        )
    }
}