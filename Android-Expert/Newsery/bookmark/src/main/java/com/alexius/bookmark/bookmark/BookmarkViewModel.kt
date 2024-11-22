package com.alexius.bookmark.bookmark


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.alexius.core.domain.usecases.news.NewsUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import androidx.compose.runtime.State

@dagger.hilt.android.lifecycle.HiltViewModel
class BookmarkViewModel @javax.inject.Inject constructor(
    private val newsUseCases: NewsUseCases
): androidx.lifecycle.ViewModel() {

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