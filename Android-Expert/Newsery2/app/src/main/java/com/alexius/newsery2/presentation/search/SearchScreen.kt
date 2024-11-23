package com.alexius.newsery2.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.alexius.core.domain.model.Article
import com.alexius.core.util.Dimens.MediumPadding1
import com.alexius.newsery2.presentation.common.ArticlesList
import com.alexius.newsery2.presentation.common.SearchBar

@Composable
fun SearchScreen (
    modifier: Modifier = Modifier,
    event:(SearchEvent) -> Unit,
    state: SearchState,
    navigateToDetails:(Article) -> Unit
) {
    Column(
        modifier = modifier
            .padding(
                top = MediumPadding1,
                start = MediumPadding1,
                end = MediumPadding1
            )
            .statusBarsPadding()
            .fillMaxSize()
        ) {
        SearchBar(
            text = state.searchQuery,
            readOnly = false,
            onValueChange = { event(SearchEvent.UpdateSearchQuery(it)) },
            onSearch = { event(SearchEvent.SearchNews) })

        Spacer(modifier = Modifier.height(MediumPadding1))
        state.articles?.let {
            val articles = it.collectAsLazyPagingItems()
            ArticlesList(articles = articles, onClick = {navigateToDetails(it)})
        }
    }
}