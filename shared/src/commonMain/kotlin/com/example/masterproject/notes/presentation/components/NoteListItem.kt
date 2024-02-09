package com.example.masterproject.notes.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.masterproject.notes.domain.Note

@Composable
fun NoteListItem(
    note: Note,
    modifier : Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .clip(RoundedCornerShape(16.dp))

    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NotePhoto(
                note = note,
                modifier = Modifier.size(50.dp)
            )

            Spacer(Modifier.width(16.dp))


            Text(
                text = "${note.title}",
                modifier = Modifier.weight(1f)
            )

        }
    }
}