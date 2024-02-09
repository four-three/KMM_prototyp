package com.example.masterproject.di

import com.example.masterproject.core.data.DatabaseDriverFactory
import com.example.masterproject.database.NoteDatabase
import com.example.masterproject.notes.data.SqlDelightNoteDataSource
import com.example.masterproject.notes.domain.NoteDataSource

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AppModule{
    actual val noteDataSource: NoteDataSource by lazy {
        SqlDelightNoteDataSource(
            db = NoteDatabase(
                driver = DatabaseDriverFactory().create()
            )
        )
    }
}