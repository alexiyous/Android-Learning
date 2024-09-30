package com.alexius.storyvibe.view.homepage

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexius.storyvibe.R
import com.alexius.storyvibe.data.Result
import com.alexius.storyvibe.data.remote.response.ListStoryItem
import com.alexius.storyvibe.databinding.ActivityHomeActivtiyBinding
import com.alexius.storyvibe.view.ViewModelFactory
import com.alexius.storyvibe.view.login.LoginViewModel

class HomeActivtiy : AppCompatActivity() {

    private lateinit var binding: ActivityHomeActivtiyBinding

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeActivtiyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupAction()
    }

    private fun setupAction() {
        setSupportActionBar(binding.toolbar)

        val storyAdapter = ListStoryAdapter()

        viewModel.getAllStories().observe(this) { response ->
            when (response) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val storyData = response.data
                    val listStory: List<ListStoryItem?>? = storyData.listStory
                    if (listStory != null) {
                        storyAdapter.submitList(listStory)
                    }
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Error Fetching: " + response.error,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = storyAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                // Handle settings action
                Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }
}