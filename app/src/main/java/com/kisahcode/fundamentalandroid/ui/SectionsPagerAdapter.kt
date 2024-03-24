package com.kisahcode.fundamentalandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Adapter for managing fragments in a ViewPager2.
 *
 * @param activity The activity hosting the ViewPager2.
 */
class SectionsPagerAdapter internal constructor(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    /**
     * Creates and returns the fragment to be displayed at the specified position.
     *
     * @param position The position of the fragment.
     * @return The fragment instance.
     */
    override fun createFragment(position: Int): Fragment {
        val fragment = NewsFragment() // Create a new instance of the NewsFragment
        val bundle = Bundle() // Create a Bundle to pass data to the fragment

        // Set the tab type (News or Bookmark) based on the position
        if (position == 0) {
            bundle.putString(NewsFragment.ARG_TAB, NewsFragment.TAB_NEWS)
        } else {
            bundle.putString(NewsFragment.ARG_TAB, NewsFragment.TAB_BOOKMARK)
        }

        // Set the arguments for the fragment
        fragment.arguments = bundle
        return fragment
    }

    /**
     * Returns the total number of fragments managed by the adapter.
     *
     * @return The number of fragments.
     */
    override fun getItemCount(): Int {
        return 2
    }
}