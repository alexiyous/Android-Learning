package com.alexius.mikatsu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.alexius.mikatsu.databinding.ItemRowRecipeBinding

class ListRecipeAdapter(private val listRecipe: ArrayList<Recipe>) : RecyclerView.Adapter<ListRecipeAdapter.ListViewHolder>() {

    lateinit var onItemClickCallback: (Recipe) -> Unit
    lateinit var ingridients: String

    class ListViewHolder(var binding: ItemRowRecipeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listRecipe.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (recipeName, author, description, photo) = listRecipe[position]
        holder.binding.tvRecipeName.text = recipeName
        holder.binding.tvAuthorName.text = author
        holder.binding.tvRecipeDesc.text = description
        holder.binding.imgRecipe.setImageResource(photo)
        ingridients = listRecipe[position].ingredients

        holder.itemView.setOnClickListener {
            onItemClickCallback(listRecipe[holder.adapterPosition])
        }

    }
}