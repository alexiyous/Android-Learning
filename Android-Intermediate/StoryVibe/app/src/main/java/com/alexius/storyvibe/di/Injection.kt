package com.alexius.storyvibe.di

import android.content.Context
import com.alexius.storyvibe.data.Repository
import com.alexius.storyvibe.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(apiService)
    }
}