package com.alexius.newsery2.presentation.navgraph

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.alexius.newsery2.presentation.onboarding.OnBoardingScreen
import com.alexius.newsery2.presentation.onboarding.OnBoardingViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.alexius.newsery2.presentation.news_navigator.NewsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    startDestination: String
) {
    val navController = rememberNavController()

    val isSystemInDarkMode = isSystemInDarkTheme()
    val systemController = rememberSystemUiController()

    NavHost(navController = navController, startDestination = startDestination){
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ){
            composable(
                route = Route.OnBoardingScreen.route
            ){
                val viewModel: OnBoardingViewModel = koinViewModel()
                OnBoardingScreen(
                    event = viewModel::onEvent
                )
                SideEffect {
                    systemController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = !isSystemInDarkMode
                    )
                }
            }
        }

        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.NewsNavigatorScreen.route
        ){
            composable(route = Route.NewsNavigatorScreen.route){
                NewsNavigator()
            }
        }
    }
}