package com.example.github.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.R
import com.example.github.data.response.ItemsItem
import com.example.github.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    companion object{
        const val INITIAL_USER = "randy" //This a query list of users when first time open the app
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.usernameList.observe(this) { users ->
            setUsersData(users)
        }

        // Search Bar Implementation
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    mainViewModel.searchUsers(searchBar.text.toString())
                    false
                }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.userGitRv.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.userGitRv.addItemDecoration(itemDecoration)

        mainViewModel.isLoading.observe(this) {value ->
            showLoading(value)
        }
    }

    private fun setUsersData(users: List<ItemsItem>) {
        val userAdapter = UserListAdapter()
        userAdapter.submitList(users)
        binding.userGitRv.adapter = userAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}