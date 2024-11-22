package com.alexius.newsery.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alexius.core.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
): ViewModel() {

    val news = newsUseCases.getNews(
        sources = listOf("techcrunch", "wired", "techinasia", "the-verge", "the-next-web")
    ).cachedIn(viewModelScope)
}