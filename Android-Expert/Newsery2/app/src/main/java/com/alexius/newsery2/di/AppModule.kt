package com.alexius.newsery2.di

import com.alexius.newsery.MainViewModel
import com.alexius.newsery2.presentation.detail.DetailsViewModel
import com.alexius.newsery2.presentation.home.HomeViewModel
import com.alexius.newsery2.presentation.onboarding.OnBoardingViewModel
import com.alexius.newsery2.presentation.search.SearchNewsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{
    viewModel{ DetailsViewModel(get()) }
    viewModel{ HomeViewModel(get()) }
    viewModel{ OnBoardingViewModel(get()) }
    viewModel{ SearchNewsViewModel(get()) }
    viewModel{ MainViewModel(get()) }
}