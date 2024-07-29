package com.example.github.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github.data.response.ItemsItem
import com.example.github.databinding.ItemUserBinding

class UserListAdapter : ListAdapter<ItemsItem, UserListAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListAdapter.MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.binding.root.setOnClickListener {
            onItemClickCallback(user, holder.binding.root.context)
        }
    }

    private fun onItemClickCallback(user: ItemsItem, context: Context) {
        val userDetailIntent = Intent(context, UserDetailActivity::class.java)
        UserDetailActivity.username = user.login
        context.startActivity(userDetailIntent)
    }

    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            Glide.with(binding.root)
                .load(user.avatarUrl)
                .into(binding.profileImage)
            binding.usernameTV.text = user.login
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}