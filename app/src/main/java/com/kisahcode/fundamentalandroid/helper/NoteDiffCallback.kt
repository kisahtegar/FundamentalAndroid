package com.kisahcode.fundamentalandroid.helper

import androidx.recyclerview.widget.DiffUtil
import com.kisahcode.fundamentalandroid.database.Note

/**
 * NoteDiffCallback is a utility class used by RecyclerView adapters to efficiently update the list of notes.
 *
 * @property oldNoteList The old list of notes.
 * @property newNoteList The new list of notes.
 */
class NoteDiffCallback(private val oldNoteList: List<Note>, private val newNoteList: List<Note>) : DiffUtil.Callback() {

    /**
     * Returns the size of the old note list.
     *
     * @return The size of the old note list.
     */
    override fun getOldListSize(): Int = oldNoteList.size

    /**
     * Returns the size of the new note list.
     *
     * @return The size of the new note list.
     */
    override fun getNewListSize(): Int = newNoteList.size

    /**
     * Checks if the items in the old and new lists are the same.
     *
     * @param oldItemPosition The position of the item in the old list.
     * @param newItemPosition The position of the item in the new list.
     * @return True if the items are the same, false otherwise.
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList[oldItemPosition].id == newNoteList[newItemPosition].id
    }

    /**
     * Checks if the contents of the items in the old and new lists are the same.
     *
     * @param oldItemPosition The position of the item in the old list.
     * @param newItemPosition The position of the item in the new list.
     * @return True if the contents are the same, false otherwise.
     */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNoteList[oldItemPosition]
        val newNote = newNoteList[newItemPosition]
        return oldNote.title == newNote.title && oldNote.description == newNote.description
    }
}
