package com.example.github.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.github.data.response.FollowerResponse
import com.example.github.data.response.FollowerResponseItem
import com.example.github.data.response.ItemsItem

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = ListFollowsFragment()
        fragment.arguments = Bundle().apply {
            putInt(ListFollowsFragment.ARG_POSITION, position + 1)
        }
        return fragment
    }

}