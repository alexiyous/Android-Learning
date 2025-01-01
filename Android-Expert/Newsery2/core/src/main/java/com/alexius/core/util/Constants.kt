package com.alexius.core.util

import com.alexius.core.BuildConfig

object Constants {

    const val USER_SETTINGS = "userSettings"

    const val APP_ENTRY = "appEntry"

    const val NEWS_API_KEY = BuildConfig.NEWS_API

    const val HUGGINGFACE_API_KEY = BuildConfig.HUGGINGFACE_API

    const val HUGGINGFACE_BASE_URL = "https://api.elevenlabs.io/v1/"

    const val BASE_URL = "https://newsapi.org/v2/"

    const val NEWS_DATABASE_NAME = "news_db"
}