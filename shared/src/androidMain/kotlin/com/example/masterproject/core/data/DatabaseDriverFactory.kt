package com.example.masterproject.core.data

import android.content.Context
import com.example.masterproject.database.NoteDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
/**
 * Android implementation of DatabaseDriverFactory.
 *
 * Creates an AndroidSqliteDriver to access the database on Android.
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DatabaseDriverFactory(
    private val context: Context
) {
    /**
     * Creates an AndroidSqliteDriver using the schema and context.
     *
     * @return The AndroidSqliteDriver instance
     */
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(
            NoteDatabase.Schema,
            context,
            "note.db"
        )
    }
}