package com.kisahcode.fundamentalandroid.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kisahcode.fundamentalandroid.data.response.CustomerReviewsItem
import com.kisahcode.fundamentalandroid.databinding.ItemReviewBinding

/**
 * Adapter class for displaying customer reviews in a RecyclerView.
 *
 * This adapter extends ListAdapter, which efficiently handles the underlying data changes.
 * @param DIFF_CALLBACK The DiffUtil callback for calculating item differences.
 */
class ReviewAdapter : ListAdapter<CustomerReviewsItem, ReviewAdapter.MyViewHolder>(DIFF_CALLBACK) {

    /**
     * Creates a ViewHolder for the RecyclerView.
     *
     * @param parent The parent ViewGroup.
     * @param viewType The type of the view.
     * @return A ViewHolder object.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    /**
     * Binds the data to the ViewHolder.
     *
     * @param holder The ViewHolder to bind data to.
     * @param position The position of the item in the data set.
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    /**
     * ViewHolder class for the RecyclerView.
     *
     * @param binding The view binding object.
     */
    class MyViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds the review data to the ViewHolder.
         *
         * @param review The review data to bind.
         */
        fun bind(review: CustomerReviewsItem){
            binding.tvItem.text = "${review.review}\n- ${review.name}"
        }
    }
    companion object {

        /**
         * DiffUtil callback for calculating the difference between two lists of items.
         * This callback is used by ListAdapter to efficiently update the RecyclerView.
         */
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CustomerReviewsItem>() {

            /**
             * Called to check whether two objects represent the same item.
             *
             * @param oldItem The old item.
             * @param newItem The new item.
             * @return true if items represent the same object, false otherwise.
             */
            override fun areItemsTheSame(oldItem: CustomerReviewsItem, newItem: CustomerReviewsItem): Boolean {
                return oldItem == newItem
            }

            /**
             * Called to check whether two items have the same data.
             *
             * @param oldItem The old item.
             * @param newItem The new item.
             * @return true if the contents of the items are the same, false otherwise.
             */
            override fun areContentsTheSame(oldItem: CustomerReviewsItem, newItem: CustomerReviewsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}