package com.kisahcode.fundamentalandroid.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kisahcode.fundamentalandroid.database.Note
import com.kisahcode.fundamentalandroid.databinding.ItemNoteBinding
import com.kisahcode.fundamentalandroid.helper.NoteDiffCallback
import com.kisahcode.fundamentalandroid.ui.insert.NoteAddUpdateActivity


/**
 * Adapter class for managing the list of notes displayed in a RecyclerView.
 * @constructor Creates a NoteAdapter with an empty list of notes.
 */
class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    /** List of notes displayed in the RecyclerView. */
    private val listNotes = ArrayList<Note>()

    /**
     * Sets the list of notes to be displayed in the RecyclerView.
     * @param listNotes The new list of notes to be displayed.
     */
    fun setListNotes(listNotes: List<Note>) {
        // Calculate the difference between the old and new lists of notes
        val diffCallback = NoteDiffCallback(this.listNotes, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        // Update the list of notes and dispatch updates to the RecyclerView
        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    /**
     * Creates a new NoteViewHolder by inflating the item_note layout.
     * @param parent The parent ViewGroup.
     * @param viewType The view type of the new View.
     * @return A new NoteViewHolder instance.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    /**
     * Binds the data of a note to the corresponding views in the ViewHolder.
     * @param holder The NoteViewHolder to bind data to.
     * @param position The position of the note in the list.
     */
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    /**
     * Gets the total number of items in the list of notes.
     * @return The total number of notes in the list.
     */
    override fun getItemCount(): Int {
        return listNotes.size
    }

    /**
     * ViewHolder class for managing the views of a single note item.
     * @param binding The ItemNoteBinding associated with the ViewHolder.
     */
    inner class NoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds the data of a note to the views in the ViewHolder.
         * @param note The note to bind data from.
         */
        fun bind(note: Note) {
            with(binding) {
                tvItemTitle.text = note.title
                tvItemDate.text = note.date
                tvItemDescription.text = note.description

                // Set click listener to navigate to NoteAddUpdateActivity when the item is clicked
                cvItemNote.setOnClickListener {
                    val intent = Intent(it.context, NoteAddUpdateActivity::class.java)
                    intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}