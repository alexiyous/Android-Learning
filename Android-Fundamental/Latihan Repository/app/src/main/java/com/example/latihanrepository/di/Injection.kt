package com.example.latihanrepository.di

import android.content.Context
import com.example.latihanrepository.data.NewsRepository
import com.example.latihanrepository.data.local.room.NewsDatabase
import com.example.latihanrepository.data.remote.retrofit.ApiConfig
import com.example.latihanrepository.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        val appExecutors = AppExecutors()
        return NewsRepository.getInstance(apiService, dao, appExecutors)
    }
}