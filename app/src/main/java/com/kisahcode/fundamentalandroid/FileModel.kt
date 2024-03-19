package com.kisahcode.fundamentalandroid

/**
 * Represents a model for a file, containing its filename and data.
 *
 * @property filename The name of the file.
 * @property data The content data of the file.
 */
data class FileModel(
    var filename: String? = null,
    var data: String? = null
)
