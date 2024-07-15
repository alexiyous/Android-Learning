package com.example.github.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.github.R
import com.example.github.data.response.SearchUserResponse
import com.example.github.data.response.UserDetailResponse
import com.example.github.databinding.ActivityUserDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private val userDetailViewModel: UserDetailViewModel by viewModels()

    companion object{
        var username: String = ""

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs = binding.tabs
        tabs.apply {
            TabLayoutMediator(this, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }

        userDetailViewModel.userDetail.observe(this) { userDetail ->
            setDetailUser(userDetail)
        }

        userDetailViewModel.isLoading.observe(this) { value ->
            showLoading(value)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setDetailUser(username: UserDetailResponse) {
        Glide.with(this)
            .load(username.avatarUrl)
            .into(binding.detailUserImage)

        binding.apply {
            usernameTv.text = username.login
            nameTv.text = username.name
            followersTv.text = buildString {
                append(username.followers.toString())
                append(" Followers")
            }
            followingTv.text = buildString {
                append(username.following.toString())
                append(" Following")
            }
        }
    }
}