package com.alexius.newsery2.presentation.bookmark


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.alexius.newsery2.domain.usecases.news.NewsUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
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