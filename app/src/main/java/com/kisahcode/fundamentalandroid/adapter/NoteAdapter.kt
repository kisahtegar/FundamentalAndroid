package com.kisahcode.fundamentalandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kisahcode.fundamentalandroid.R
import com.kisahcode.fundamentalandroid.databinding.ItemNoteBinding
import com.kisahcode.fundamentalandroid.entity.Note

/**
 * Adapter class for managing Note items in a RecyclerView.
 *
 * This adapter is responsible for binding Note data to the item views in the RecyclerView.
 * It also provides methods for adding, updating, and removing items from the dataset.
 *
 * @param onItemClickCallback The callback interface for handling item click events.
 */
class NoteAdapter(private val onItemClickCallback: OnItemClickCallback) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    /**
     * List to store the Note items.
     */
    var listNotes = ArrayList<Note>()
        set(listNotes) {
            if (listNotes.size > 0) {
                this.listNotes.clear()
            }
            this.listNotes.addAll(listNotes)
        }

    /**
     * Adds a new Note item to the dataset.
     *
     * @param note The Note object to add.
     */
    fun addItem(note: Note) {
        this.listNotes.add(note)
        notifyItemInserted(this.listNotes.size - 1)
    }

    /**
     * Updates an existing Note item in the dataset.
     *
     * @param position The position of the Note item to update.
     * @param note The updated Note object.
     */
    fun updateItem(position: Int, note: Note) {
        this.listNotes[position] = note
        notifyItemChanged(position, note)
    }

    /**
     * Removes a Note item from the dataset.
     *
     * @param position The position of the Note item to remove.
     */
    fun removeItem(position: Int) {
        this.listNotes.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listNotes.size)
    }

    /**
     * Inflates the layout for a Note item view.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    /**
     * Binds Note data to the item view.
     */
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    /**
     * Retrieves the total number of Note items in the dataset.
     */
    override fun getItemCount(): Int = this.listNotes.size

    /**
     * ViewHolder class for representing a Note item view.
     */
    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNoteBinding.bind(itemView)

        /**
         * Binds Note data to the item view.
         */
        fun bind(note: Note) {
            binding.tvItemTitle.text = note.title
            binding.tvItemDate.text = note.date
            binding.tvItemDescription.text = note.description
            binding.cvItemNote.setOnClickListener {
                onItemClickCallback.onItemClicked(note, adapterPosition)
            }
        }
    }

    /**
     * Callback interface for handling item click events.
     */
    interface OnItemClickCallback {
        /**
         * Called when a Note item is clicked.
         *
         * @param selectedNote The clicked Note object.
         * @param position The position of the clicked item.
         */
        fun onItemClicked(selectedNote: Note?, position: Int?)
    }
}
