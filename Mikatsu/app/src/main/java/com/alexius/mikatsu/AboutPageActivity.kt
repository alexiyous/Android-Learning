package com.alexius.mikatsu

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alexius.mikatsu.databinding.ActivityAboutPageBinding
import com.bumptech.glide.Glide

class AboutPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAboutPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}