package com.kisahcode.fundamentalandroid.ui

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.kisahcode.fundamentalandroid.R
import com.kisahcode.fundamentalandroid.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * The main activity of the application, responsible for displaying the home screen with tabs for
 * viewing news and bookmarked news.
 */
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    /**
     * Called when the activity is starting. Initializes the activity layout, ViewPager,
     * and TabLayout.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create the SectionsPagerAdapter for managing the fragments
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter

        // Attach TabLayoutMediator to bind the ViewPager and TabLayout together
        TabLayoutMediator(
            binding.tabs, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            // Set tab titles using the string resources
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        // Remove elevation from the ActionBar to have a seamless transition between tabs and content
        supportActionBar?.elevation = 0f
    }

    companion object {
        // Array of string resource IDs for tab titles
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.home, R.string.bookmark)
    }
}