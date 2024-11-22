package com.alexius.bookmark.bookmark

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alexius.bookmark.bookmark.ui.theme.NewseryTheme
import com.alexius.core.domain.model.Article
import com.google.android.play.core.splitcompat.SplitCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class BookmarkActivity() : ComponentActivity() {

    val viewModel by viewModels<BookmarkViewModel>()
    val state = viewModel.state.value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewseryTheme {

                val context = LocalContext.current

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

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
        SplitCompat.installActivity(newBase)
    }

}