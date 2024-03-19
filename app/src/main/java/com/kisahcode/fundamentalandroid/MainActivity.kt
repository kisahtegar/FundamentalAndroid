package com.kisahcode.fundamentalandroid

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kisahcode.fundamentalandroid.databinding.ActivityMainBinding

/**
 * The main activity of the application.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    /**
     * Called when the activity is starting. Responsible for initializing the activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    /**
     * Handles click events for buttons.
     */
    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_new -> newFile()
            R.id.button_open -> showList()
            R.id.button_save -> saveFile()
        }
    }

    /**
     * Sets up click listeners for buttons.
     */
    private fun setupClickListeners() {
        binding.buttonNew.setOnClickListener(this)
        binding.buttonOpen.setOnClickListener(this)
        binding.buttonSave.setOnClickListener(this)
    }

    /**
     * Clears the content of the file.
     */
    private fun newFile() {
        binding.editTitle.setText("")
        binding.editFile.setText("")
        showToast("Clearing file")
    }

    /**
     * Shows the list of files to choose from.
     */
    private fun showList() {
        // Use this if your emulator have profileInstalled
        // val items = fileList().filter { fileName -> (fileName != "profileInstalled") }.toTypedArray()

        // Retrieves the list of files available in the app's internal storage.
        val items = fileList()

        // Creates an AlertDialog.Builder instance.
        val builder = AlertDialog.Builder(this)

        // Sets the title of the AlertDialog.
        builder.setTitle("Pilih file yang diinginkan")

        // Sets the items to display in the AlertDialog.
        builder.setItems(items) { dialog, item -> loadData(items[item].toString()) }

        // Creates an AlertDialog from the builder.
        val alert = builder.create()

        // Displays the AlertDialog.
        alert.show()
    }

    /**
     * Loads data from the selected file.
     *
     * @param title The title of the selected file.
     */
    private fun loadData(title: String) {
        // Reads data from the selected file using the FileHelper class.
        val fileModel = FileHelper.readFromFile(this, title)

        // Sets the filename retrieved from the fileModel to the editTitle EditText.
        binding.editTitle.setText(fileModel.filename)

        // Sets the data retrieved from the fileModel to the editFile EditText.
        binding.editFile.setText(fileModel.data)

        // Displays a Toast message indicating that the file data is being loaded.
        showToast("Loading ${fileModel.filename} data")
    }

    /**
     * Saves the content to a file.
     */
    private fun saveFile() {
        // Checks if the title EditText is empty.
        when {
            binding.editTitle.text.toString().isEmpty() -> showToast("Title harus diisi terlebih dahulu")
            binding.editFile.text.toString().isEmpty() -> showToast("Kontent harus diisi terlebih dahulu")
            else -> {
                // Retrieves the title and content from the EditText fields.
                val title = binding.editTitle.text.toString()
                val text = binding.editFile.text.toString()

                // Creates a new FileModel instance and assigns the title and content to it.
                val fileModel = FileModel()
                fileModel.filename = title
                fileModel.data = text

                // Writes the fileModel data to a file using the FileHelper class.
                FileHelper.writeToFile(fileModel, this)

                // Displays a Toast message indicating that the file is being saved.
                showToast("Saving ${fileModel.filename} file")
            }
        }
    }

    /**
     * Displays a toast message.
     *
     * @param message The message to be displayed.
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}