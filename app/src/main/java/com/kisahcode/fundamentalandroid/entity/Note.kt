package com.kisahcode.fundamentalandroid.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data class representing a Note entity.
 *
 * @property id The unique identifier for the note.
 * @property title The title of the note.
 * @property description The description or content of the note.
 * @property date The date when the note was created or last updated.
 */
@Parcelize
data class Note(
    var id: Int = 0,
    var title: String? = null,
    var description: String? = null,
    var date: String? = null
) : Parcelable
