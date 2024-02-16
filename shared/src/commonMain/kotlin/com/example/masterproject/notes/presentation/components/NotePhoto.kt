package com.example.masterproject.notes.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.masterproject.core.presentation.rememberBitmapFromBytes
import com.example.masterproject.notes.domain.Note


@Composable
fun NotePhoto(
    note: Note?,
    modifier: Modifier = Modifier,
    iconSize: Dp = 25.dp
) {
    val bitmap = rememberBitmapFromBytes(note?.photoBytes)
    val photoModifier = modifier.clip(RoundedCornerShape(10))
//TODO: Crop the photo
    if(bitmap != null) {
        Image(
            bitmap = bitmap,
            contentDescription = note?.title,
            modifier = photoModifier,
            contentScale = ContentScale.Crop
        )
    } else {
//        Box(
//            modifier = photoModifier
//                .background(MaterialTheme.colorScheme.secondaryContainer),
//            contentAlignment = Alignment.Center
//        ) {
//            Icon(
//                imageVector = Icons.Rounded.Image,
//                contentDescription = note?.title,
//                modifier = Modifier.size(iconSize),
//                tint = MaterialTheme.colorScheme.onSecondaryContainer
//            )
//        }
    }

}