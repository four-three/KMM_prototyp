package com.example.masterproject.core.data

import com.squareup.sqldelight.db.SqlDriver
/**
 * Common interface for creating SqlDriver instances on each platform.
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class DatabaseDriverFactory {
    /**
     * Creates the SqlDriver to use for database access on this platform.
     *
     * @return The SqlDriver instance
     */
    fun create(): SqlDriver
}