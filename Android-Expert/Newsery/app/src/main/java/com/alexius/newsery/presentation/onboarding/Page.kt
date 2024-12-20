package com.alexius.newsery.presentation.onboarding

import androidx.annotation.DrawableRes
import com.alexius.core.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = "Welcome to Newsery",
        description = "Get the latest news from around the world",
        image = R.drawable.onboarding_1
    ),
    Page(
        title = "Stay Updated",
        description = "Get the latest news from around the world",
        image = R.drawable.onboarding_2
    ),
    Page(
        title = "Get Notified",
        description = "Get the latest news from around the world",
        image = R.drawable.onboarding_3
    )
)