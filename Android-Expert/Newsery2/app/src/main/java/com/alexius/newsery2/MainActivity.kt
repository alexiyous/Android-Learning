package com.alexius.newsery2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.alexius.newsery.MainViewModel
import com.alexius.newsery2.presentation.navgraph.NavGraph
import com.alexius.newsery2.presentation.onboarding.OnBoardingScreen
import com.alexius.newsery2.presentation.onboarding.OnBoardingViewModel
import com.alexius.newsery2.ui.theme.NewseryTheme
import org.koin.androidx.compose.koinViewModel
import kotlin.getValue
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.splashCondition
            }
        }


        setContent {
            NewseryTheme {
                Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)){
                    val startDestination = viewModel.startDestination
                    NavGraph(startDestination = startDestination)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewseryTheme {
        Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)){
            val viewModel: OnBoardingViewModel = koinViewModel()
            OnBoardingScreen(
                event = viewModel::onEvent
            )
        }
    }
}