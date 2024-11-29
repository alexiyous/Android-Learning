package com.alexius.newsery2.presentation.news_navigator

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.alexius.core.domain.model.ArticleModel
import com.alexius.newsery2.R
import com.alexius.newsery2.presentation.detail.DetailScreen
import com.alexius.newsery2.presentation.detail.DetailsEvent
import com.alexius.newsery2.presentation.detail.DetailsViewModel
import com.alexius.newsery2.presentation.home.HomeScreen
import com.alexius.newsery2.presentation.home.HomeViewModel
import com.alexius.newsery2.presentation.navgraph.Route
import com.alexius.newsery2.presentation.news_navigator.components.BottomNavigationItem
import com.alexius.newsery2.presentation.news_navigator.components.NewsBottomNavigation
import com.alexius.newsery2.presentation.search.SearchNewsViewModel
import com.alexius.newsery2.presentation.search.SearchScreen
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel


@Composable
fun NewsNavigator(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {

    val bottomNavigationItems = remember() {
        listOf(
            BottomNavigationItem(R.drawable.ic_home, "Home"),
            BottomNavigationItem(R.drawable.ic_search, "Search"),
            BottomNavigationItem(R.drawable.ic_bookmark, "Bookmark")
        )
    }

    val scope = rememberCoroutineScope()

    var isBookmarkOpened by rememberSaveable { mutableStateOf(false) }

    // Line 34 - 45 is configuration for the bottom navigation bar pages to be displayed
    val navController = rememberNavController()
    val backstackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0) // Default value is 0 (home screen)
    }

    selectedItem = remember(backstackState) {
        when (backstackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.BookmarkScreen.route -> 2
            else -> 0
        }
    }

    // This will hide the bottom navigation bar when the user navigates to the detail screen
    val isBottomBarVisible = remember(key1 = backstackState) {
        backstackState?.destination?.route != Route.DetailScreen.route
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                NewsBottomNavigation(
                    items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTap(navController, Route.HomeScreen.route)
                            1 -> navigateToTap(navController, Route.SearchScreen.route)
                            2 -> navigateToTap(navController, Route.BookmarkScreen.route)
                        }
                    })
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) {
                val viewModel: HomeViewModel = koinViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    navigateToSearch = {
                        navigateToTap(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )
                    },
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }

            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchNewsViewModel = koinViewModel()
                val state = viewModel.state.value
                SearchScreen(
                    state = state,
                    event = viewModel::onEvent,
                    navigateToDetails = {
                        navigateToDetails(navController = navController, article = it)
                    })
            }

            composable(route = Route.DetailScreen.route) {
                val viewModel: DetailsViewModel = koinViewModel()
                if (viewModel.sideEffect != null) {
                    Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_SHORT)
                        .show()
                    viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }
                navController.previousBackStackEntry?.savedStateHandle?.get<ArticleModel>("article")
                    ?.let { article ->
                        Log.d("NewsNavigator", "Article: $article")

                        // State to control whether DetailScreen is displayed
                        var showDetailScreen by remember { mutableStateOf(false) }

                        // Launch effect to handle the delay and change the state
                        LaunchedEffect(article) {
                            // Call your event that might take some time
                            viewModel.onEvent(DetailsEvent.IsArticleInDatabase(article))

                            // Add a slight delay before navigating to DetailScreen
                            delay(100)  // Adjust the delay time as needed

                            // After the delay, change the state to show the DetailScreen
                            showDetailScreen = true
                        }

                        // Conditionally show DetailScreen after delay
                        if (showDetailScreen) {
                            DetailScreen(
                                article = article,
                                event = viewModel::onEvent,
                                navigateUp = {
                                    navController.navigateUp()
                                }
                            )
                        }
                    }

            }
            composable(route = Route.BookmarkScreen.route) {
                // BookmarkScreen()

                if (!isBookmarkOpened) {
                    try {
                        val uri = Uri.parse("newsery2://bookmark")
                        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                        isBookmarkOpened = true
                    } catch (e: Exception) {
                        Toast.makeText(context, "Module cannot be installed", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                LaunchedEffect(Unit) {
                    isBookmarkOpened = false
                }

                /*   val viewModel: BookmarkViewModel = hiltViewModel()
                   val state = viewModel.state.value
                   BookmarkScreen(
                       state = state,
                       navigateToDetails = { article ->
                           navigateToDetails(navController = navController, article = article)
                       })*/
            }
        }
    }
}

/**
 * Navigate to the selected tab in the bottom navigation bar when user taps on it
 */
private fun navigateToTap(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            // Reuse the existing instance of the composable (case: if user taps on the same tab,
            // it will not create a new instance)
            launchSingleTop = true
        }
    }
}


// Current Back Stack Entry is the current page that is displayed on the screen
fun navigateToDetails(navController: NavController, article: ArticleModel) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(Route.DetailScreen.route)
}