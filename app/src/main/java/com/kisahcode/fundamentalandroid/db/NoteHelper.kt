package com.kisahcode.fundamentalandroid.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.kisahcode.fundamentalandroid.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.kisahcode.fundamentalandroid.db.DatabaseContract.NoteColumns.Companion._ID
import java.sql.SQLException

/**
 * Helper class to perform database operations for the Note entity.
 *
 * @param context The context of the application.
 */
class NoteHelper(context: Context) {

    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    /**
     * Opens the database connection.
     *
     * @throws SQLException If the database cannot be opened for writing.
     */
    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    /**
     * Closes the database connection.
     */
    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    /**
     * Retrieves all notes from the database.
     *
     * @return A cursor containing the result of the query.
     */
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC",
            null
        )
    }

    /**
     * Retrieves a note from the database by its ID.
     *
     * @param id The ID of the note to retrieve.
     * @return A cursor containing the result of the query.
     */
    fun queryById(id: String): Cursor {
        return database.query(DATABASE_TABLE, null, "$_ID = ?", arrayOf(id), null, null, null, null)
    }

    /**
     * Inserts a new note into the database.
     *
     * @param values The content values of the note to insert.
     * @return The row ID of the newly inserted note, or -1 if an error occurred.
     */
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    /**
     * Updates an existing note in the database.
     *
     * @param id The ID of the note to update.
     * @param values The content values with which to update the note.
     * @return The number of rows affected by the update operation.
     */
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    /**
     * Deletes a note from the database by its ID.
     *
     * @param id The ID of the note to delete.
     * @return The number of rows affected by the delete operation.
     */
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: NoteHelper? = null

        /**
         * Gets an instance of the NoteHelper.
         *
         * @param context The context of the application.
         * @return An instance of the NoteHelper.
         */
        fun getInstance(context: Context): NoteHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: NoteHelper(context)
            }
    }
}