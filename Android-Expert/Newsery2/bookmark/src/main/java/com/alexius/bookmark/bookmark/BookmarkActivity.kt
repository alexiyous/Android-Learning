package com.alexius.bookmark.bookmark

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.alexius.bookmark.bookmarkModule
import com.alexius.newsery2.presentation.onboarding.OnBoardingScreen
import com.alexius.newsery2.presentation.onboarding.OnBoardingViewModel
import com.alexius.newsery2.ui.theme.NewseryTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import kotlin.getValue


class BookmarkActivity() : ComponentActivity() {

    private val viewModel: BookmarkViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadKoinModules(bookmarkModule)

        setContent {
            NewseryTheme {
                val state = viewModel.state.value

                val context = LocalContext.current


                val isSystemInDarkMode = isSystemInDarkTheme()
                val systemController = rememberSystemUiController()

                SideEffect {
                    systemController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = !isSystemInDarkMode
                    )
                }

                Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
                    BookmarkScreen(
                        state = state,
                        navigateToDetails = { article ->
                            Intent(Intent.ACTION_VIEW).also {
                                it.data = article.url.toUri()
                                /* Check if there is an app that can handle the intent */
                                /* Must add queries in the android manifest, see manifest */
                                if (it.resolveActivity(context.packageManager) != null) {
                                    context.startActivity(it)}
                            }
                        }
                    )
                }
            }
        }
    }

}