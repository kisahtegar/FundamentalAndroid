package com.kisahcode.fundamentalandroid.ui

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kisahcode.fundamentalandroid.R
import com.kisahcode.fundamentalandroid.databinding.ActivityNoteAddUpdateBinding
import com.kisahcode.fundamentalandroid.db.DatabaseContract
import com.kisahcode.fundamentalandroid.db.NoteHelper
import com.kisahcode.fundamentalandroid.entity.Note
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Activity for adding or updating a note.
 */
class NoteAddUpdateActivity : AppCompatActivity(), View.OnClickListener {

    // Binding for the layout of this activity
    private lateinit var binding: ActivityNoteAddUpdateBinding

    // Helper class to interact with the database for note operations
    private lateinit var noteHelper: NoteHelper

    // Variables to track whether the note is being edited, the note object, and its position
    private var isEdit = false
    private var note: Note? = null
    private var position: Int = 0

    companion object {
        // Keys for extras passed with intents
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"

        // Result codes for different operations
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301

        // Constants for dialog types
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the NoteHelper instance for database operations and opens the db connection
        noteHelper = NoteHelper.getInstance(applicationContext)
        noteHelper.open()

        // Retrieve the Note object from the intent extras
        note = intent.getParcelableExtra(EXTRA_NOTE)
        if (note != null) {
            // If in edit mode, set the position from the intent extras and flag as edit
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            // If not in edit mode, create a new Note object
            note = Note()
        }

        // Define variables to hold the title and button text based on edit mode
        val actionBarTitle: String
        val btnTitle: String

        // Determine the action bar title and button text based on edit mode
        if (isEdit) {
            actionBarTitle = "Ubah"
            btnTitle = "Update"

            // If in edit mode, populate the EditText fields with the note's data
            note?.let {
                binding.edtTitle.setText(it.title)
                binding.edtDescription.setText(it.description)
            }

        } else {
            actionBarTitle = "Tambah"
            btnTitle = "Simpan"
        }

        // Set the action bar title
        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set the button text based on edit mode
        binding.btnSubmit.text = btnTitle

        // Set click listener for the submit button
        binding.btnSubmit.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        // Check if the clicked view is the submit button
        if (view.id == R.id.btn_submit) {
            // Retrieve the title and description from the EditText fields
            val title = binding.edtTitle.text.toString().trim()
            val description = binding.edtDescription.text.toString().trim()

            // Check if the title is empty
            if (title.isEmpty()) {
                // If title is empty, display an error message and return
                binding.edtTitle.error = "Field can not be blank"
                return
            }

            // Update the note object with the new title and description
            note?.title = title
            note?.description = description

            // Create an intent to pass the updated note data back to the calling activity
            val intent = Intent()
            intent.putExtra(EXTRA_NOTE, note)
            intent.putExtra(EXTRA_POSITION, position)

            // Create ContentValues to store the note data for database operations
            val values = ContentValues()
            values.put(DatabaseContract.NoteColumns.TITLE, title)
            values.put(DatabaseContract.NoteColumns.DESCRIPTION, description)

            // Check if in edit mode
            if (isEdit) {
                // If in edit mode, update the note in the database
                val result = noteHelper.update(note?.id.toString(), values)

                if (result > 0) {
                    // If update successful, set the result and finish the activity
                    setResult(RESULT_UPDATE, intent)
                    finish()
                } else {
                    // If update failed, display a toast message
                    Toast.makeText(this@NoteAddUpdateActivity, "Gagal mengupdate data", Toast.LENGTH_SHORT).show()
                }
            } else {
                // If not in edit mode, insert a new note into the database
                note?.date = getCurrentDate()
                values.put(DatabaseContract.NoteColumns.DATE, getCurrentDate())
                val result = noteHelper.insert(values)

                if (result > 0) {
                    // If insert successful, set the result and finish the activity
                    note?.id = result.toInt()
                    setResult(RESULT_ADD, intent)
                    finish()
                } else {
                    // If insert failed, display a toast message
                    Toast.makeText(this@NoteAddUpdateActivity, "Gagal menambah data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Retrieves the current date and time in the specified format.
     *
     * @return A string representing the current date and time.
     */
    private fun getCurrentDate(): String {
        // Define the date format
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        // Create a Date object representing the current date and time
        val date = Date()
        // Format the date object to a string using the defined date format
        return dateFormat.format(date)
    }

    /**
     * Initialize the contents of the Activity's standard options menu.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu layout if it's an edit action
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Handle menu item selection.
     *
     * @param item The menu item that was selected.
     * @return Return false to allow normal menu processing to proceed,
     * true to consume it here.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Show confirmation dialog to delete the note
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            // Show confirmation dialog to close the activity
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }

        // Return the super method result
        return super.onOptionsItemSelected(item)
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        // Show confirmation dialog to close the activity
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    /**
     * Displays an alert dialog based on the specified type.
     *
     * @param type The type of alert dialog to display. Use ALERT_DIALOG_CLOSE to show a dialog for cancelling changes on the form,
     * or ALERT_DIALOG_DELETE to show a dialog for deleting a note.
     */
    private fun showAlertDialog(type: Int) {
        // Determine the type of dialog based on the input parameter
        val isDialogClose = type == ALERT_DIALOG_CLOSE

        // Initialize dialog title and message variables
        val dialogTitle: String
        val dialogMessage: String

        // Set dialog title and message based on the dialog type
        if (isDialogClose) {
            dialogTitle = "Batal"
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?"
        } else {
            dialogTitle = "Hapus Note"
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
        }

        // Create an AlertDialog.Builder instance
        val alertDialogBuilder = AlertDialog.Builder(this)

        // Set the title and message of the dialog
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder.setMessage(dialogMessage)

        // Set the dialog as not cancelable to prevent dismissal without making a choice
        alertDialogBuilder.setCancelable(false)

        // Add "Ya" (Yes) and "Tidak" (No) buttons to the dialog
        alertDialogBuilder
            .setPositiveButton("Ya") { _, _ ->
                if (isDialogClose) {
                    // Finish the activity if the dialog is for cancelling changes
                    finish()
                } else {
                    // Delete the note if the dialog is for deleting a note
                    val result = noteHelper.deleteById(note?.id.toString()).toLong()
                    if (result > 0) {
                        val intent = Intent()
                        intent.putExtra(EXTRA_POSITION, position)
                        setResult(RESULT_DELETE, intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@NoteAddUpdateActivity,
                            "Gagal menghapus data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                // Cancel the dialog if the "Tidak" button is clicked
                dialog.cancel()
            }

        // Create and show the AlertDialog
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}