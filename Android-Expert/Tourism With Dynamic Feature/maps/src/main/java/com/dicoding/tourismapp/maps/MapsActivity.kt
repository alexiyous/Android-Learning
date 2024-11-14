package com.dicoding.tourismapp.maps

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.tourismapp.core.data.Resource
import com.dicoding.tourismapp.maps.databinding.ActivityMapsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class MapsActivity : AppCompatActivity() {


    private val mapsViewModel: MapsViewModel by viewModel()
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Kode di atas digunakan untuk memanggil module secara manual, hal ini karena koin tidak dapat
        // memanggil module yang di dalam dynamic feature module sejak awal melalui startKoin yang ada
        // di MyApplication. Karena itulah Anda harus memanggilnya secara terpisah.
        loadKoinModules(mapsModule)

        supportActionBar?.title = "Tourism Map"
        getTourismData()
    }

    private fun getTourismData() {
        mapsViewModel.tourism.observe(this) { tourism ->
            if (tourism != null) {
                when (tourism) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvMaps.text = "This is map of ${tourism.data?.get(0)?.name}"
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvError.visibility = View.VISIBLE
                        binding.tvError.text = tourism.message
                    }
                }
            }
        }
    }
}