package com.kisahcode.fundamentalandroid.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.kisahcode.fundamentalandroid.database.Note
import com.kisahcode.fundamentalandroid.repository.NoteRepository

/**
 * ViewModel responsible for handling data operations for adding, updating, and deleting notes.
 *
 * This ViewModel interacts with the NoteRepository to perform CRUD operations on the Note entities.
 * It provides methods for inserting, updating, and deleting notes.
 *
 * @param application The application context.
 */
class NoteAddUpdateViewModel(application: Application) : ViewModel() {

    // Repository instance for accessing Note data
    private val mNoteRepository: NoteRepository = NoteRepository(application)

    /**
     * Inserts a new note into the database.
     *
     * @param note The Note object to be inserted.
     */
    fun insert(note: Note) {
        mNoteRepository.insert(note)
    }

    /**
     * Updates an existing note in the database.
     *
     * @param note The Note object to be updated.
     */
    fun update(note: Note) {
        mNoteRepository.update(note)
    }

    /**
     * Deletes a note from the database.
     *
     * @param note The Note object to be deleted.
     */
    fun delete(note: Note) {
        mNoteRepository.delete(note)
    }
}