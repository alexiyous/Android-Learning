package com.alexius.storyvibe.view.uploadstory

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alexius.storyvibe.data.Result
import com.alexius.storyvibe.databinding.ActivityUploadStoryBinding
import com.alexius.storyvibe.utils.getImageUri
import com.alexius.storyvibe.view.ViewModelFactory

class UploadStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadStoryBinding

    private var currentImageUri: Uri? = null

    private val viewModel by viewModels<UploadStoryViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUploadStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupAction()
        setupView()
    }

    private fun setupView() {
        // Set the status bar text color to black
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun setupAction() {



        binding.galleryButton.setOnClickListener {
            startGallery()
        }
        binding.cameraButton.setOnClickListener {
            startCamera()
        }
        binding.uploadButton.setOnClickListener {
            uploadStory()
        }
    }

    fun uploadStory() {
        if (currentImageUri != null) {
            viewModel.uploadStory(currentImageUri!!, this, binding.storyText.text.toString()).observe(this){ response ->
                when (response) {
                    is Result.Loading -> {
                        binding.progressIndicator.visibility = View.VISIBLE
                        binding.uploadButton.isEnabled = false
                        binding.storyText.isEnabled = false
                        binding.galleryButton.isEnabled = false
                        binding.cameraButton.isEnabled = false
                    }
                    is Result.Success -> {
                        binding.progressIndicator.visibility = View.GONE
                        binding.uploadButton.isEnabled = true
                        binding.storyText.isEnabled = true
                        binding.galleryButton.isEnabled = true
                        binding.cameraButton.isEnabled = true
                        onUploadSuccess()
                    }
                    is Result.Error -> {
                        binding.progressIndicator.visibility = View.GONE
                        binding.uploadButton.isEnabled = true
                        binding.storyText.isEnabled = true
                        binding.galleryButton.isEnabled = true
                        binding.cameraButton.isEnabled = true
                        Log.d("UploadStory", "Error: ${response.error}")
                    }
                }
            }
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        } else {
            currentImageUri = null
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            this.contentResolver.takePersistableUriPermission(uri, flag)

            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.uploadedImageView.setImageURI(it)
        }
    }

    private fun onUploadSuccess() {
        val resultIntent = Intent().apply {
            putExtra(IS_UPLOADED, true)
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    companion object {
        const val IS_UPLOADED = "is_uploaded"
    }
}