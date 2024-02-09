@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.example.masterproject.di

import com.example.masterproject.notes.domain.NoteDataSource

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class AppModule {
    val noteDataSource: NoteDataSource
}