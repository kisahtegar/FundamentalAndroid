package com.kisahcode.fundamentalandroid

import android.content.Context

/**
 * A helper object for file operations such as writing to and reading from a file in internal storage.
 */
internal object FileHelper {

    /**
     * Writes data to a file in internal storage.
     *
     * @param fileModel The model containing the filename and data to be written.
     * @param context The context of the application.
     */
    fun writeToFile(fileModel: FileModel, context: Context) {
        context.openFileOutput(fileModel.filename, Context.MODE_PRIVATE).use {
            it.write(fileModel.data?.toByteArray())
        }
    }

    /**
     * Reads data from a file in internal storage.
     *
     * @param context The context of the application.
     * @param filename The name of the file to be read.
     * @return The FileModel containing the filename and data read from the file.
     */
    fun readFromFile(context: Context, filename: String): FileModel {
        // Create a new instance of FileModel to hold the filename and data
        val fileModel = FileModel()

        // Set the filename in the file model to the provided filename
        fileModel.filename = filename

        // Open the file input stream for the specified filename
        fileModel.data = context.openFileInput(filename)
            // Read the file content as a buffered reader and process each line
            .bufferedReader().useLines { lines ->
                // Fold each line into a single string, appending a newline character after each line
                lines.fold("") { some, text ->
                    "$some$text\n"
                }
            }

        // Return the FileModel containing the filename and data read from the file
        return fileModel
    }
}