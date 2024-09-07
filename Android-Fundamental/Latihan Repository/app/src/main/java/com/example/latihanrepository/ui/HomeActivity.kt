package com.example.latihanrepository.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.latihanrepository.R
import com.example.latihanrepository.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(
            binding.tabs, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }


    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.home, R.string.bookmark)
    }
}