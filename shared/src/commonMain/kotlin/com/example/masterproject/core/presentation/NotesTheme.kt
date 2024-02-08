package com.example.masterproject.core.presentation

import androidx.compose.runtime.Composable

@Composable
expect fun NotesTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
)