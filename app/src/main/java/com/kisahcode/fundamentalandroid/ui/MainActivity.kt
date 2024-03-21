package com.kisahcode.fundamentalandroid.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kisahcode.fundamentalandroid.adapter.NoteAdapter
import com.kisahcode.fundamentalandroid.databinding.ActivityMainBinding
import com.kisahcode.fundamentalandroid.db.NoteHelper
import com.kisahcode.fundamentalandroid.entity.Note
import com.kisahcode.fundamentalandroid.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * MainActivity displays a list of notes and allows users to add, update, or delete notes.
 */
class MainActivity : AppCompatActivity() {

    // View binding for the activity layout
    private lateinit var binding: ActivityMainBinding

    // Adapter for managing note items in RecyclerView
    private lateinit var noteAdapter: NoteAdapter

    // Activity result launcher for handling result from NoteAddUpdateActivity
    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.data != null) {
            // Handle result based on request code
            when (result.resultCode) {
                NoteAddUpdateActivity.RESULT_ADD -> {
                    val note = result.data?.getParcelableExtra<Note>(NoteAddUpdateActivity.EXTRA_NOTE) as Note
                    noteAdapter.addItem(note)
                    binding.rvNotes.smoothScrollToPosition(noteAdapter.itemCount - 1)
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                NoteAddUpdateActivity.RESULT_UPDATE -> {
                    val note = result.data?.getParcelableExtra<Note>(NoteAddUpdateActivity.EXTRA_NOTE) as Note
                    val position = result?.data?.getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0) as Int
                    noteAdapter.updateItem(position, note)
                    binding.rvNotes.smoothScrollToPosition(position)
                    showSnackbarMessage("Satu item berhasil diubah")
                }
                NoteAddUpdateActivity.RESULT_DELETE -> {
                    val position = result?.data?.getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0) as Int
                    noteAdapter.removeItem(position)
                    showSnackbarMessage("Satu item berhasil dihapus")
                }
            }
        }
    }

    companion object {
        // Extra state key for saving and restoring note list state
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set action bar title
        supportActionBar?.title = "Notes"

        // Setup RecyclerView and adapter
        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        binding.rvNotes.setHasFixedSize(true)
        noteAdapter = NoteAdapter(object : NoteAdapter.OnItemClickCallback {
            override fun onItemClicked(selectedNote: Note?, position: Int?) {
                val intent = Intent(this@MainActivity, NoteAddUpdateActivity::class.java)
                intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, selectedNote)
                intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, position)
                resultLauncher.launch(intent)
            }
        })
        binding.rvNotes.adapter = noteAdapter

        // Set click listener for add button
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, NoteAddUpdateActivity::class.java)
            resultLauncher.launch(intent)
        }

        // Restore saved instance state if available, otherwise load notes from database
        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Note>(EXTRA_STATE)
            if (list != null) {
                noteAdapter.listNotes = list
            }
        }
    }

    /**
     * Loads the notes asynchronously from the database using coroutines.
     * Displays a progress bar while loading the notes.
     * If there are no notes, displays a snackbar message.
     */
    private fun loadNotesAsync() {
        // Use lifecycleScope to launch a coroutine
        lifecycleScope.launch {
            // Show progress bar while loading notes
            binding.progressbar.visibility = View.VISIBLE

            // Access the NoteHelper instance
            val noteHelper = NoteHelper.getInstance(applicationContext)
            noteHelper.open()

            // Use async to perform database query on IO thread
            val deferredNotes = async(Dispatchers.IO) {
                // Retrieve notes cursor from the database
                val cursor = noteHelper.queryAll()
                // Map cursor data to ArrayList of notes using MappingHelper
                MappingHelper.mapCursorToArrayList(cursor)
            }

            // Hide progress bar after notes are loaded
            binding.progressbar.visibility = View.INVISIBLE

            // Wait for the deferred result to be available
            val notes = deferredNotes.await()

            // Check if there are notes available
            if (notes.size > 0) {
                // Update the noteAdapter with the loaded notes
                noteAdapter.listNotes = notes
            } else {
                // If there are no notes, display a snackbar message
                noteAdapter.listNotes = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }

            // Close the NoteHelper database connection
            noteHelper.close()
        }
    }

    /**
     * Overrides the onSaveInstanceState method to save the state of the activity when it is paused or stopped.
     * This method is called before the activity is stopped and may be used to store transient data.
     *
     * @param outState The Bundle in which to place the saved state.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, noteAdapter.listNotes)
    }

    /**
     * Displays a Snackbar message with the given text.
     *
     * @param message The message to be displayed in the Snackbar.
     */
    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvNotes, message, Snackbar.LENGTH_SHORT).show()
    }
}