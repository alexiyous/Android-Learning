package com.alexius.newsery.presentation.detail

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import com.alexius.core.domain.usecases.news.NewsUseCases
import com.alexius.core.domain.model.Article
import kotlinx.coroutines.launch

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
): ViewModel() {

    var sideEffect by mutableStateOf<String?>(null)
        private set

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.UpsertDeleteArticle -> {
                viewModelScope.launch {
                    Log.d("DetailsViewModel", "UpsertDeleteArticle")
                    val article = newsUseCases.selectArticle(event.article.url)
                    if (article != null && event.article != null && article.isBookmarked == false) {
                        article.isBookmarked = true
                        upsertArticle(event.article)
                        Log.d("DetailsViewModel", "Case 1")
                    } else if (article != null && article.isBookmarked == true) {
                        deleteArticle(article)
                        event.article.isBookmarked = false
                        Log.d("DetailsViewModel", "Case 2")
                    } else if (article == null && event.article != null) {
                        event.article.isBookmarked = true
                        upsertArticle(event.article)
                        Log.d("DetailsViewModel", "Case 3")
                    }
                }
            }

            is DetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }

            is DetailsEvent.IsArticleInDatabase -> {
                viewModelScope.launch {
                    val article = newsUseCases.selectArticle(event.article.url)
                    if (article == null) {
                        Log.d("DetailsViewModel", "Article not in database")
                        event.article.isBookmarked = false
                    }
                }
            }
        }
    }

    private suspend fun upsertArticle(article: Article) {
        newsUseCases.upsertArticle(article)
        sideEffect = "Article saved"
    }

    private suspend fun deleteArticle(article: Article) {
        newsUseCases.deleteArticle(article)
        sideEffect = "Article removed"
    }
}