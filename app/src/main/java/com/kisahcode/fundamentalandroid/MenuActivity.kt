package com.kisahcode.fundamentalandroid

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kisahcode.fundamentalandroid.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)

//            searchView
//                .editText
//                .setOnEditorActionListener { textView, actionId, event ->
//                    //TODO: THIS CANNOT BE REASSIGNED. searchBar.text = searchView.text
//                    searchBar.setText(searchView.text)
//                    searchView.hide()
//                    Toast.makeText(this@MenuActivity, searchView.text, Toast.LENGTH_SHORT).show()
//                    false
//                }

            searchView.editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    searchBar.setText(s) // Set the text of the searchBar based on the text in the searchView
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            searchView.editText.setOnEditorActionListener { textView, actionId, event ->
                // Perform actions when the user submits the search
                searchView.hide()
                Toast.makeText(this@MenuActivity, searchView.text, Toast.LENGTH_SHORT).show()
                false
            }


            searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu1 -> {
                        true
                    }
                    R.id.menu2 -> {
                        true
                    }
                    else -> false
                }
            }
        }
    }
}