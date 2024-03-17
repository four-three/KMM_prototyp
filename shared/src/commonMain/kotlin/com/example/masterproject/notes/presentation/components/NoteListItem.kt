package com.example.masterproject.notes.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.masterproject.core.presentation.rememberBitmapFromBytes
import com.example.masterproject.notes.domain.Note
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun NoteListItem(
    note: Note,
    modifier : Modifier = Modifier
) {
    // Convert timestamp to LocalDateTime
    val localDateTime = Instant.fromEpochMilliseconds(note.createdAt).toLocalDateTime(TimeZone.currentSystemDefault())
    // Format the date and hour
    val formattedDate =
        localDateTime.dayOfMonth.toString() +
                "." +
                localDateTime.monthNumber.toString() +
                "." +
                localDateTime.year.toString()
//                localDateTime.hour.toString() + ":" + localDateTime.minute.toString()
    val hourAndMinute = localDateTime.time.toString().substring(0, 5)
    val bitmap = rememberBitmapFromBytes(note?.photoBytes)
    if (bitmap != null) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .height(170.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                NotePhoto(
                    note = note,
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.66f)
                )
                Text(
                    text = "${note.title}",
                    modifier = Modifier
                        .weight(1f),
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    style = MaterialTheme.typography.headlineSmall
                )

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )
                Text(
                    text = "Created: $formattedDate $hourAndMinute",
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.Start),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1
                )

            }
        }
    } else {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .height(75.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${note.title}",
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    style = MaterialTheme.typography.headlineSmall
                )

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )
                Text(
                    text = "Created: $formattedDate $hourAndMinute",
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.Start),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1
                )

            }
        }
    }
}