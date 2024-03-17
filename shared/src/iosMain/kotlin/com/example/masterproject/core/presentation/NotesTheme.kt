package com.example.masterproject.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import com.example.masterproject.ui.DarkColorScheme
import com.example.masterproject.ui.LightColorScheme

@Composable
actual fun NotesTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if(darkTheme) DarkColorScheme else LightColorScheme,
        typography = typography,
        content = content
    )
}