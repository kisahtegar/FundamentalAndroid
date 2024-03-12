package com.kisahcode.fundamentalandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter class for displaying a list of quotes in a RecyclerView.
 *
 * @param listReview List of quotes to be displayed.
 */
class QuoteAdapter(private val listReview: ArrayList<String>): RecyclerView.Adapter<QuoteAdapter.ViewHolder>() {

    /**
     * Creates a new ViewHolder object by inflating the item layout.
     *
     * @param viewGroup The parent ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_quote, viewGroup, false)
        return ViewHolder(view)
    }

    /**
     * Binds the data to the ViewHolder.
     *
     * @param viewHolder The ViewHolder to bind the data to.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvItem.text = listReview[position]
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items.
     */
    override fun getItemCount(): Int {
        return listReview.size
    }

    /**
     * ViewHolder class for holding the views associated with items in the RecyclerView.
     *
     * @param view The item view.
     */
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvItem: TextView = view.findViewById(R.id.tvItem)
    }
}