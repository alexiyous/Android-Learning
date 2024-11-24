package com.alexius.bookmark.bookmark

import android.os.Bundle
import android.widget.Toast
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alexius.bookmark.bookmarkModule
import com.alexius.core.data.remote.response.Article
import com.alexius.core.domain.model.ArticleModel
import com.alexius.newsery2.presentation.detail.DetailScreen
import com.alexius.newsery2.presentation.detail.DetailsEvent
import com.alexius.newsery2.presentation.detail.DetailsViewModel
import com.alexius.newsery2.presentation.navgraph.Route
import com.alexius.newsery2.ui.theme.NewseryTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import kotlin.getValue


class BookmarkActivity() : ComponentActivity() {

    private val viewModel: BookmarkViewModel by viewModel()

    lateinit var articleTemp: ArticleModel

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

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "bookmark"
                    )
                    {
                        composable("bookmark") {
                            val viewModel: BookmarkViewModel = koinViewModel()
                            BookmarkScreen(
                                state = state,
                                navigateToDetails = { article ->

                                    articleTemp = article

                                    navController.navigate(Route.DetailScreen.route)

                                    /*Intent(Intent.ACTION_VIEW).also {
                                        it.data = article.url.toUri()
                                        *//* Check if there is an app that can handle the intent *//*
                                        *//* Must add queries in the android manifest, see manifest *//*
                                        if (it.resolveActivity(context.packageManager) != null) {
                                            context.startActivity(it)
                                        }
                                    }*/

                                }
                            )
                        }

                        composable(route = Route.DetailScreen.route) {
                            val viewModel: DetailsViewModel = koinViewModel()
                            if (viewModel.sideEffect != null) {
                                Toast.makeText(
                                    LocalContext.current,
                                    viewModel.sideEffect,
                                    Toast.LENGTH_SHORT
                                ).show()
                                viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                            }
                            DetailScreen(
                                article = articleTemp,
                                event = viewModel::onEvent,
                                navigateUp = {
                                    navController.navigateUp()
                                })
                        }
                    }
                }
            }
        }
    }
}