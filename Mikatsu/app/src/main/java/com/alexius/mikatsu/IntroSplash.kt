package com.alexius.mikatsu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class IntroSplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        val keepOnScreen = true
        super.onCreate(savedInstanceState)

        // Keep the splash screen on for a while
        splashScreen.setKeepOnScreenCondition {
            // Keep the splash screen on the screen until the app is ready
            keepOnScreen
        }

        // Go to Main Activity after 1 Second
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000) // 1 second delay
    }
}