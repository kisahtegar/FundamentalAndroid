package com.kisahcode.fundamentalandroid.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kisahcode.fundamentalandroid.R
import com.kisahcode.fundamentalandroid.data.local.entity.NewsEntity
import com.kisahcode.fundamentalandroid.databinding.ItemNewsBinding
import com.kisahcode.fundamentalandroid.ui.NewsAdapter.MyViewHolder
import com.kisahcode.fundamentalandroid.utils.DateFormatter

/**
 * Adapter for displaying news items in a RecyclerView.
 *
 * @param onBookmarkClick Callback for handling bookmark button clicks.
 */
class NewsAdapter(private val onBookmarkClick: (NewsEntity) -> Unit) : ListAdapter<NewsEntity, MyViewHolder>(DIFF_CALLBACK) {

    /**
     * Creates a ViewHolder by inflating the item_news layout.
     *
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View.
     * @return The newly created MyViewHolder instance.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    /**
     * Binds data to the ViewHolder at the specified position.
     *
     * @param holder The ViewHolder to bind data to.
     * @param position The position of the item in the dataset.
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)

        val ivBookmark = holder.binding.ivBookmark
        if (news.isBookmarked) {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmarked_white))
        } else {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmark_white))
        }
        ivBookmark.setOnClickListener {
            onBookmarkClick(news)
        }
    }

    /**
     * ViewHolder class for holding views of each news item.
     *
     * @param binding The View binding object for the item_news layout.
     */
    class MyViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds data to the views.
         *
         * @param news The news entity to bind data from.
         */
        fun bind(news: NewsEntity) {
            binding.tvItemTitle.text = news.title
            binding.tvItemPublishedDate.text = DateFormatter.formatDate(news.publishedAt)
            Glide.with(itemView.context)
                .load(news.urlToImage)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(binding.imgPoster)
            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(news.url)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {

        // DiffUtil callback for calculating the difference between two lists.
        val DIFF_CALLBACK: DiffUtil.ItemCallback<NewsEntity> =
            object : DiffUtil.ItemCallback<NewsEntity>() {
                override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                    return oldItem.title == newItem.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }
}