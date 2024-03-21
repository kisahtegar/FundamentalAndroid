package com.kisahcode.fundamentalandroid.db

import android.provider.BaseColumns

/**
 * A contract class for the app's database.
 *
 * This class defines the structure of the database including table names and column names.
 * It facilitates the use of database names as constants throughout the app, which makes the
 * database schema more manageable.
 */
internal class DatabaseContract {

    /**
     * Inner class that defines the table contents of the notes.
     *
     * It implements the interface [BaseColumns] which includes a primary key field named _ID that
     * many Android classes expect. This class serves as a schema for the notes table, providing
     * the names of the table and its columns.
     */
    internal class NoteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "note"
            const val _ID = "_id"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val DATE = "date"
        }
    }
}