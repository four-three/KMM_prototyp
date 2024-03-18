package com.example.masterproject.core.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * Displays a bottom sheet with customizable content.
 *
 * This composable function creates a bottom sheet UI component that is conditionally visible.
 * The bottom sheet animation and appearance can be customized via parameters.
 *
 * @param visible Controls the visibility of the bottom sheet. When true, the bottom sheet is visible.
 * @param modifier The modifier to be applied to the bottom sheet layout. Allows for customization of layout aspects.
 * @param content A composable lambda expression that defines the content of the bottom sheet. This allows for flexible content definition within the bottom sheet.
 */
@Composable
fun BottomSheet(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    // AnimatedVisibility is used to animate the appearance and disappearance of the bottom sheet.
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            // The animation spec for the enter transition. A tween animation is used for smoothness.
            animationSpec = tween(durationMillis = 300),
            // The initial offset for the enter animation. It starts off-screen and slides in.
            initialOffsetY = { it }
        ),
        exit = slideOutVertically(
            // The animation spec for the exit transition. Matches the enter animation for consistency.
            animationSpec = tween(durationMillis = 300),
            // The target offset for the exit animation. It slides out to the bottom of the screen.
            targetOffsetY = { it }
        )
    ) {
        // The content of the bottom sheet, wrapped in a Column for vertical arrangement.
        Column(
            modifier = modifier
                // Rounded corners are applied at the top of the bottom sheet for a more aesthetic appearance.
                .clip(
                    RoundedCornerShape(
                        topStart = 30.dp,
                        topEnd = 30.dp
                    )
                )
                // The background color of the bottom sheet. Uses the surface color from the Material theme.
                .background(MaterialTheme.colorScheme.surface)
                // Padding is applied inside the bottom sheet for content spacing.
                .padding(16.dp)
                // Enables vertical scrolling within the bottom sheet. Useful for content that exceeds the screen height.
                .verticalScroll(rememberScrollState())
        ) {
            // Executes the content lambda, allowing for dynamic content composition within the bottom sheet.
            content()
        }
    }
}
