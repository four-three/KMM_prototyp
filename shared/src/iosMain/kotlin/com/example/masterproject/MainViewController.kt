package com.example.masterproject

import androidx.compose.ui.window.ComposeUIViewController
import com.example.masterproject.core.presentation.createPicker
import com.example.masterproject.di.AppModule
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

fun MainViewController() = ComposeUIViewController {
    val isDarkTheme =
        UIScreen.mainScreen.traitCollection.userInterfaceStyle ==
                UIUserInterfaceStyle.UIUserInterfaceStyleDark

    App(
        darkTheme = isDarkTheme,
        dynamicColor = false,
        appModule = AppModule(),
        imagePicker = createPicker()
    )
}