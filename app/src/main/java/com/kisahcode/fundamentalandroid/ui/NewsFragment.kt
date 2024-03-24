package com.kisahcode.fundamentalandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kisahcode.fundamentalandroid.data.Result
import com.kisahcode.fundamentalandroid.databinding.FragmentNewsBinding

/**
 * A fragment to display a list of news articles.
 */
class NewsFragment : Fragment() {

    private var tabName: String? = null

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding

    /**
     * Inflates the fragment's layout file.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    /**
     * Initializes the fragment's views and sets up the RecyclerView adapter.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabName = arguments?.getString(ARG_TAB)

        // Get an instance of the ViewModelFactory to create the NewsViewModel
        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())

        // Get the NewsViewModel using the viewModels delegate and the ViewModelFactory
        val viewModel: NewsViewModel by viewModels {
            factory
        }

        // Create a NewsAdapter with a lambda function to handle bookmark clicks
        val newsAdapter = NewsAdapter { news ->
            // Toggle the bookmark state when a news item is clicked
            if (news.isBookmarked){
                viewModel.deleteNews(news)
            } else {
                viewModel.saveNews(news)
            }
        }

        // Set up observers to observe LiveData from the ViewModel
        if (tabName == TAB_NEWS) {
            // If the tab is for displaying news, observe the headline news LiveData
            viewModel.getHeadlineNews().observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            // Show loading progress bar while fetching data
                            binding?.progressBar?.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            // Hide progress bar and update RecyclerView with news data on success
                            binding?.progressBar?.visibility = View.GONE
                            val newsData = result.data
                            newsAdapter.submitList(newsData)
                        }
                        is Result.Error -> {
                            // Hide progress bar and show error message if data retrieval fails
                            binding?.progressBar?.visibility = View.GONE
                            Toast.makeText(
                                context,
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        } else if (tabName == TAB_BOOKMARK) {
            // If the tab is for displaying bookmarked news, observe the bookmarked news LiveData
            viewModel.getBookmarkedNews().observe(viewLifecycleOwner) { bookmarkedNews ->
                // Hide progress bar and update RecyclerView with bookmarked news data
                binding?.progressBar?.visibility = View.GONE
                newsAdapter.submitList(bookmarkedNews)
            }
        }

        // Set up RecyclerView with LinearLayoutManager and NewsAdapter
        binding?.rvNews?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    /**
     * Clears the view binding instance when the fragment is destroyed to avoid memory leaks.
     */
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_TAB = "tab_name"
        const val TAB_NEWS = "news"
        const val TAB_BOOKMARK = "bookmark"
    }
}