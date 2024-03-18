package com.example.masterproject.core.data

import com.example.masterproject.database.NoteDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

/**
 * iOS implementation of DatabaseDriverFactory.
 *
 * Creates a NativeSqliteDriver to access the database on iOS.
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DatabaseDriverFactory {
    /**
     * Creates a NativeSqliteDriver using the schema.
     *
     * @return The NativeSqliteDriver instance
     */
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(
            NoteDatabase.Schema,
            "note.db"
        )
    }
}