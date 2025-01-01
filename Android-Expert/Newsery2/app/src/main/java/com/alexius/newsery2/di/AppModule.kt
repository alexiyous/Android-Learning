package com.alexius.newsery2.di

import com.alexius.core.data.remote.huggingface.HuggingFaceAPI
import com.alexius.core.util.Constants.HUGGINGFACE_API_KEY
import com.alexius.core.util.Constants.HUGGINGFACE_BASE_URL
import com.alexius.newsery2.MainViewModel
import com.alexius.newsery2.presentation.detail.DetailsViewModel
import com.alexius.newsery2.presentation.home.HomeViewModel
import com.alexius.newsery2.presentation.huggingface.BarkViewModel
import com.alexius.newsery2.presentation.onboarding.OnBoardingViewModel
import com.alexius.newsery2.presentation.search.SearchNewsViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { DetailsViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { OnBoardingViewModel(get()) }
    viewModel { SearchNewsViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { BarkViewModel(get()) }
}