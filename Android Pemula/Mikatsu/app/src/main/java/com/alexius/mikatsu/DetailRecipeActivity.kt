package com.alexius.mikatsu

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alexius.mikatsu.databinding.ActivityDetailRecipeBinding

class DetailRecipeActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_RECIPE = "extra_recipe"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipe = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Recipe>(EXTRA_RECIPE, Recipe::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Recipe>(EXTRA_RECIPE)
        }

        if (recipe != null) {
            binding.tvFoodName.text = recipe.recipeName
            binding.recipeImage.setImageResource(recipe.photo)
            binding.tvFullDescription.text = recipe.description
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvFullIngredients.text = Html.fromHtml(recipe.ingredients, Html.FROM_HTML_MODE_COMPACT)
            }
        }
    }
}