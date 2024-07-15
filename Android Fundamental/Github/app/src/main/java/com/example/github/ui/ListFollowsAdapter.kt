package com.example.github.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github.data.response.FollowerResponseItem
import com.example.github.databinding.ItemUserBinding

class ListFollowsAdapter : ListAdapter<FollowerResponseItem, ListFollowsAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListFollowsAdapter.MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FollowerResponseItem) {
            Glide.with(binding.root)
                .load(user.avatarUrl)
                .into(binding.profileImage)
            binding.usernameTV.text = user.login
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowerResponseItem>() {
            override fun areItemsTheSame(
                oldItem: FollowerResponseItem,
                newItem: FollowerResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: FollowerResponseItem,
                newItem: FollowerResponseItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}