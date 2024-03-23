package com.kisahcode.fundamentalandroid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * Data Access Object (DAO) interface for interacting with the Note entity in the Room database.
 */
@Dao
interface NoteDao {

    /**
     * Inserts a new note into the database.
     *
     * @param note The note to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    /**
     * Updates an existing note in the database.
     *
     * @param note The updated note.
     */
    @Update
    fun update(note: Note)

    /**
     * Deletes a note from the database.
     *
     * @param note The note to be deleted.
     */
    @Delete
    fun delete(note: Note)

    /**
     * Retrieves all notes from the database, ordered by their IDs in ascending order.
     *
     * @return A LiveData object containing a list of notes.
     */
    @Query("SELECT * from note ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>
}
