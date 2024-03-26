package com.kisahcode.fundamentalandroid.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Represents a single note entity in the database.
 *
 * @property id The unique identifier of the note. This is auto-generated by the database.
 * @property title The title of the note.
 * @property description The description or content of the note.
 * @property date The date when the note was created or last updated.
 */
@Entity
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @ColumnInfo(name = "date")
    var date: String? = null
) : Parcelable