package com.example.masterproject.di

import android.content.Context
import com.example.masterproject.core.data.DatabaseDriverFactory
import com.example.masterproject.core.data.ImageStorage
import com.example.masterproject.core.data.LocationProvider
import com.example.masterproject.database.NoteDatabase
import com.example.masterproject.notes.data.SqlDelightNoteDataSource
import com.example.masterproject.notes.domain.NoteDataSource

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AppModule(
    private val context: Context
) {
    actual val noteDataSource: NoteDataSource by lazy {
        SqlDelightNoteDataSource(
            db = NoteDatabase(
                driver = DatabaseDriverFactory(context).create()
            ),
            imageStorage = ImageStorage(context),
            locationProvider = LocationProvider(context)
        )
    }
}