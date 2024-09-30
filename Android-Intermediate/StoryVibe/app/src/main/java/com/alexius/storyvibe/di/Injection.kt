package com.alexius.storyvibe.di

import android.app.Application
import android.content.Context
import com.alexius.storyvibe.data.Repository
import com.alexius.storyvibe.data.local.LoginDatastore
import com.alexius.storyvibe.data.local.dataStore
import com.alexius.storyvibe.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

object Injection {
    fun provideRepository(context: Context): Repository {
        val datastore = LoginDatastore.getInstance(context.dataStore)
        val token = runBlocking {
            withContext(Dispatchers.IO) {
                datastore.getLoginToken().first()
            }
        }
        val apiService = ApiConfig.getApiService(token)
        return Repository.getInstance(apiService, datastore)
    }
}