package com.kisahcode.fundamentalandroid

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Adapter class for managing fragments in a ViewPager2.
 * This adapter provides the necessary fragments for the ViewPager2.
 * @param activity The activity hosting the ViewPager2.
 */
class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    /**
     * Gets the total number of fragments in the ViewPager2.
     * @return The total number of fragments.
     */
    override fun getItemCount(): Int {
        return 2 // Return the total number of fragments
    }

    /**
     * Creates and returns the fragment for the given position.
     * @param position The position of the fragment.
     * @return The fragment at the specified position.
     */
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment() // Return HomeFragment for position 0
            1 -> ProfileFragment() // Return ProfileFragment for position 1
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}