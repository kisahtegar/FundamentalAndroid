package com.kisahcode.fundamentalandroid.helper

import android.database.Cursor
import com.kisahcode.fundamentalandroid.db.DatabaseContract
import com.kisahcode.fundamentalandroid.entity.Note

/**
 * A helper class that provides methods to map data from Cursor to ArrayList of Note objects.
 */
object MappingHelper {

    /**
     * Maps a Cursor containing note data to an ArrayList of Note objects.
     *
     * @param notesCursor The Cursor containing note data.
     * @return An ArrayList of Note objects mapped from the Cursor data.
     */
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<Note> {
        // Initialize an ArrayList to store Note objects
        val notesList = ArrayList<Note>()

        // Check if the Cursor is not null
        notesCursor?.apply {
            // Iterate through the Cursor rows
            while (moveToNext()) {
                // Extract note data from the Cursor
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.TITLE))
                val description = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DESCRIPTION))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DATE))

                // Create a new Note object and add it to the ArrayList
                notesList.add(Note(id, title, description, date))
            }
        }

        // Return the ArrayList of Note objects
        return notesList
    }
}