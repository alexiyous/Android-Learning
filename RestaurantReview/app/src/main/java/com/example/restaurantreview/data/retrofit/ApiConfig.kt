package com.example.restaurantreview.data.retrofit

import com.example.restaurantreview.BuildConfig
import com.google.gson.internal.GsonBuildConfig
import okhttp3.logging.HttpLoggingInterceptor

class ApiConfig {
    companion object {
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    }
}