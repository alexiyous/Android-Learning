package com.alexius.newsery2.presentation.navgraph

sealed class Route(
    val route: String
) {
    object OnBoardingScreen : Route("onboarding_screen")
    object HomeScreen : Route("home_screen")
    object DetailScreen : Route("detail_screen")
    object SearchScreen : Route("search_screen")
    object BookmarkScreen : Route("bookmark_screen")
    object AppStartNavigation : Route("app_start_navigation")
    object NewsNavigation : Route("news_navigation")
    object NewsNavigatorScreen : Route("news_navigator_screen")
}