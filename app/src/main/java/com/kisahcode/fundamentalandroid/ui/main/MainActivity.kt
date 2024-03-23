package com.kisahcode.fundamentalandroid.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kisahcode.fundamentalandroid.R
import com.kisahcode.fundamentalandroid.databinding.ActivityMainBinding
import com.kisahcode.fundamentalandroid.helper.ViewModelFactory

/**
 * Main activity displaying a list of notes.
 */
class MainActivity : AppCompatActivity() {

    // View binding for the activity layout
    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding

    // Adapter for the RecyclerView
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Initialize ViewModel
        val mainViewModel = obtainViewModel(this@MainActivity)

        // Observe LiveData for note list changes
        mainViewModel.getAllNotes().observe(this) { noteList ->
            if (noteList != null) {
                adapter.setListNotes(noteList)
            }
        }

        // Initialize RecyclerView and its adapter
        adapter = NoteAdapter()
        binding?.rvNotes?.layoutManager = LinearLayoutManager(this)
        binding?.rvNotes?.setHasFixedSize(true)
        binding?.rvNotes?.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }

    /**
     * Obtains the MainViewModel associated with the activity.
     * @param activity The reference to the activity.
     * @return The MainViewModel instance.
     */
    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }
}