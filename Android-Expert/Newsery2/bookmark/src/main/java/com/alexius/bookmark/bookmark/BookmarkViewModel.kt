package com.alexius.bookmark.bookmark


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.alexius.core.domain.usecases.news.NewsUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel


class BookmarkViewModel (
    private val newsUseCases: NewsUseCases
): ViewModel() {

    private val _state = mutableStateOf(BookmarkState())
    val state:State<BookmarkState> = _state



    init {
        getArticles()
    }

    private fun getArticles(){
        newsUseCases.selectArticles().onEach {
            _state.value = state.value.copy(articles = it)
        }.launchIn(viewModelScope)
    }

}