package com.kisahcode.fundamentalandroid.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.kisahcode.fundamentalandroid.database.Note
import com.kisahcode.fundamentalandroid.database.NoteDao
import com.kisahcode.fundamentalandroid.database.NoteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Repository class for managing Note data operations.
 *
 * @param application The application context.
 */
class NoteRepository(application: Application) {

    private val mNotesDao: NoteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        // Obtain the NoteRoomDatabase instance using the application context
        val db = NoteRoomDatabase.getDatabase(application)

        // Initialize the mNotesDao with the NoteDao instance obtained from the database
        mNotesDao = db.noteDao()
    }

    /**
     * Retrieves all notes from the database.
     *
     * @return A LiveData object containing a list of notes.
     */
    fun getAllNotes(): LiveData<List<Note>> = mNotesDao.getAllNotes()

    /**
     * Inserts a new note into the database.
     *
     * @param note The note to be inserted.
     */
    fun insert(note: Note) {
        executorService.execute { mNotesDao.insert(note) }
    }

    /**
     * Deletes a note from the database.
     *
     * @param note The note to be deleted.
     */
    fun delete(note: Note) {
        executorService.execute { mNotesDao.delete(note) }
    }

    /**
     * Updates an existing note in the database.
     *
     * @param note The note to be updated.
     */
    fun update(note: Note) {
        executorService.execute { mNotesDao.update(note) }
    }
}