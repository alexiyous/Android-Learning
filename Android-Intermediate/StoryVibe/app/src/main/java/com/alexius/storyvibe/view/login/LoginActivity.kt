package com.alexius.storyvibe.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alexius.storyvibe.R
import com.alexius.storyvibe.data.Result
import com.alexius.storyvibe.databinding.ActivityLoginBinding
import com.alexius.storyvibe.databinding.ActivitySignUpBinding
import com.alexius.storyvibe.view.ViewModelFactory
import com.alexius.storyvibe.view.homepage.HomeActivtiy

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        Log.d(TAG, "TEST")
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupAction()
        setupAnimation()
    }

    private fun setupAnimation() {
        val imageView = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(500)
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val message = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
        val emailText = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailTextEdit = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passwordText = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditText = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(imageView, title, message, emailText, emailTextEdit, passwordText, passwordEditText, login)
            startDelay = 100
            start()
        }
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val name = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.login(name, password).observe(this) { response ->
                when (response) {
                    is Result.Loading -> {
                        binding.progressIndicator.visibility = View.VISIBLE
                        binding.loginButton.isEnabled = false
                    }
                    is Result.Success -> {
                        binding.progressIndicator.visibility = View.GONE
                        binding.loginButton.isEnabled = true
                        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, HomeActivtiy::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        startActivity(intent)
                        finish()
                    }
                    is Result.Error -> {
                        binding.progressIndicator.visibility = View.GONE
                        binding.loginButton.isEnabled = true
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}