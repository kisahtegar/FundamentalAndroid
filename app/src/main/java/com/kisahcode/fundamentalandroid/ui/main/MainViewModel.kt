package com.kisahcode.fundamentalandroid.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kisahcode.fundamentalandroid.database.Note
import com.kisahcode.fundamentalandroid.repository.NoteRepository

/**
 * ViewModel responsible for providing access to a list of notes to be displayed in the main UI.
 *
 * This ViewModel interacts with the NoteRepository to retrieve a LiveData list of notes.
 *
 * @param application The application context.
 */
class MainViewModel(application: Application) : ViewModel() {

    // Repository instance for accessing Note data
    private val mNoteRepository: NoteRepository = NoteRepository(application)

    /**
     * Retrieves a LiveData list of all notes from the repository.
     *
     * @return LiveData object containing a list of notes.
     */
    fun getAllNotes(): LiveData<List<Note>> = mNoteRepository.getAllNotes()
}