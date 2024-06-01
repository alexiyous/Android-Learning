package com.alexius.mikatsu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexius.mikatsu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var rvRecipes : RecyclerView
    private val list = ArrayList<Recipe>()


    private fun showSelectedRecipeForDetail(recipe: Recipe) {
        val detailIntent = Intent(this, DetailRecipeActivity::class.java)
        detailIntent.putExtra(DetailRecipeActivity.EXTRA_RECIPE, recipe)
        startActivity(detailIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvRecipes = binding.rvRecipes
        rvRecipes.setHasFixedSize(true)
        list.addAll(getListRecipes())
        showRecyclerList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_about -> {
                startActivity(Intent(this, AboutPageActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getListRecipes(): ArrayList<Recipe> {
        val dataRecipeName = resources.getStringArray(R.array.recipe_name)
        val dataAuthor = resources.getStringArray(R.array.author_name)
        val dataDescription = resources.getStringArray(R.array.recipe_desc)
        val dataIngredients = resources.getStringArray(R.array.recipe_ingredients)
        val dataPhoto = resources.obtainTypedArray(R.array.recipe_image)
        val listRecipe = ArrayList<Recipe>()
        for (i in dataRecipeName.indices) {
            val recipe = Recipe(dataRecipeName[i], dataAuthor[i], dataDescription[i], dataPhoto.getResourceId(i, -1), dataIngredients[i])
            listRecipe.add(recipe)
        }
        return listRecipe
    }

    private fun showRecyclerList() {
        rvRecipes.layoutManager = LinearLayoutManager(this)
        val listRecipeAdapter = ListRecipeAdapter(list)
        rvRecipes.adapter = listRecipeAdapter

        listRecipeAdapter.onItemClickCallback = { selectedRecipe ->
            showSelectedRecipeForDetail(selectedRecipe)
        }
    }
}