package com.alexius.storyvibe.di

import android.app.Application
import android.content.Context
import com.alexius.storyvibe.data.Repository
import com.alexius.storyvibe.data.local.LoginDatastore
import com.alexius.storyvibe.data.local.dataStore
import com.alexius.storyvibe.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(apiService)
    }

    fun provideDataStore(context: Context): LoginDatastore {
        return LoginDatastore.getInstance(context.dataStore)
    }
}